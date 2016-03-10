package de.uos.se.designertool.gui.create_dialog.newnode;

import de.uos.se.designertool.datamodels.ModulflexModule;
import de.uos.se.designertool.datamodels.ModulflexNode;
import de.uos.se.designertool.logic.AddFromXSDModule;
import de.uos.se.designertool.logic.ComponentAddedModule;
import de.uos.se.designertool.logic.ElementChangedModule;
import de.uos.se.designertool.logic.ModulflexNodeSelectedModule;
import de.uos.se.xsd2gui.model_generators.*;
import de.uos.se.xsd2gui.models.RootModel;
import de.uos.se.xsd2gui.xsdparser.DefaultWidgetFactory;
import de.uos.se.xsd2gui.xsdparser.IWidgetGenerator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import org.xml.sax.SAXException;

import javax.inject.Inject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by sem on 10.03.2016.
 */
public class NewnodePresenter
        implements Initializable
{
    public static final String XSD_FILE_TYPE = ".xsd";
    @Inject
    ModulflexNodeSelectedModule nodeSelected;
    @Inject
    ElementChangedModule elementChangedModule;
    @Inject
    AddFromXSDModule addFromXSD;
    @Inject
    ComponentAddedModule componentAddedModule;
    @FXML
    private TextField nodeName;
    @FXML
    private ComboBox<File> moduleFC;

    @FXML
    private Button addModule;
    private ModulflexNode currentNode;
    private DocumentBuilder documentBuilder;
    private DefaultWidgetFactory widgetFactory;
    private String XSD_BASE_DIR;
    private int globalID;
    private IWidgetGenerator lastCustomParser;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        init();
        nodeSelected.addListener(data -> {
            if (currentNode != null)
                currentNode.nameProperty().unbindBidirectional(nodeName.textProperty());
            nodeName.textProperty().bindBidirectional(data.nameProperty());
            currentNode = data;
        });
        nodeName.textProperty()
                .addListener((observable, oldValue, newValue) -> elementChangedModule.fireEvent(currentNode));
    }

    private void init()
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
        String xsdFilesname = XSD_BASE_DIR + "\\components";
        File dir = new File(xsdFilesname);
        System.out.println(dir.canRead());
        System.out.println(dir);
        moduleFC.getItems().addAll(dir.listFiles(f -> f.isFile() && f.toString().endsWith(XSD_FILE_TYPE)));
        moduleFC.getSelectionModel().selectFirst();
    }

    @FXML
    public void addModule()
    {
        Pane root = new VBox();
        File selectedItem = moduleFC.getSelectionModel().getSelectedItem();
        widgetFactory.removeWidgetGenerator(lastCustomParser);
        lastCustomParser = new CustomTypesParser("", selectedItem.getPath());
        widgetFactory.addWidgetGenerator(lastCustomParser);
        try
        {
            RootModel rootModel = widgetFactory.parseXsd(documentBuilder.parse(selectedItem), root,
                                                         selectedItem.getPath().replaceAll("\\" + File.separator, "/"));
            String absolutePath = selectedItem.getAbsolutePath();
            int beginIndex = absolutePath.lastIndexOf(File.separator) + 1;
            String name = absolutePath
                    .substring(beginIndex >= 0 ? beginIndex : 0, absolutePath.lastIndexOf(XSD_FILE_TYPE));
            ModulflexModule module = new ModulflexModule(globalID++, name, selectedItem, rootModel);
            currentNode.modulesProperty().add(module);
            addFromXSD.fireEvent(new Pair<>(module, root));
            componentAddedModule.fireEvent(currentNode);
        } catch (SAXException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
