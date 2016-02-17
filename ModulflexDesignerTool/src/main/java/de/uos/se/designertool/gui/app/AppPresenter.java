package de.uos.se.designertool.gui.app;

import de.uos.se.designertool.gui.systemtree.SystemtreeView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author dziegenhagen
 */
public class AppPresenter implements Initializable {

    @FXML
    AnchorPane leftContent;
    
    @FXML
    AnchorPane rightContent;

    SystemtreeView systemTreeView;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        systemTreeView = new SystemtreeView();
        leftContent.getChildren().add(systemTreeView.getView());
    }

}
