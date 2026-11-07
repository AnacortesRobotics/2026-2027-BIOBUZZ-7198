package org.firstinspires.ftc.teamcode.commands;

public class RepeatCommand extends Command {

    private Command repeatCommand;

    private int maxLoops;
    private int currentLoops = 0;

    private boolean isStopped = false;

    public RepeatCommand(Command command) {
        this(command, -1);
    }

    public RepeatCommand(Command command, int loops) {
        repeatCommand = command;
        addRequirements(repeatCommand.getRequirements());
        maxLoops = loops;
    }

    @Override
    public void init() {
        repeatCommand.init();
        currentLoops = 0;
    }

    @Override
    public void run() {
        if (currentLoops == maxLoops) return;
        if (isStopped && (currentLoops < maxLoops || maxLoops == -1)) {
            repeatCommand.init();
            isStopped = false;
        }
        repeatCommand.run();
        if (repeatCommand.isFinished()) {
            repeatCommand.stop(false);
            isStopped = true;
            if (maxLoops != -1) {currentLoops += 1;}
        }
    }

    @Override
    public void stop(boolean isInterrupted) {
        if (!isStopped) {
            repeatCommand.stop(true);
            isStopped = true;
        }
    }

    @Override
    public boolean isFinished() {
        return maxLoops != -1 && currentLoops == maxLoops;
    }

}