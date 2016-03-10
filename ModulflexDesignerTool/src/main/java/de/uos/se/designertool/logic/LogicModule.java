package de.uos.se.designertool.logic;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by sem on 18.02.2016.
 * An abstract class representing a module which can house {@linkplain ILogicModuleListener}. Types of events should be modelled by creating children of this class and naming them accordingly.
 */
public class LogicModule <T>
{

    //the listeners
    private List<ILogicModuleListener<T>> listeners = new LinkedList<>();

    /**
     * same as calling <i>fireEvent(null)</i>
     */
    public void fireEvent()
    {
        fireEvent(null);
    }

    /**
     * This method fires the event represented by this {@linkplain LogicModule}. Is iterates over the internal list of {@linkplain ILogicModuleListener}s and calls {@linkplain ILogicModuleListener#eventFired(Object)} on them.
     *
     * @param data
     *         the data to be passed (should be immutable)
     */
    public void fireEvent(T data)
    {
        for (ILogicModuleListener<T> listener : listeners)
        {
            listener.eventFired(data);
        }
    }

    public void addListener(ILogicModuleListener<T> listener)
    {
        this.listeners.add(listener);
    }

    public void removeListener(ILogicModuleListener<T> listener)
    {
        this.listeners.remove(listener);}

}
