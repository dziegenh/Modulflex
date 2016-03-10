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
 * This class encapsulates a node which is the 2nd tier in the hierarchy.
 */

@SuppressWarnings ("unused")
public class ModulflexNode
        extends ModulflexSystemElementType
{
    private final ListProperty<ModulflexModule> _children;

    /**
     * Creates a new instance that has merely an id and a name.
     *
     * @param id
     *         The ID to assign.
     * @param name
     *         The name to assign.
     */
    public ModulflexNode(int id, String name)
    {
        this(id, name, Collections.emptyList());
    }

    /**
     * Creates a new instance that works like {@linkplain ModulflexNode#ModulflexNode(int, String)} but adds a list of children as well.
     *
     * @param id
     *         The ID to assign.
     * @param name
     *         The name to assign.
     * @param children
     *         The children to assign.
     */
    public ModulflexNode(int id, String name, List<ModulflexModule> children)
    {
        super(id, name);
        this._children = new SimpleListProperty<>(new ObservableListWrapper<>(new LinkedList<>(children)));
    }

    /**
     * Instantiates with an empty children list property.
     */
    public ModulflexNode()
    {
        super();
        this._children = new SimpleListProperty<>(new ObservableListWrapper<>(new LinkedList<>()));
    }

    /**
     * @return The {@linkplain javafx.beans.property.Property} that contains the children.
     */
    public ListProperty<ModulflexModule> modulesProperty()
    {
        return this._children;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        return Objects.hash(this.get_id(), this.get_name(), this.getChildren());
    }

    /**
     * Tests for equality according to the rules defined by {@linkplain Object#equals(Object)}.
     * Only other {@linkplain ModulflexNode}s can be equal and their id, name and children are tested.
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

    /**
     * For JAXB purposes only.
     */
    @XmlAttribute (name = "id")
    private int get_id()
    {
        return _id.get();
    }

    /**
     * For JAXB purposes only.
     */
    private void set_id(int _id)
    {
        this._id.set(_id);
    }

    /**
     * For JAXB purposes only.
     */
    @XmlAttribute (name = "name")
    private String get_name()
    {
        return _name.get();
    }

    /**
     * For JAXB purposes only.
     */
    @XmlElementWrapper (name = "children")
    @XmlElement (name = "module")
    private ObservableList<ModulflexModule> getChildren()
    {
        return this._children.getValue();
    }

    /**
     * For JAXB purposes only.
     */
    private void set_name(String _name)
    {
        this._name.set(_name);
    }

    /**
     * @return A {@linkplain String} containing the name and ID of the node.
     */
    @Override
    public String toString()
    {
        return _name.get() + " [ID:" + _id.get() + "]";
    }
}
