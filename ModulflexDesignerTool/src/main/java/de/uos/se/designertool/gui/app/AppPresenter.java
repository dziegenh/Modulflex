package de.uos.se.designertool.gui.app;

import de.uos.se.designertool.datamodels.ModulflexModule;
import de.uos.se.designertool.datamodels.ModulflexNode;
import de.uos.se.designertool.datamodels.ModulflexNodeServer;
import de.uos.se.designertool.datamodels.ModulflexSystemElementType;
import de.uos.se.designertool.gui.systemtree.SystemtreeView;
import de.uos.se.designertool.logic.ILogicModuleListener;
import de.uos.se.designertool.logic.ModulflexDesignerLogic;
import de.uos.se.designertool.logic.NodeServerAddedModule;
import de.uos.se.designertool.logic.SystemElementTypeChangedModule;
import de.uos.se.xsd2gui.generators.*;
import de.uos.se.xsd2gui.models.RootModel;
import de.uos.se.xsd2gui.models.XSDModel;
import de.uos.se.xsd2gui.xsdparser.WidgetFactory;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

    @Inject
    ModulflexDesignerLogic logic;
    @FXML
    AnchorPane leftContent;
    @FXML
    ScrollPane rightPane;
    SystemtreeView systemTreeView;
    ObjectProperty<ModulflexNodeServer> ns;
    ListProperty<ModulflexNode> elems;
    ObjectProperty<Map<XSDModel, Pane>> models;
    WidgetFactory widgetFactory;
    ObjectProperty<ModulflexModule> currentSelected;
    @Inject
    SystemElementTypeChangedModule logicModule;
    private String XSD_BASE_DIR;
    private DocumentBuilder documentBuilder;

    @Inject
    NodeServerAddedModule naModule;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        URL configDir = Thread.currentThread().getContextClassLoader().getResource("config");
        // todo check dir existance :)
        try
        {
            this.XSD_BASE_DIR = configDir.toURI().getPath() + File.separator;
        }
        catch (URISyntaxException e)
        {
            throw new RuntimeException(e);
        }

        models = new SimpleObjectProperty<>(new HashMap<>());
        widgetFactory = new WidgetFactory();
        widgetFactory.addWidgetGenerator(new BasicAttributeParser());
        widgetFactory.addWidgetGenerator(new SimpleTypeParser());
        widgetFactory.addWidgetGenerator(new ContainerParser());
        widgetFactory.addWidgetGenerator(new BasicSequenceParser());
        widgetFactory.addWidgetGenerator(
                new CustomTypesParser("ct:", XSD_BASE_DIR + "predefined\\CommonTypes.xsd"));
        widgetFactory.addWidgetGenerator(
                new CustomTypesParser("st:", XSD_BASE_DIR + "predefined\\StructuredTypes.xsd"));

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringComments(true);
        factory.setIgnoringElementContentWhitespace(true);
        factory.setNamespaceAware(true);

        try
        {
            documentBuilder = factory.newDocumentBuilder();
        }
        catch (ParserConfigurationException e)
        {
            throw new RuntimeException(e);
        }
        systemTreeView = new SystemtreeView();

        currentSelected = new SimpleObjectProperty<>();
        ns = new SimpleObjectProperty<>();
        elems = new SimpleListProperty<>();
        //
        //        currentSelected.addListener((observable1, oldValue1, newValue1) -> {
        //            System.out.println(observable1);
        //            rightPane.contentProperty().setValue(models.get().get(observable1.getValue
        // ().rootModelProperty().get()));
        //        });

        logicModule.addListener(new ILogicModuleListener<ModulflexSystemElementType>()
        {

            @Override
            public void eventFired(ModulflexSystemElementType element)
            {
                if (element instanceof ModulflexModule)
                {
                    rightPane.contentProperty().setValue(models.get()
                                                               .get(((ModulflexModule) element)
                                                                            .rootModelProperty()
                                                                            .getValue()));
                }
            }

        });


        leftContent.getChildren().add(systemTreeView.getView());
        logic.nodeServerProperty().bindBidirectional(ns);
        ns.addListener((observable, oldValue, newValue) -> load(newValue));
        ModulflexNodeServer server = new ModulflexNodeServer();
        naModule.fireEvent(server);
        load(server);
        ns.setValue(server);
        addDummys();
    }

    private void load(ModulflexNodeServer newValue)
    {
        elems.unbind();
        elems.bindBidirectional(newValue.childrenProperty());
    }

    private void addDummys()
    {
        String[] filenames = new String[] {XSD_BASE_DIR + "components\\PWM.xsd",
                                           XSD_BASE_DIR + "components\\AnalogDigitalConverter.xsd",
                                           XSD_BASE_DIR + "components\\DigitalIO.xsd"};
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
                    RootModel model = widgetFactory.parseXsd(doc, currentContent,
                                                             filename.replaceAll(
                                                                     "\\" + File.separator, "/"));
                    dummyModels.put(model, currentContent);
                    myNode.modulesProperty().add(new ModulflexModule(i, "testname", new File(
                            "." + File.separator + "testname" + i + "xsd"), model));
                }
                catch (Exception ex)
                {
                    Logger.getLogger(AppPresenter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            elems.add(myNode);
        }
        models.setValue(dummyModels);

    }

    @FXML
    private void handleExit()
    {
        System.exit(0);
    }

}
