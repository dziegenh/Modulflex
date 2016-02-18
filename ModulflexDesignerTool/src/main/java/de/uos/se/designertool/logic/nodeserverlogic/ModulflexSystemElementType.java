package de.uos.se.designertool.logic.nodeserverlogic;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Observable;

/**
 * Created by sem on 18.02.2016.
 */
public class ModulflexSystemElementType
        extends Observable
{
    protected final IntegerProperty _id;
    protected final StringProperty _name;

    public ModulflexSystemElementType(int id, String name)
    {
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
}
