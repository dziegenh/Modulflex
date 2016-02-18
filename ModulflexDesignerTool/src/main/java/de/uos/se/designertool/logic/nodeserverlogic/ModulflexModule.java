package de.uos.se.designertool.logic.nodeserverlogic;


import javafx.beans.property.*;

import java.io.File;
import java.util.Observable;

/**
 * Created by sem on 18.02.2016.
 */
public class ModulflexModule
        extends Observable
{
    private final IntegerProperty _id;
    private final StringProperty _name;
    private final ObjectProperty<File> _parameterFile;

    public ModulflexModule(int id, String name, File parameterFile)
    {
        this._id = new SimpleIntegerProperty(id);
        this._name = new SimpleStringProperty(name);
        this._parameterFile = new SimpleObjectProperty<>(parameterFile);
    }

    public ModulflexModule(int id, String name)
    {
        this(id, name, null);
    }

    public IntegerProperty idProperty()
    {
        return _id;
    }


    public StringProperty nameProperty()
    {
        return _name;
    }

    public ObjectProperty<File> parameterFileProperty()
    {
        return _parameterFile;
    }
}
