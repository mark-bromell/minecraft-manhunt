package com.markbromell.manhunt.event;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ListenerManager {
    private static ListenerManager instance = null;
    private PluginManager pluginManager;
    private Plugin plugin;
    private List<Listener> listeners;

    private ListenerManager() {
        listeners = new ArrayList<>();
    }

    public static ListenerManager getInstance() {
        if (instance == null) {
            instance = new ListenerManager();
        }

        return instance;
    }

    public void setPluginManager(PluginManager pluginManager) {
        this.pluginManager = pluginManager;
    }

    public void setPlugin(Plugin plugin) {
        this.plugin = plugin;
    }

    public void add(Listener listener) {
        listeners.add(listener);
        pluginManager.registerEvents(listener, plugin);
    }

    public void add(List<Listener> listeners) {
        this.listeners.addAll(listeners);
        listeners.forEach(l -> pluginManager.registerEvents(l, plugin));
    }

    public void remove(Listener listener) {
        listeners.add(listener);
        HandlerList.unregisterAll(listener);
    }

    public void remove(List<Listener> listeners) {
        this.listeners.removeAll(listeners);
        listeners.forEach(HandlerList::unregisterAll);
    }

    public void removeAll() {
        listeners.forEach(HandlerList::unregisterAll);
    }
}
