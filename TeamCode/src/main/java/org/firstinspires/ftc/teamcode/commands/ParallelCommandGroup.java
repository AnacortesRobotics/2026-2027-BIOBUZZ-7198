package org.firstinspires.ftc.teamcode.commands;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class ParallelCommandGroup extends Command {

    private Map<Command, Boolean> commands = new LinkedHashMap<>();

    public ParallelCommandGroup(Command... commandsToAdd) {
        for (Command command : commandsToAdd) {
            if (!Collections.disjoint(command.getRequirements(), getRequirements())) {
                throw new IllegalArgumentException(
                        "Multiple commands in a parallel composition cannot require the same subsystems");
            }
            commands.put(command, false);
            addRequirements(command.getRequirements());
    }}

    @Override
    public final void init() {
        for (Map.Entry<Command, Boolean> commandRunning : commands.entrySet()) {
            commandRunning.getKey().init();
            commandRunning.setValue(true);
        }
    }

    @Override
    public final void run() {
        for (Map.Entry<Command, Boolean> commandRunning : commands.entrySet()) {
            if (!commandRunning.getValue()) {
                continue;
            }
            commandRunning.getKey().run();
            if (commandRunning.getKey().isFinished()) {
                commandRunning.getKey().stop(false);
                commandRunning.setValue(false);
            }
        }
    }

    @Override
    public final void stop(boolean interrupted) {
        if (interrupted) {
            for (Map.Entry<Command, Boolean> commandRunning : commands.entrySet()) {
                if (commandRunning.getValue()) {
                    commandRunning.getKey().stop(true);
                }
            }
        }
    }

    @Override
    public final boolean isFinished() {
        return !commands.containsValue(true);
    }

}
