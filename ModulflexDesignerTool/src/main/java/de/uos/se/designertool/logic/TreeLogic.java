package de.uos.se.designertool.logic;

import de.uos.se.designertool.logic.nodeserverlogic.ModulflexSystemElementType;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sem on 18.02.2016.
 */
public class TreeLogic
{

    private List<ITreeListener> listeners = new LinkedList<>();

    public void changeElement(ModulflexSystemElementType element)
    {
        for (ITreeListener listener : listeners)
        {
            listener.selectionChanged(element);
        }
    }

    public void addListener(ITreeListener listener)
    {
        this.listeners.add(listener);
    }

}
