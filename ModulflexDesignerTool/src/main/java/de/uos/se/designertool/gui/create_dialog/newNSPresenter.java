package de.uos.se.designertool.gui.create_dialog;

import de.uos.se.designertool.datamodels.ModulflexNode;
import de.uos.se.designertool.datamodels.ModulflexNodeServer;
import de.uos.se.designertool.logic.ComponentAddedModule;
import de.uos.se.designertool.logic.ModulflexNodeAddedModule;
import de.uos.se.designertool.logic.NodeChangedModule;
import de.uos.se.designertool.logic.NodeServerAddedModule;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.converter.NumberStringConverter;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by sem on 10.03.2016.
 */
public class newNSPresenter
        implements Initializable
{
    public static final String NEW_NODE = "new Node";
    @Inject
    NodeServerAddedModule nodeServerAddedModule;
    @Inject
    ComponentAddedModule componentAddedModule;
    @Inject
    NodeChangedModule nodeChangedModule;
    @Inject
    ModulflexNodeAddedModule nodeAdded;
    @FXML
    private TextField nameField;
    @FXML
    private TextField cycleTime;
    @FXML
    private ComboBox<?> profilingBox;
    @FXML
    private TextField logDir;
    @FXML
    private TextField logLevel;
    @FXML
    private ComboBox<?> noLog;
    private ModulflexNodeServer nodeServer;
    private int globalID;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        globalID = 0;
        nodeServerAddedModule.addListener(data -> {
            nodeServer = data;
            cycleTime.textProperty().bindBidirectional(data.cycleTimeProperty(), new NumberStringConverter());
            data.nameProperty().bindBidirectional(nameField.textProperty());
            data.profilingProperty().bind(profilingBox.getSelectionModel().selectedItemProperty().isEqualTo("true"));
            data.logDirProperty().bindBidirectional(logDir.textProperty());
            data.logLevelProperty().bindBidirectional(logLevel.textProperty());
            data.noLogProperty().bind(noLog.getSelectionModel().selectedItemProperty().isEqualTo("true"));
        });
    }

    public void addNode()
    {
        ModulflexNode node = new ModulflexNode(globalID++, NEW_NODE);
        nodeServer.childrenProperty().add(node);
        componentAddedModule.fireEvent(node);
        nodeAdded.fireEvent(node);
        nodeChangedModule.fireEvent(node);
    }
}
