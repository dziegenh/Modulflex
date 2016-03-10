package de.uos.se.designertool.gui.systemtree;

import de.uos.se.designertool.datamodels.ModulflexModule;
import de.uos.se.designertool.datamodels.ModulflexNode;
import de.uos.se.designertool.datamodels.ModulflexNodeServer;
import de.uos.se.designertool.datamodels.ModulflexSystemElementType;
import de.uos.se.designertool.logic.ComponentAddedModule;
import de.uos.se.designertool.logic.NodeChangedModule;
import de.uos.se.designertool.logic.NodeServerAddedModule;
import de.uos.se.designertool.logic.SystemElementTypeSelectedModule;
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
    SystemElementTypeSelectedModule selectElementModule;

    @Inject
    NodeServerAddedModule nodeServerAddedModule;
    @Inject
    NodeChangedModule nodeChangedModule;

    @Inject
    ComponentAddedModule componentAddedModule;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        children = new SimpleListProperty<>();
        current = new SimpleObjectProperty<>();
        componentAddedModule.addListener((data) -> {
            TreeItem<ModulflexSystemElementType> rootItem = treeView.getRoot();
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
        nodeChangedModule.addListener(data -> treeView.refresh());
        nodeServerAddedModule.addListener(data -> {
            rootPane.getChildren().remove(treeView);
            ns = data;
            TreeItem<ModulflexSystemElementType> newRootItem = new TreeItem<>(ns);
            newRootItem.setExpanded(true);
            treeView = new TreeView<>(newRootItem);
            treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                ModulflexSystemElementType value = newValue.getValue();
                System.out.println(oldValue);
                System.out.println(newValue);
                selectElementModule.fireEvent(value);


            });
                                              treeView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
                                              rootPane.getChildren().add(treeView);
    }

        );
    }
}
