package org.firstinspires.ftc.teamcode.commands;

import java.util.*;

public class CommandScheduler {

    private static CommandScheduler instance;

    private final Set<Command> activeCommands = new LinkedHashSet<>();
    private LinkedHashMap<Subsystem, Command> requirements = new LinkedHashMap<>();
    private final Map<Subsystem, Command> subsystems = new LinkedHashMap<>();
    private final EventLoop defaultButtonLoop = new EventLoop();

    private boolean disabled;

    private boolean inRunLoop;
    private final Set<Command> toSchedule = new LinkedHashSet<>();
    private final List<Command> toCancelCommands = new ArrayList<>();
    private final Set<Command> endingCommands = new LinkedHashSet<>();

    public static synchronized CommandScheduler getInstance() {
        if (instance == null) {instance = new CommandScheduler();}
        return instance;
    }

    public EventLoop getDefaultButtonLoop() {
        return defaultButtonLoop;
    }

    private void initCommand(Command command, Set<Subsystem> newRequirements) {
        activeCommands.add(command);
        for (Subsystem requirement : newRequirements) {
            requirements.put(requirement, command);
        }
        command.init();
    }

    private void schedule(Command command) {
        if (command == null) {
            return;
        }
        if (inRunLoop) {
            toSchedule.add(command);
            return;
        }

        Set<Subsystem> newRequirements = command.getRequirements();

        // Schedule the command if the requirements are not currently in-use.
        if (Collections.disjoint(requirements.keySet(), newRequirements)) {
            initCommand(command, newRequirements);
        } else {
            // Else check if the requirements that are in use have all have interruptible commands,
            // and if so, interrupt those commands and schedule the new command.
            for (Subsystem requirement : newRequirements) {
                Command requiring = requirements.get(requirement);
                if (requiring != null
                        && !requiring.interruptable) {
                    return;
                }
            }
            for (Subsystem requirement : newRequirements) {
                Command requiring = requirements.get(requirement);
                if (requiring != null) {
                    cancel(requiring);
                }
            }
            initCommand(command, newRequirements);
        }
    }

    public void schedule(Command... commands) {
        for (Command command : commands) {
            schedule(command);
        }
    }

    public void run() {
        // Run the periodic method of all registered subsystems.
        for (Subsystem subsystem : subsystems.keySet()) {
            subsystem.periodic();
        }

        // Cache the active instance to avoid concurrency problems if setActiveLoop() is called from
        // inside the button bindings.
        EventLoop loopCache = defaultButtonLoop;
        // Poll buttons for new commands to add.
        loopCache.poll();

        inRunLoop = true;
        // Run scheduled commands, remove finished commands.
        for (Iterator<Command> iterator = activeCommands.iterator(); iterator.hasNext(); ) {
            Command command = iterator.next();

            command.run();
            if (command.isFinished()) {
                endingCommands.add(command);
                command.stop(false);
                endingCommands.remove(command);
                iterator.remove();

                requirements.keySet().removeAll(command.getRequirements());
            }
        }
        inRunLoop = false;

        // Schedule/cancel commands from queues populated during loop
        for (Command command : toSchedule) {
            schedule(command);
        }

        for (int i = 0; i < toCancelCommands.size(); i++) {
            cancel(toCancelCommands.get(i));
        }

        toSchedule.clear();
        toCancelCommands.clear();

        // Add default commands for un-required registered subsystems.
        for (Map.Entry<Subsystem, Command> subsystemCommand : subsystems.entrySet()) {
            if (!requirements.containsKey(subsystemCommand.getKey())
                    && subsystemCommand.getValue() != null) {
                schedule(subsystemCommand.getValue());
            }
        }
    }

    private void cancel(Command command) {
        if (command == null) {
            return;
        }
        if (endingCommands.contains(command)) {
            return;
        }
        if (inRunLoop) {
            toCancelCommands.add(command);
            return;
        }
        if (!activeCommands.contains(command)) {
            return;
        }

        endingCommands.add(command);
        command.stop(true);

        endingCommands.remove(command);
        activeCommands.remove(command);
        requirements.keySet().removeAll(command.getRequirements());
    }

}
