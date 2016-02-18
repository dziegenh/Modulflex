package de.uos.se.designertool.logic;

import de.uos.se.designertool.datamodels.ModulflexNodeServer;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Created by sem on 18.02.2016.
 */
public class ModulflexDesignerLogic
{
    private final ObjectProperty<ModulflexNodeServer> _ns;

    public ModulflexDesignerLogic()
    {
        this(null);
    }

    public ModulflexDesignerLogic(ModulflexNodeServer ns)
    {
        this._ns = new SimpleObjectProperty<>(ns);
    }

    public ObjectProperty<ModulflexNodeServer> nodeServerProperty()
    {
        return _ns;
    }
}
