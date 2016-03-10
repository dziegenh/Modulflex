package de.uos.se.designertool.gui.app;

import de.uos.se.designertool.datamodels.ModulflexModule;
import de.uos.se.designertool.datamodels.ModulflexNode;
import de.uos.se.designertool.datamodels.ModulflexNodeServer;
import de.uos.se.designertool.datamodels.ModulflexSystemElementType;
import de.uos.se.designertool.gui.create_dialog.newNSView;
import de.uos.se.designertool.gui.systemtree.SystemtreeView;
import de.uos.se.designertool.logic.ComponentAddedModule;
import de.uos.se.designertool.logic.NodeChangedModule;
import de.uos.se.designertool.logic.NodeServerAddedModule;
import de.uos.se.designertool.logic.SystemElementTypeChangedModule;
import de.uos.se.xsd2gui.model_generators.*;
import de.uos.se.xsd2gui.models.RootModel;
import de.uos.se.xsd2gui.models.XSDModel;
import de.uos.se.xsd2gui.xsdparser.AbstractWidgetFactory;
import de.uos.se.xsd2gui.xsdparser.DefaultWidgetFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.w3c.dom.Document;

import javax.inject.Inject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    Map<ModulflexNode, Pane> nodeModels;
    AbstractWidgetFactory widgetFactory;
    ModulflexSystemElementType currentSelected;
    @Inject
    SystemElementTypeChangedModule logicModule;
    @Inject
    NodeServerAddedModule nodeServerAddedModule;
    @Inject
    NodeChangedModule nodeChangedModule;
    @Inject
    ComponentAddedModule componentAddedModule;
    private String XSD_BASE_DIR;
    private DocumentBuilder documentBuilder;
    private newNSView newNSView;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        URL configDir = Thread.currentThread().getContextClassLoader().getResource("config");
        // todo check dir existance :)
        try
        {
            this.XSD_BASE_DIR = configDir.toURI().getPath() + File.separator;
        } catch (URISyntaxException e)
        {
            throw new RuntimeException(e);
        }
        nodeServerAddedModule.addListener(data -> {
            ns = data;
            models.clear();
            nodeModels.clear();
            newNSView = new newNSView();
        });
        models = new HashMap<>();
        nodeModels = new HashMap<>();
        widgetFactory = new DefaultWidgetFactory();
        widgetFactory.addWidgetGenerator(new BasicAttributeParser());
        widgetFactory.addWidgetGenerator(new SimpleTypeParser());
        widgetFactory.addWidgetGenerator(new ContainerParser());
        widgetFactory.addWidgetGenerator(new BasicSequenceParser());
        widgetFactory.addWidgetGenerator(new CustomTypesParser("ct:", XSD_BASE_DIR + "predefined\\CommonTypes.xsd"));
        widgetFactory
                .addWidgetGenerator(new CustomTypesParser("st:", XSD_BASE_DIR + "predefined\\StructuredTypes.xsd"));

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringComments(true);
        factory.setIgnoringElementContentWhitespace(true);
        factory.setNamespaceAware(true);

        try
        {
            documentBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e)
        {
            throw new RuntimeException(e);
        }
        systemTreeView = new SystemtreeView();
        //
        //        currentSelected.addListener((observable1, oldValue1, newValue1) -> {
        //            System.out.println(observable1);
        //            rightPane.contentProperty().setValue(models.get().get(observable1.getValue
        // ().rootModelProperty().get()));
        //        });
        logicModule.addListener(element -> {
            if (element instanceof ModulflexModule)
            {
                rightPane.contentProperty()
                         .setValue(models.get(((ModulflexModule) element).rootModelProperty().getValue()));
            } else if (element instanceof ModulflexNode)
            {
                Pane v = nodeModels.get((ModulflexNode) element);
                rightPane.contentProperty().setValue(v);
            } else if (element instanceof ModulflexNodeServer)
            {
                rightPane.contentProperty().setValue(newNSView.getView());
            }
        });
        nodeChangedModule.addListener(data -> nodeModels.put(data, new VBox(10, new Label(data.toString()))));

        leftContent.getChildren().add(systemTreeView.getView());
        ModulflexNodeServer server = new ModulflexNodeServer();

        nodeServerAddedModule.fireEvent(server);
        //addDummys();
    }


    private void addDummys()
    {
        String[] filenames = new String[] {XSD_BASE_DIR + "components\\PWM.xsd", XSD_BASE_DIR + "components\\AnalogDigitalConverter.xsd", XSD_BASE_DIR + "components\\DigitalIO.xsd"};
        Map<XSDModel, Pane> dummyModels = new HashMap<>();
        for (int i = 0; i < 6; i++)
        {
            ModulflexNode myNode = new ModulflexNode(i, "myNode");
            for (int j = 0; j < filenames.length; j++)
            {
                String filename = filenames[j];
                try
                {
                    VBox currentContent = new VBox();
                    Document doc = documentBuilder.parse(filename);
                    // Generated widgets are added to the root node
                    RootModel model = widgetFactory
                            .parseXsd(doc, currentContent, filename.replaceAll("\\" + File.separator, "/"));
                    dummyModels.put(model, currentContent);
                    ModulflexModule module = new ModulflexModule(i, "testname", new File(
                            "." + File.separator + "testname" + i + "xsd"), model);
                    myNode.modulesProperty().add(module);
                    componentAddedModule.fireEvent(module);
                } catch (Exception ex)
                {
                    Logger.getLogger(AppPresenter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            ns.childrenProperty().add(myNode);
            nodeChangedModule.fireEvent(myNode);
        }
        models = dummyModels;

    }

    @FXML
    private void handleExit()
    {
        System.exit(0);
    }

}
