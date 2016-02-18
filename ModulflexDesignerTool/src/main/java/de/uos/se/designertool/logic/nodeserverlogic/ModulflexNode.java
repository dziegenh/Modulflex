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
        extends Observable
{
    private final ListProperty<ModulflexModule> _children;
    private final IntegerProperty _id;
    private final StringProperty _name;

    public ModulflexNode(int id, String name)
    {
        this(id, name, Collections.emptyList());
    }

    public ModulflexNode(int id, String name, List<ModulflexModule> children)
    {
        this._children = new SimpleListProperty<>(new ObservableListWrapper<>(new LinkedList<>(children)));
        this._id = new SimpleIntegerProperty(id);
        this._name = new SimpleStringProperty(name);
    }

    public StringProperty nameProperty()
    {
        return this._name;
    }

    public IntegerProperty idProperty()
    {
        return this._id;
    }

    public ListProperty<ModulflexModule> modulesProperty()
    {
        return this._children;
    }

}
