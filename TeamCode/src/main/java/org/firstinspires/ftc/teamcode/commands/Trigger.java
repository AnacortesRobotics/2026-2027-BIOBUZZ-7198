package org.firstinspires.ftc.teamcode.commands;

import java.util.function.BooleanSupplier;

public class Trigger implements BooleanSupplier {

    @FunctionalInterface
    private interface BindingBody {
        void run(boolean previous, boolean current);
    }

    private final BooleanSupplier condition;
    private final EventLoop loop;

    public Trigger(BooleanSupplier condition) {
        this.loop = CommandScheduler.getInstance().getDefaultButtonLoop();
        this.condition = condition;
    }

    @Override
    public boolean getAsBoolean() {
        return condition.getAsBoolean();
    }

    private void addBinding(BindingBody body) {
        loop.bind(
                new Runnable() {
                    private boolean previous = condition.getAsBoolean();

                    @Override
                    public void run() {
                        boolean current = condition.getAsBoolean();

                        body.run(previous, current);

                        previous = current;
                    }
                }
        );
    }

    public Trigger onChange(Command command) {
        addBinding(
                ((previous, current) -> {
                    if (previous != current) {
                        CommandScheduler.getInstance().schedule(command);
                    }})
        );
        return this;
    }

    public Trigger onTrue(Command command) {
        addBinding(
                ((previous, current) -> {
                    if (!previous && current) {
                        CommandScheduler.getInstance().schedule(command);
                    }
                })
        );
        return this;
    }

    public Trigger onFalse(Command command) {
        addBinding(
                ((previous, current) -> {
                    if (previous && !current) {
                        CommandScheduler.getInstance().schedule(command);
                    }
                })
        );
        return this;
    }

    public Trigger whileTrue(Command command) {
        addBinding(
                ((previous, current) -> {
                    if (!previous && current) {
                        CommandScheduler.getInstance().schedule(command);
                    } else if (previous && !current) {
                        command.cancel();
                    }
                })
        );
        return this;
    }

    public Trigger whileFalse(Command command) {
        addBinding(
                ((previous, current) -> {
                    if (previous && !current) {
                        CommandScheduler.getInstance().schedule(command);
                    } else if (!previous && current) {
                        command.cancel();
                    }
                })
        );
        return this;
    }

}
