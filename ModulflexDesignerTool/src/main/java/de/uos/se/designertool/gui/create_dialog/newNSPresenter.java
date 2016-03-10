package de.uos.se.designertool.gui.create_dialog;

import de.uos.se.designertool.logic.NodeServerAddedModule;
import javafx.fxml.Initializable;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by sem on 10.03.2016.
 */
public class newNSPresenter
        implements Initializable
{
    @Inject
    NodeServerAddedModule nodeServerAddedModule;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        nodeServerAddedModule.addListener(data -> {

        });
    }
}
