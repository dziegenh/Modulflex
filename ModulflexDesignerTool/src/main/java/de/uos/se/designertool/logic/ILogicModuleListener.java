package de.uos.se.designertool.logic;

/**
 *
 * @author dziegenhagen
 */
public interface ILogicModuleListener<T>
{

    void eventFired(T data);

}
