package org.firstinspires.ftc.teamcode.commands;

import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.function.LongSupplier;

public class WaitCommand extends Command{
    ElapsedTime elapsedTime;
    private long msDuration = 0;

    public WaitCommand(long duration) {
        this.msDuration = duration;
    }

    @Override
    public void init() {
        elapsedTime.reset();
    }

    @Override
    public boolean isFinished() {
        return System.currentTimeMillis() >= msDuration;
    }
}
