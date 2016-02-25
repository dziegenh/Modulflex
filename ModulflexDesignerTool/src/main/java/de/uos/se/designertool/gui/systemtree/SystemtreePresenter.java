package de.uos.se.designertool.gui.systemtree;

import de.uos.se.designertool.datamodels.ModulflexModule;
import de.uos.se.designertool.datamodels.ModulflexNode;
import de.uos.se.designertool.datamodels.ModulflexNodeServer;
import de.uos.se.designertool.datamodels.ModulflexSystemElementType;
import de.uos.se.designertool.logic.ComponentAddedModule;
import de.uos.se.designertool.logic.NodeAddedModule;
import de.uos.se.designertool.logic.NodeServerAddedModule;
import de.uos.se.designertool.logic.SystemElementTypeChangedModule;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author dziegenhagen
 */
public class SystemtreePresenter
        implements Initializable
{

    @FXML
    AnchorPane rootPane;

    TreeView<ModulflexSystemElementType> treeView;

    ModulflexNodeServer ns;

    ListProperty<ModulflexNode> children;

    ObjectProperty<ModulflexModule> current;

    @Inject
    SystemElementTypeChangedModule logicModule;

    @Inject
    NodeServerAddedModule nodeServerAddedModule;
    @Inject
    NodeAddedModule nodeAddedModule;

    @Inject
    ComponentAddedModule componentAddedModule;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        children = new SimpleListProperty<>();
        current = new SimpleObjectProperty<>();
        TreeItem<ModulflexSystemElementType> rootItem = new TreeItem<>();
        rootItem.setExpanded(true);
        treeView = new TreeView<>(rootItem);
        nodeAddedModule.addListener((data) -> {
            rootItem.getChildren().clear();
            for (ModulflexNode node : ns.childrenProperty())
            {
                TreeItem<ModulflexSystemElementType> nested = new TreeItem<>(node);
                rootItem.getChildren().add(nested);
                for (ModulflexModule module : node.modulesProperty())
                {
                    nested.getChildren().add(new TreeItem<>(module));
                }
            }
        });
        nodeServerAddedModule.addListener(data -> ns = data);
        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            ModulflexSystemElementType value = newValue.getValue();
            System.out.println(value);
            logicModule.fireEvent(value);
        });


        treeView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        rootPane.getChildren().add(treeView);
    }
}
