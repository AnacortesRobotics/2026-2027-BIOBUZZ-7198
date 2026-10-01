package org.firstinspires.ftc.teamcode.commands;

import java.util.ConcurrentModificationException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

public class EventLoop {

    private final LinkedHashSet<Runnable> bindings = new LinkedHashSet<>();
    private final LinkedHashSet<Runnable> toBind = new LinkedHashSet<>();
    private final LinkedHashSet<Runnable> toUnbind = new LinkedHashSet<>();
    private boolean running = false;

    public void bind(Runnable action) {
        if (running) {
            toBind.add(action);
            return;
        }
        bindings.add(action);
    }

    public boolean isBound(Runnable action) {
        return bindings.contains(action);
    }

    public void unbind(Runnable action) {
        if (running) {
            toUnbind.add(action);
            return;
        }
        bindings.remove(action);
    }

    public void poll() {
        try {
            running = true;
            bindings.forEach(Runnable::run);
        } finally {
            running = false;
            for (Runnable action : toBind) {
                bind(action);
            }
            for (Runnable action : toUnbind) {
                unbind(action);
            }
        }
    }

    public void clear() {
        if (running) {
            throw new ConcurrentModificationException("Cannot clear EventLoop while it is running");
        }
        bindings.clear();
    }

}
