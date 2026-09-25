package org.firstinspires.ftc.teamcode.commands;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

public class FunctionalCommand extends Command {

    private final Runnable onInit;
    private final Runnable onRun;
    private final Consumer<Boolean> onStop;
    private final BooleanSupplier isFinished;

    public FunctionalCommand(
            Runnable onInit,
            Runnable onRun,
            Consumer <Boolean> onStop,
            BooleanSupplier isFinished,
            Subsystem... requirements
    ) {
        this.onInit = onInit;
        this.onRun = onRun;
        this.onStop = onStop;
        this.isFinished = isFinished;
        addRequirements(requirements);
    }

    @Override
    public void init() {
        onInit.run();
    }

    @Override
    public void run() {
        onRun.run();
    }

    @Override
    public void stop(boolean interrupted) {
        onStop.accept(interrupted);
    }

    @Override
    public boolean isFinished() {
        return isFinished.getAsBoolean();
    }


}
