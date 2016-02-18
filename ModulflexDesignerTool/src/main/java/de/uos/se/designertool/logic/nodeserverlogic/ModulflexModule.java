package de.uos.se.designertool.logic.nodeserverlogic;


import de.uos.se.xsd2gui.models.RootModel;
import de.uos.se.xsd2gui.models.XSDModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.io.File;

/**
 * Created by sem on 18.02.2016.
 */
public class ModulflexModule
        extends ModulflexSystemElementType
{
    private final ObjectProperty<File> _parameterFile;
    private final ObjectProperty<XSDModel> _rootModel;

    public ModulflexModule(int id, String name, File parameterFile)
    {
        this(id, name, parameterFile, null);
    }

    public ModulflexModule(int id, String name, File parameterFile, RootModel model)
    {
        super(id, name);
        this._parameterFile = new SimpleObjectProperty<>(parameterFile);
        this._rootModel = new SimpleObjectProperty<>(model);
    }

    public ObjectProperty<File> parameterFileProperty()
    {
        return _parameterFile;
    }


    public ObjectProperty<XSDModel> rootModelProperty()
    {
        return _rootModel;
    }

    @Override
    public String toString()
    {
        return _name.get()+_id.get();
    }
}
