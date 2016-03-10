package de.uos.se.designertool.gui.app;

import de.uos.se.designertool.datamodels.ModulflexModule;
import de.uos.se.designertool.datamodels.ModulflexNode;
import de.uos.se.designertool.datamodels.ModulflexNodeServer;
import de.uos.se.designertool.datamodels.ModulflexSystemElementType;
import de.uos.se.designertool.gui.create_dialog.newnode.NewnodeView;
import de.uos.se.designertool.gui.create_dialog.newns.NewnsView;
import de.uos.se.designertool.gui.systemtree.SystemtreeView;
import de.uos.se.designertool.logic.*;
import de.uos.se.xsd2gui.models.XSDModel;
import de.uos.se.xsd2gui.xsdparser.AbstractWidgetFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import javax.inject.Inject;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author dziegenhagen
 */
@SuppressWarnings ("unused")
public class AppPresenter
        implements Initializable
{

    @FXML
    AnchorPane leftContent;
    @FXML
    ScrollPane rightPane;
    SystemtreeView systemTreeView;
    ModulflexNodeServer ns;
    Map<XSDModel, Pane> models;
    NewnodeView nodeView;
    AbstractWidgetFactory widgetFactory;
    ModulflexSystemElementType currentSelected;
    @Inject
    SystemElementTypeSelectedModule selectElementModule;
    @Inject
    NodeServerAddedModule nodeServerAddedModule;
    @Inject
    ElementChangedModule elementChangedModule;
    @Inject
    ComponentAddedModule componentAddedModule;
    @Inject
    ModulflexNodeAddedModule nodeAdded;
    @Inject
    ModulflexNodeSelectedModule nodeSelected;
    @Inject
    AddFromXSDModule addFromXSD;

    private NewnsView newNSView;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        initState();
        initListener();
    }

    private void initState()
    {
        models = new HashMap<>();
        nodeView = new NewnodeView();
        systemTreeView = new SystemtreeView();
        newNSView = new NewnsView();
        rightPane.contentProperty().setValue(newNSView.getView());
        leftContent.getChildren().add(systemTreeView.getView());
        ModulflexNodeServer server = new ModulflexNodeServer();

        nodeServerAddedModule.fireEvent(server);
    }

    private void initListener()
    {
        nodeServerAddedModule.addListener(data -> {
            ns = data;
            models.clear();
        });
        selectElementModule.addListener(element -> {
            if (element instanceof ModulflexModule)
            {
                rightPane.contentProperty()
                         .setValue(models.get(((ModulflexModule) element).rootModelProperty().getValue()));
            } else if (element instanceof ModulflexNode)
            {
                rightPane.contentProperty().setValue(nodeView.getView());
                nodeSelected.fireEvent((ModulflexNode) element);
            } else if (element instanceof ModulflexNodeServer)
            {
                rightPane.contentProperty().setValue(newNSView.getView());
            }
        });
        addFromXSD.addListener(data -> {
            models.put(data.getKey().rootModelProperty().getValue(), data.getValue());
        });
    }

    @FXML
    private void handleExit()
    {
        System.exit(0);
    }

}
