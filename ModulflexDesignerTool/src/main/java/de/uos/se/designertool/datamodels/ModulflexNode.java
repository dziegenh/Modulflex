package de.uos.se.designertool.datamodels;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Created by sem on 18.02.2016.
 * <p>
 * This class encapsulates a node which is the 2nd tier.
 */

@SuppressWarnings ("unused")
public class ModulflexNode
        extends ModulflexSystemElementType
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

    public ModulflexNode()
    {
        super();
        this._children = new SimpleListProperty<>(new ObservableListWrapper<>(new LinkedList<>()));
    }

    public ListProperty<ModulflexModule> modulesProperty()
    {
        return this._children;
    }

    @XmlElementWrapper (name = "children")
    @XmlElement (name = "module")
    private ObservableList<ModulflexModule> getChildren()
    {
        return this._children.getValue();
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

        ModulflexNode that = (ModulflexNode) o;

        if (get_id() != that.get_id())
            return false;
        //noinspection SimplifiableIfStatement
        if (! get_name().equals(that.get_name()))
        {
            return false;
        }

        return this.getChildren().equals(that.getChildren());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(this.get_id(), this.get_name(), this.getChildren());
    }
}
