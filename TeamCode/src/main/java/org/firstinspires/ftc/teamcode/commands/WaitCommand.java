package org.firstinspires.ftc.teamcode.commands;

import java.util.function.LongSupplier;

public class WaitCommand extends Command {

    private long msDuration = 0;
    private long startTime = 0;

    public WaitCommand(long duration) {
        this.msDuration = duration;
    }

    public WaitCommand(LongSupplier duration) {
        this.msDuration = duration.getAsLong();
    }

    @Override
    public void init() {
        startTime = System.currentTimeMillis();
    }

    @Override
    public boolean isFinished() {
        return System.currentTimeMillis() >= startTime + msDuration;
    }

}