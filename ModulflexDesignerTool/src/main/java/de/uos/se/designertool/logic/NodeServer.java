package de.uos.se.designertool.logic;

import de.uos.se.designertool.logic.nodeserverlogic.ModulflexNode;
import javafx.beans.property.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Created by sem on 18.02.2016.
 */
public class NodeServer
{
    private final DoubleProperty cycleTime;

    private final BooleanProperty profiling;

    private final StringProperty logDir;

    private final StringProperty logLevel;

    private final BooleanProperty noLog;

    private final List<ModulflexNode> children;

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
    private NodeServer(double cycleTime, Boolean profiling, String logDir, String logLevel, Boolean noLog, List<ModulflexNode> children)
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

        if (Objects.isNull(children))
        {
            this.children = new LinkedList<>();
        } else
        {
            this.children = children;
        }
    }

    public double getCycleTime()
    {
        return cycleTime.get();
    }

    public DoubleProperty cycleTimeProperty()
    {
        return cycleTime;
    }

    public boolean getProfiling()
    {
        return profiling.get();
    }

    public BooleanProperty profilingProperty()
    {
        return profiling;
    }

    public String getLogDir()
    {
        return logDir.get();
    }

    public StringProperty logDirProperty()
    {
        return logDir;
    }

    public String getLogLevel()
    {
        return logLevel.get();
    }

    public StringProperty logLevelProperty()
    {
        return logLevel;
    }

    public boolean getNoLog()
    {
        return noLog.get();
    }

    public BooleanProperty noLogProperty()
    {
        return noLog;
    }

    public List<ModulflexNode> getChildren()
    {
        return children;
    }
}
