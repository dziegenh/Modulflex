package de.uos.se.designertool.datamodels;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import org.eclipse.persistence.oxm.annotations.XmlPath;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Created by sem on 18.02.2016.
 * <p>
 * This class represents a node server. This is the top-level element in a topology.
 * For convenience all values are handled by Properties (See {@linkplain Property}). This also means values can be changed after creation.
 * The getter and setter methods are private since they need to be exclusively accessed by JAXB.
 */
@SuppressWarnings ("unused")
@XmlRootElement (name = "Nodeserver")
public class ModulflexNodeServer
{
    private final DoubleProperty cycleTime;

    private final ObjectProperty<Boolean> profiling;

    private final StringProperty logDir;

    private final StringProperty logLevel;

    private final ObjectProperty<Boolean> noLog;

    private final ListProperty<ModulflexNode> children;

    /**
     * Creates a new node server. The parameters are mostly not primitive to allow <code>null</code>-values in case of the absence of non-required values.
     *
     * @param cycleTime
     *         The cycle time, required.
     * @param profiling
     *         Defines whether profiling is enabled. Not required.
     * @param logDir
     *         Defines the directory for logging. Not required.
     * @param logLevel
     *         Defines the log level. Not required.
     * @param noLog
     *         Defines whether to log or not. Not required.
     * @param children
     *         A list of children. Not required.
     */
    public ModulflexNodeServer(double cycleTime, Boolean profiling, String logDir, String logLevel, Boolean noLog, List<ModulflexNode> children)
    {
        /*
         * BooleanProperty does not support null values, therefore ObjectProperty<Boolean> is used instead.
         */

        this.cycleTime = new SimpleDoubleProperty(cycleTime);

        if (Objects.isNull(profiling))
        {
            this.profiling = new SimpleObjectProperty<>();
        } else
        {
            this.profiling = new SimpleObjectProperty<>(profiling);
        }

        if (Objects.isNull(logDir))
        {
            this.logDir = new SimpleStringProperty();
        } else
        {
            this.logDir = new SimpleStringProperty(logDir);
        }

        if (Objects.isNull(logLevel))
        {
            this.logLevel = new SimpleStringProperty();
        } else
        {
            this.logLevel = new SimpleStringProperty(logLevel);
        }

        if (Objects.isNull(noLog))
        {
            this.noLog = new SimpleObjectProperty<>();
        } else
        {
            this.noLog = new SimpleObjectProperty<>(noLog);
        }

        this.children = new SimpleListProperty<>();
        if (Objects.nonNull(children))
        {
            this.children.setValue(new ObservableListWrapper<>(children));
        } else
        {
            this.children.setValue(new ObservableListWrapper<>(new LinkedList<>()));
        }
    }

    /**
     * Convenience constructor that only needs arguments that are required for a node server.
     *
     * @param cycleTime
     *         The cycle time for the node server.
     */
    @SuppressWarnings ("WeakerAccess")
    public ModulflexNodeServer(@SuppressWarnings ("SameParameterValue") double cycleTime)
    {
        this(cycleTime, null, null, null, null, null);
    }

    /**
     * For JAXB purposes only
     */
    public ModulflexNodeServer()
    {
        this(.0);
    }

    /**
     * For JAXB purposes only
     */
    @XmlPath ("configuration/@cycleTime")
    private double getCycleTime()
    {
        return cycleTime.get();
    }

    public DoubleProperty cycleTimeProperty()
    {
        return cycleTime;
    }

    /**
     * For JAXB purposes only
     */
    private void setCycleTime(double cycleTime)
    {
        this.cycleTime.set(cycleTime);
    }

    /**
     * For JAXB purposes only
     */
    @XmlPath ("configuration/@profiling")
    private Boolean getProfiling()
    {
        return profiling.get();
    }

    public ObjectProperty<Boolean> profilingProperty()
    {
        return profiling;
    }

    /**
     * For JAXB purposes only
     */
    private void setProfiling(Boolean profiling)
    {
        this.profiling.set(profiling);
    }

    /**
     * For JAXB purposes only
     */
    @XmlPath ("configuration/@logDir")
    private String getLogDir()
    {
        return logDir.get();
    }

    public StringProperty logDirProperty()
    {
        return logDir;
    }

    /**
     * For JAXB purposes only
     */
    private void setLogDir(String logDir)
    {
        this.logDir.set(logDir);
    }

    /**
     * For JAXB purposes only
     */
    @XmlPath ("configuration/@logLevel")
    private String getLogLevel()
    {
        return logLevel.get();
    }

    public StringProperty logLevelProperty()
    {
        return logLevel;
    }

    /**
     * For JAXB purposes only
     */
    private void setLogLevel(String logLevel)
    {
        this.logLevel.set(logLevel);
    }

    /**
     * For JAXB purposes only
     */
    @XmlPath ("configuration/@noLog")
    private Boolean getNoLog()
    {
        return noLog.get();
    }

    public ObjectProperty<Boolean> noLogProperty()
    {
        return noLog;
    }

    /**
     * For JAXB purposes only
     */
    private void setNoLog(Boolean noLog)
    {
        this.noLog.set(noLog);
    }

    /**
     * For JAXB purposes only
     */
    @XmlElementWrapper (name = "children")
    @XmlElement (name = "node")
    private ObservableList<ModulflexNode> getChildren()
    {
        return children.get();
    }

    public ListProperty<ModulflexNode> childrenProperty()
    {
        return children;
    }

    /**
     * For JAXB purposes only
     */
    private void setChildren(ObservableList<ModulflexNode> children)
    {
        this.children.set(children);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        int result = cycleTime.getValue().hashCode();
        result = 31 * result + Objects.hashCode(profiling.getValue());
        result = 31 * result + Objects.hashCode(logDir.getValue());
        result = 31 * result + Objects.hashCode(logLevel.getValue());
        result = 31 * result + Objects.hashCode(noLog.getValue());
        // TODO: incorporate this when it's part of equals()
        //   result = 31 * result + children.hashCode();
        return result;
    }

    /**
     * Two instances are equals if they fulfil what is defined in {@linkplain Object#equals(Object)} and their {@linkplain #getClass()}
     * yield the same. And all their attributes are the same, e.g. cycle time (same value), log dir (either both not set or same).
     *
     * @param o
     *         The instance to test for equality.
     *
     * @return true as described above, false otherwise.
     */
    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        ModulflexNodeServer that = (ModulflexNodeServer) o;

        // properties inherit equals() from object, which is problematic for a useful comparison

        if (this.getCycleTime() != that.getCycleTime())
        {
            return false;
        }
        if (this.getNoLog() != that.getNoLog())
        {
            return false;
        }
        if (this.getProfiling() != that.getProfiling())
        {
            return false;
        }
        if (! Objects.equals(this.getLogDir(), that.getLogDir()))
        {
            return false;
        }
        if (! Objects.equals(this.getLogLevel(), that.getLogLevel()))
        {
            return false;
        }
        // TODO: Incorporate children, they must implement equals() therefore
        //return this.getChildren().equals(that.getChildren());
        return true;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return "ModulflexNodeServer{" +
                "cycleTime=" + cycleTime +
                ", profiling=" + profiling +
                ", logDir=" + logDir +
                ", logLevel=" + logLevel +
                ", noLog=" + noLog +
                ", children=" + children +
                '}';
    }


}
