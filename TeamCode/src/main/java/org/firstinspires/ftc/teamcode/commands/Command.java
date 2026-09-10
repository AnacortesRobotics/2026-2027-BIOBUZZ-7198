package org.firstinspires.ftc.teamcode.commands;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public abstract class Command {

    private final Set<Subsystem> requirements = new HashSet<>();

    public boolean interruptable = true;

    public void init() {}

    public void run() {}

    public void stop(boolean interrupted) {}

    public boolean isFinished() {return false;}

    public Command addRequirements(Subsystem... subsystems) {
        requirements.addAll(Arrays.asList(subsystems));
        return this;
    }

    public Command addRequirements(Collection<Subsystem> subsystems) {
        requirements.addAll(subsystems);
        return this;
    }

    public Set<Subsystem> getRequirements() {
        return requirements;
    }

    public Command cancel() {
        return null; //new InstantCommand(()->{CommandScheduler.getInstance().cancelCommand(this);});
    }

}
