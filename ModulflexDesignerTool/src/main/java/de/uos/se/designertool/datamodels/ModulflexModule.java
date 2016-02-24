package de.uos.se.designertool.datamodels;


import de.uos.se.xsd2gui.models.RootModel;
import de.uos.se.xsd2gui.models.XSDModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.File;
import java.util.Objects;

/**
 * Created by sem on 18.02.2016.
 *
 * Describes a module which is the 3rd tier.
 */
@SuppressWarnings ("unused")
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

    private XSDModel get_rootModel()
    {
        return this._rootModel.get();
    }

    @Override
    public String toString()
    {
        return _name.get() + _id.get();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        ModulflexModule that = (ModulflexModule) o;

        if (get_id() != that.get_id())
            return false;
        if (! get_name().equals(that.get_name()))
        {
            return false;
        }
        // TODO: incorporate rootmodel
        return get_parameterFile().equals(that.get_parameterFile());

    }

    @Override
    public int hashCode()
    {
        // TODO: include the root model
        return Objects.hash(this.get_id(), this.get_name(), this.get_parameterFile());
    }
}
