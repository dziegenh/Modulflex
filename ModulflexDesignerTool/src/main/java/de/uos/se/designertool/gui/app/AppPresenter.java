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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import javax.inject.Inject;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;

/**
 * @author dziegenhagen
 */
@SuppressWarnings("unused")
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
    private File xmlFile;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        xmlFile = new File("ns.xml");

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
        ns = server;

        nodeServerAddedModule.fireEvent(server);
    }

    private void initListener()
    {
        nodeServerAddedModule.addListener(data
                -> 
                {
                    ns = data;
                    models.clear();
        });
        selectElementModule.addListener(element
                -> 
                {
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
        addFromXSD.addListener(data
                -> 
                {
                    models.put(data.getKey().rootModelProperty().getValue(), data.getValue());
        });
    }

    @FXML
    private void handleExit()
    {
        System.exit(0);
    }

    @FXML
    private void menuSave() throws ParserConfigurationException, JAXBException
    {

        JAXBContext jaxbContext = JAXBContext.newInstance(ModulflexNodeServer.class);

        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller
                .setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, "src/main/resources/config/schema.xsd");
        marshaller.marshal(ns, xmlFile);

        int i = 0;

        Set<XSDModel> keySet = models.keySet();
        for (XSDModel xSDModel : keySet)
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);
            factory.setNamespaceAware(true);

            DocumentBuilder _documentBuilder = factory.newDocumentBuilder();

            Document newDoc = _documentBuilder.newDocument();

            xSDModel.parseToXML(newDoc, null);

            try (FileOutputStream out = new FileOutputStream((i++) + "out.xml"))
            {
                TransformerFactory tFactory = TransformerFactory.newInstance();
                Transformer transformer = tFactory.newTransformer();

                DOMSource source = new DOMSource(newDoc);
                StreamResult result = new StreamResult(out);
                transformer.transform(source, result);
            } catch (IOException | TransformerException e)
            {
                Logger.getLogger(this.getClass().getName())
                        .log(Level.SEVERE, "fatal error while writing output", e);
            }
        }
    }

    @FXML
    private void menuLoad() throws JAXBException
    {
        JAXBContext jaxbContext = JAXBContext.newInstance(ModulflexNodeServer.class);

        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        ModulflexNodeServer ns = (ModulflexNodeServer) unmarshaller.unmarshal(xmlFile);

        nodeServerAddedModule.fireEvent(ns);
        elementChangedModule.fireEvent(ns);
    }

}
