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
 * <p>
 * Describes a module which is the 3rd tier in the hierarchy.
 */
@SuppressWarnings ("unused")
public class ModulflexModule
        extends ModulflexSystemElementType
{
    private final ObjectProperty<File> _parameterFile;
    private final ObjectProperty<XSDModel> _rootModel;

    /**
     * Creates a new module with an ID, a name and a parameter file.
     *
     * @param id
     *         The ID to assign.
     * @param name
     *         The name to assign.
     * @param parameterFile
     *         The parameter file to assign.
     */
    public ModulflexModule(int id, String name, File parameterFile)
    {
        this(id, name, parameterFile, null);
    }

    /**
     * Does basically the same as {@linkplain this#ModulflexModule(int, String, File)} but has a {@linkplain RootModel}.
     *
     * @param id
     *         The ID to assign.
     * @param name
     *         The name to assign.
     * @param parameterFile
     *         The parameter file to assign.
     * @param model
     *         The root model.
     */
    public ModulflexModule(int id, String name, File parameterFile, RootModel model)
    {
        super(id, name);
        this._parameterFile = new SimpleObjectProperty<>(parameterFile);
        this._rootModel = new SimpleObjectProperty<>(model);
    }

    /**
     * An empty constructor that assigns empty properties to parameter file and root model.
     */
    public ModulflexModule()
    {
        super();
        this._parameterFile = new SimpleObjectProperty<>();
        this._rootModel = new SimpleObjectProperty<>();
    }

    /**
     * @return The parameter file {@linkplain javafx.beans.property.Property}.
     */
    public ObjectProperty<File> parameterFileProperty()
    {
        return _parameterFile;
    }

    /**
     * @return The root model {@linkplain javafx.beans.property.Property}.
     */
    public ObjectProperty<XSDModel> rootModelProperty()
    {
        return _rootModel;
    }

    /**
     * For JAXB only.
     */
    private XSDModel get_rootModel()
    {
        return this._rootModel.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        // TODO: include the root model
        return Objects.hash(this.get_id(), this.get_name(), this.get_parameterFile());
    }

    /**
     * Tests for equality according to the rules defined by {@linkplain Object#equals(Object)}.
     * Only other {@linkplain ModulflexModule}s can be equal and their id, name and parameter file are tested.
     *
     * @param o
     *         The instance to test against.
     *
     * @return true if equal.
     */
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

    /**
     * For JAXB only.
     */
    @XmlAttribute (name = "id")
    private int get_id()
    {
        return _id.get();
    }

    /**
     * For JAXB only.
     */
    private void set_id(int _id)
    {
        this._id.set(_id);
    }

    /**
     * For JAXB only.
     */
    @XmlAttribute (name = "name")
    private String get_name()
    {
        return _name.get();
    }

    /**
     * For JAXB only.
     */
    private void set_name(String _name)
    {
        this._name.set(_name);
    }

    /**
     * For JAXB only.
     */
    @XmlJavaTypeAdapter (FileAdapter.class)
    @XmlAttribute (name = "parameterFile")
    private File get_parameterFile()
    {
        return this._parameterFile.get();
    }

    /**
     * For JAXB only.
     */
    private void set_parameterFile(File file)
    {
        this._parameterFile.setValue(file);
    }

    /**
     * @return A String which describes an instance by name and id.
     */
    @Override
    public String toString()
    {
        return _name.get() + " [" + _id.get() + "]";
    }
}
