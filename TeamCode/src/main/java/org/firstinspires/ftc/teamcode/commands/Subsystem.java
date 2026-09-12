package org.firstinspires.ftc.teamcode.commands;

public interface Subsystem {

    default void periodic() {

    }

    default String getName() {
        return this.getClass().getSimpleName();
    }

    default void setDefaultCommand(Command defaultCommand) {
        CommandScheduler.getInstance().setDefaultCommand(this, defaultCommand);
    }

    default void removeDefaultCommand() {
        CommandScheduler.getInstance().removeDefaultCommand(this);
    }

    default Command getDefaultCommand() {
        return CommandScheduler.getInstance().getDefaultCommand(this);
    }

    default Command getCurrentCommand() {
        return CommandScheduler.getInstance().requiring(this);
    }

    default void register() {
        CommandScheduler.getInstance().registerSubsystem(this);
    }

}
