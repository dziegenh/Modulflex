package de.uos.se.designertool.logic.nodeserverlogic;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.beans.property.*;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

/**
 * Created by sem on 18.02.2016.
 */
public class ModulflexNode
        extends SystemElementType
{
    private final ListProperty<ModulflexModule> _children;

    public ModulflexNode(int id, String name)
    {
        this(id, name, Collections.emptyList());
    }

    public ModulflexNode(int id, String name, List<ModulflexModule> children)
    {
        super(id, name);
        this._children = new SimpleListProperty<>(new ObservableListWrapper<>(new LinkedList<>(children)));
    }

    public ListProperty<ModulflexModule> modulesProperty()
    {
        return this._children;
    }

    @Override
    public String toString()
    {
        return _name.get() + _id.get();
    }

}
