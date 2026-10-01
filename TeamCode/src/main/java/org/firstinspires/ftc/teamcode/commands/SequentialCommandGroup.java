package org.firstinspires.ftc.teamcode.commands;

import java.util.ArrayList;
import java.util.List;

public class SequentialCommandGroup extends Command {

    private final List<Command> commands = new ArrayList<>();
    private int currentIndex = -1;

    public SequentialCommandGroup(Command... commandsToAdd) {
        for (Command command : commandsToAdd) {
            commands.add(command);
            addRequirements(command.getRequirements());
    }}

    @Override
    public final void init() {
        currentIndex = 0;

        if (!commands.isEmpty()) {
            commands.get(0).init();
        }
    }

    @Override
    public final void run() {
        if (commands.isEmpty()) {
            return;
        }

        Command currentCommand = commands.get(currentIndex);

        currentCommand.run();
        if (currentCommand.isFinished()) {
            currentCommand.stop(false);
            currentIndex++;
            if (currentIndex < commands.size()) {
                commands.get(currentIndex).init();
            }
        }
    }

    @Override
    public final void stop(boolean interrupted) {
        if (interrupted
                && !commands.isEmpty()
                && currentIndex > -1
                && currentIndex < commands.size()) {
            commands.get(currentIndex).stop(true);
        }
        currentIndex = -1;
    }

    @Override
    public final boolean isFinished() {
        return currentIndex == commands.size();
    }

}
