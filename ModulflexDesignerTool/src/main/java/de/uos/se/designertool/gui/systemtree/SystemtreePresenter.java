package de.uos.se.designertool.gui.systemtree;

import de.uos.se.designertool.logic.TreeLogic;
import de.uos.se.designertool.logic.nodeserverlogic.ModulflexModule;
import de.uos.se.designertool.logic.nodeserverlogic.ModulflexNode;
import de.uos.se.designertool.logic.nodeserverlogic.ModulflexNodeServer;
import de.uos.se.designertool.logic.nodeserverlogic.ModulflexSystemElementType;
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

import java.net.URL;
import java.util.ResourceBundle;
import javax.inject.Inject;

/**
 * @author dziegenhagen
 */
public class SystemtreePresenter
        implements Initializable
{

    @FXML
    AnchorPane rootPane;

    TreeView<ModulflexSystemElementType> treeView;

    ObjectProperty<ModulflexNodeServer> ns;

    ListProperty<ModulflexNode> children;

    ObjectProperty<ModulflexModule> current;

    @Inject
    TreeLogic treeLogic;
    
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

        // dummy entries
        ns = new SimpleObjectProperty<>();
        children = new SimpleListProperty<>();
        current = new SimpleObjectProperty<>();
        TreeItem<ModulflexSystemElementType> rootItem = new TreeItem<>();
        rootItem.setExpanded(true);
        ns.addListener((observable1, oldValue1, newValue1) -> {
            load(newValue1);
        });
        treeView = new TreeView<>(rootItem);
        children.addListener((observable, oldValue, newValue) -> {
            rootItem.getChildren().clear();
            for (ModulflexNode node : newValue)
            {
                TreeItem<ModulflexSystemElementType> nested = new TreeItem<>(node);
                rootItem.getChildren().add(nested);
                for (ModulflexModule module : node.modulesProperty())
                {
                    nested.getChildren().add(new TreeItem<>(module));
                }
            }
        });

        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            ModulflexSystemElementType value = newValue.getValue();
            System.out.println(value);
            
            treeLogic.changeElement(value);
            
            
//            if (value instanceof ModulflexModule)
//                current.setValue((ModulflexModule) value);
        });


        treeView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        rootPane.getChildren().add(treeView);
    }

    private void load(ModulflexNodeServer newValue)
    {
        children.unbind();
        children.bindBidirectional(newValue.childrenProperty());
    }

    public ObjectProperty<ModulflexNodeServer> nodeServerProperty()
    {
        return ns;
    }

    public ObjectProperty<ModulflexModule> currentProperty()
    {
        return current;
    }
}
