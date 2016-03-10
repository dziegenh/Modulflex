package de.uos.se.designertool.gui.create_dialog.newns;

import de.uos.se.designertool.datamodels.ModulflexNode;
import de.uos.se.designertool.datamodels.ModulflexNodeServer;
import de.uos.se.designertool.logic.ComponentAddedModule;
import de.uos.se.designertool.logic.ElementChangedModule;
import de.uos.se.designertool.logic.ModulflexNodeAddedModule;
import de.uos.se.designertool.logic.NodeServerAddedModule;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.util.converter.NumberStringConverter;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by sem on 10.03.2016.
 */
public class NewnsPresenter
        implements Initializable
{
    public static final String NEW_NODE = "new Node";
    @Inject
    NodeServerAddedModule nodeServerAddedModule;
    @Inject
    ComponentAddedModule componentAddedModule;
    @Inject
    ElementChangedModule elementChangedModule;
    @Inject
    ModulflexNodeAddedModule nodeAdded;
    @FXML
    private TextField nameField;
    @FXML
    private TextField cycleTime;
    @FXML
    private CheckBox profilingBox;
    @FXML
    private TextField logDir;
    @FXML
    private TextField logLevel;
    @FXML
    private CheckBox noLog;
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
            data.profilingProperty().bindBidirectional(profilingBox.selectedProperty());
            data.logDirProperty().bindBidirectional(logDir.textProperty());
            data.logLevelProperty().bindBidirectional(logLevel.textProperty());
            data.noLogProperty().bindBidirectional(noLog.selectedProperty());
        });
    }

    @FXML
    public void addNode()
    {
        ModulflexNode node = new ModulflexNode(globalID++, NEW_NODE);
        nodeServer.childrenProperty().add(node);
        componentAddedModule.fireEvent(node);
        nodeAdded.fireEvent(node);
        elementChangedModule.fireEvent(node);
    }
}
