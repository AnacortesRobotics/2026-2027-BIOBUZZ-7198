package org.firstinspires.ftc.teamcode.commands;

import java.util.*;

public class ParallelRaceCommandGroup extends Command{

    private Set<Command> commands = new LinkedHashSet<>();
    private boolean isFinished = false;

    public ParallelRaceCommandGroup(Command... commandsToAdd) {
        for (Command command : commandsToAdd) {
            if (!Collections.disjoint(command.getRequirements(), getRequirements())) {
                throw new IllegalArgumentException(
                        "Multiple commands in a parallel composition cannot require the same subsystems");
            }
            commands.add(command);
            addRequirements(command.getRequirements());
    }}

    @Override
    public final void init() {
        for (Command command : commands) {
            command.init();
        }
    }

    @Override
    public final void run() {
        for (Command command : commands) {
            command.run();
            if (command.isFinished()) {
                isFinished = true;
            }
        }
    }

    @Override
    public final void stop(boolean interrupted) {
        if (interrupted) {
            for (Command command : commands) {
                command.stop(true);
            }
        }
    }

    @Override
    public final boolean isFinished() {
        return isFinished;
    }


}
