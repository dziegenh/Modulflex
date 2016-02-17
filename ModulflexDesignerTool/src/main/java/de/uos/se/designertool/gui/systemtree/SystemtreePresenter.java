package de.uos.se.designertool.gui.systemtree;

import de.uos.se.designertool.logic.DummyLogic;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javax.inject.Inject;

/**
 *
 * @author dziegenhagen
 */
public class SystemtreePresenter implements Initializable {

    @FXML
    AnchorPane rootPane;

    TreeView treeView;

    @Inject
    DummyLogic someLogic;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // dummy entries
        TreeItem<String> rootItem = new TreeItem<String>("Dummy");
        rootItem.setExpanded(true);
        for (int i = 1; i < 6; i++) {
            TreeItem<String> item = new TreeItem<String>("Sub Dummy " + i);
            rootItem.getChildren().add(item);
        }

        treeView = new TreeView<>(rootItem);

        treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem>() {

            @Override
            public void changed(ObservableValue<? extends TreeItem> observable, TreeItem oldValue, TreeItem newValue) {
                Logger.getLogger(SystemtreePresenter.class.getName()).log(Level.INFO, "Tree selection changed: {0}", newValue);
                someLogic.doSomething();
            }

        });
        rootPane.getChildren().add(treeView);
    }

}
