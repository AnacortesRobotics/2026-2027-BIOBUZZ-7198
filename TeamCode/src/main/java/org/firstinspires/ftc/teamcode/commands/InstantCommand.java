package org.firstinspires.ftc.teamcode.commands;

public class InstantCommand extends FunctionalCommand{

    public InstantCommand(Runnable toRun, Subsystem... requirements) {
        super(toRun, ()->{}, (interrupted)->{}, ()->true, requirements);
    }

}
