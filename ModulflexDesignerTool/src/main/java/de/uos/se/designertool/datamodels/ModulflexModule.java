package de.uos.se.designertool.datamodels;


import de.uos.se.xsd2gui.models.RootModel;
import de.uos.se.xsd2gui.models.XSDModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
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

    public ModulflexModule()
    {
        super();
        this._parameterFile = new SimpleObjectProperty<>();
        this._rootModel = new SimpleObjectProperty<>();
    }

    public ObjectProperty<File> parameterFileProperty()
    {
        return _parameterFile;
    }

    public ObjectProperty<XSDModel> rootModelProperty()
    {
        return _rootModel;
    }

    @XmlAttribute (name = "id")
    private int get_id()
    {
        return _id.get();
    }

    private void set_id(int _id)
    {
        this._id.set(_id);
    }

    @XmlAttribute (name = "name")
    private String get_name()
    {
        return _name.get();
    }

    private void set_name(String _name)
    {
        this._name.set(_name);
    }

    @XmlJavaTypeAdapter (FileAdapter.class)
    @XmlAttribute (name = "parameterFile")
    private File get_parameterFile()
    {
        return this._parameterFile.get();
    }

    private void set_parameterFile(File file)
    {
        this._parameterFile.setValue(file);
    }

    @Override
    public String toString()
    {
        return _name.get() + _id.get();
    }
}
