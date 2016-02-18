package de.uos.se.designertool.logic.nodeserverlogic;

import javafx.beans.property.*;

import java.util.List;
import java.util.Objects;

/**
 * Created by sem on 18.02.2016.
 * <p>
 * This class represents a node server. This is the top-level element in a topology.
 * For convenience all values are handled by Properties (See {@linkplain Property}). This also means values can be changed after creation.
 */
public class ModulflexNodeServer
{
    private final DoubleProperty cycleTime;

    private final BooleanProperty profiling;

    private final StringProperty logDir;

    private final StringProperty logLevel;

    private final BooleanProperty noLog;

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
    private ModulflexNodeServer(double cycleTime, Boolean profiling, String logDir, String logLevel, Boolean noLog, List<ModulflexNode> children)
    {
        this.cycleTime = new SimpleDoubleProperty(cycleTime);

        if (Objects.isNull(profiling))
        {
            this.profiling = new SimpleBooleanProperty();
        } else
        {
            this.profiling = new SimpleBooleanProperty(profiling);
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
            this.noLog = new SimpleBooleanProperty();
        } else
        {
            this.noLog = new SimpleBooleanProperty(noLog);
        }

        this.children = new SimpleListProperty<>();
        if (Objects.nonNull(children))
        {
            this.children.addAll(children);
        }
    }

    /**
     * Convenience constructor that only needs arguments that are required for a node server.
     *
     * @param cycleTime
     *         The cycle time for the node server.
     */
    public ModulflexNodeServer(double cycleTime)
    {
        this(cycleTime, null, null, null, null, null);
    }

    public DoubleProperty cycleTimeProperty()
    {
        return cycleTime;
    }

    public BooleanProperty profilingProperty()
    {
        return profiling;
    }

    public StringProperty logDirProperty()
    {
        return logDir;
    }

    public StringProperty logLevelProperty()
    {
        return logLevel;
    }

    public BooleanProperty noLogProperty()
    {
        return noLog;
    }

    public ListProperty<ModulflexNode> childrenProperty()
    {
        return children;
    }

    @Override
    public int hashCode()
    {
        int result = cycleTime.hashCode();
        result = 31 * result + profiling.hashCode();
        result = 31 * result + logDir.hashCode();
        result = 31 * result + logLevel.hashCode();
        result = 31 * result + noLog.hashCode();
        result = 31 * result + children.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        ModulflexNodeServer that = (ModulflexNodeServer) o;

        if (! cycleTime.equals(that.cycleTime))
            return false;
        if (! profiling.equals(that.profiling))
            return false;
        if (! logDir.equals(that.logDir))
            return false;
        if (! logLevel.equals(that.logLevel))
            return false;
        if (! noLog.equals(that.noLog))
            return false;
        return children.equals(that.children);

    }

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
