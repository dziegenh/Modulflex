package de.uos.se.designertool.logic;

/**
 *
 * @author dziegenhagen
 * A listener to be used for registering within an {@linkplain LogicModule}
 */
public interface ILogicModuleListener<T>
{
    /**
     * To be called when an event has been 'fired' as in {@linkplain LogicModule#fireEvent(Object)}
     * Data is null if {@linkplain LogicModule#fireEvent()} was called.
     *
     * @param data
     *         the data to be fired, possibly null if no data is present
     */
    void eventFired(T data);

}
