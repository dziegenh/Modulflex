package de.uos.se.designertool.gui.create_dialog;

import de.uos.se.designertool.datamodels.ModulflexNode;
import de.uos.se.designertool.logic.ModulflexNodeSelectedModule;
import de.uos.se.designertool.logic.NodeChangedModule;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by sem on 10.03.2016.
 */
public class newNodePresenter
        implements Initializable
{
    @Inject
    ModulflexNodeSelectedModule nodeSelected;
    @Inject
    NodeChangedModule nodeChangedModule;
    @FXML
    private TextField nodeName;
    private ModulflexNode currentNode;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        nodeSelected.addListener(data -> {
            if (currentNode != null)
                currentNode.nameProperty().unbindBidirectional(nodeName.textProperty());
            nodeName.textProperty().bindBidirectional(data.nameProperty());
            currentNode = data;
        });
        nodeName.textProperty()
                .addListener((observable, oldValue, newValue) -> nodeChangedModule.fireEvent(currentNode));
    }
}
