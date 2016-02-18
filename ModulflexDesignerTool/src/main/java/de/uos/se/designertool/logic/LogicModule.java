package de.uos.se.designertool.logic;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by sem on 18.02.2016.
 */
public class LogicModule <T>
{

    private List<IModuleListener<T>> listeners = new LinkedList<>();

    public void fireEvent()
    {
        fireEvent(null);
    }

    public void fireEvent(T data)
    {
        for (IModuleListener<T> listener : listeners)
        {
            listener.eventFired(data);
        }
    }

    public void addListener(IModuleListener<T> listener)
    {
        this.listeners.add(listener);
    }

}
