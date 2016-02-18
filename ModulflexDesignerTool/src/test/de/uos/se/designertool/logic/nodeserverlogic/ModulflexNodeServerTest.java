package de.uos.se.designertool.logic.nodeserverlogic;

import com.sun.javafx.collections.ObservableListWrapper;
import de.uos.se.designertool.datamodels.ModulflexModule;
import de.uos.se.designertool.datamodels.ModulflexNode;
import de.uos.se.designertool.datamodels.ModulflexNodeServer;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

/**
 * Created by sem on 18.02.2016.
 */
public class ModulflexNodeServerTest
{
    /**
     * Tests marshalling by writing to a temporary file, reading then and eventually compare what was hold in
     * memory and what was saved on disk.
     */
    @Test
    public void testMarshallingAndHashCode()
    {
        try
        {
            final File xmlFile = Files.createTempFile(null, ".xml").toFile();

            // node server and its children
            List<ModulflexNode> nsChildren = new ArrayList<>(2);
            ModulflexNode modulflexNode = new ModulflexNode(0, "simpleSlave");
            nsChildren.add(modulflexNode);

            // the children of the node
            List<ModulflexModule> modulflexModules = new ArrayList<>(5);
            modulflexModules.add(new ModulflexModule(0, "DigitalIO", new File("./modules/DigitalIO.xml")));
            modulflexModules.add(new ModulflexModule(0, "PWM", new File("./modules/PWM.xml")));
            modulflexModules.add(new ModulflexModule(0, "ADC", new File("./modules/ADC.xml")));
            modulflexModules.add(new ModulflexModule(0, "Debug", new File("./modules/Debug.xml")));
            modulflexModules.add(new ModulflexModule(0, "SystemConfig", new File("./modules/SystemConfig.xml")));
            modulflexNode.modulesProperty().setValue(new ObservableListWrapper<>(modulflexModules));

            ModulflexNodeServer modulflexNodeServer = new ModulflexNodeServer(5, false, null, null, null, nsChildren);

            JAXBContext jaxbContext = JAXBContext.newInstance(ModulflexNodeServer.class);

            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller
                    .setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, "src/main/resources/config/schema.xsd");
            marshaller.marshal(modulflexNodeServer, xmlFile);

            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            ModulflexNodeServer unmarshal = (ModulflexNodeServer) unmarshaller.unmarshal(xmlFile);

            assertEquals(modulflexNodeServer, unmarshal);
            assertEquals(modulflexNodeServer.hashCode(), unmarshal.hashCode());
        } catch (Exception e)
        {
            fail(e.getMessage());
        }
    }
}