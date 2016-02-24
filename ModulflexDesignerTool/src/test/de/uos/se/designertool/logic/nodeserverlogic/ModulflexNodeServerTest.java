package de.uos.se.designertool.logic.nodeserverlogic;

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
import java.util.LinkedList;
import java.util.List;

import static junit.framework.TestCase.*;
import static org.junit.Assert.assertTrue;

/**
 * Created by sem on 18.02.2016.
 * <p>
 * Tests some important implementation details of {@linkplain ModulflexNodeServer}.
 */
public class ModulflexNodeServerTest
{
    //top down
    private static final ModulflexNodeServer NODE_SERVER;
    private static final ModulflexNode SIMPLE_SLAVE;
    private static final ModulflexModule DIGITAL_IO;
    private static final ModulflexModule PWM;
    private static final ModulflexModule ADC;
    private static final ModulflexModule DEBUG;
    private static final ModulflexModule SYSTEM_CONFIG;

    static
    {
        //bottom up
        SYSTEM_CONFIG = new ModulflexModule(0, "SystemConfig", new File("./modules/SystemConfig.xml"));
        DEBUG = new ModulflexModule(0, "Debug", new File("./modules/Debug.xml"));
        ADC = new ModulflexModule(0, "ADC", new File("./modules/ADC.xml"));
        PWM = new ModulflexModule(0, "PWM", new File("./modules/PWM.xml"));
        DIGITAL_IO = new ModulflexModule(0, "DigitalIO", new File("./modules/DigitalIO.xml"));
        final List<ModulflexModule> modules = new ArrayList<>(5);
        modules.add(SYSTEM_CONFIG);
        modules.add(DEBUG);
        modules.add(ADC);
        modules.add(PWM);
        modules.add(DIGITAL_IO);

        SIMPLE_SLAVE = new ModulflexNode(0, "simpleSlave", modules);
        final List<ModulflexNode> nodes = new ArrayList<>(1);
        nodes.add(SIMPLE_SLAVE);

        NODE_SERVER = new ModulflexNodeServer(5, false, null, null, null, nodes);
    }

    @Test
    public void testEquals()
    {
        final ModulflexModule systemConfig = new ModulflexModule(0, "SystemConfig",
                                                                 new File("./modules/SystemConfig.xml"));
        final ModulflexModule debug = new ModulflexModule(0, "Debug", new File("./modules/Debug.xml"));
        final ModulflexModule adc = new ModulflexModule(0, "ADC", new File("./modules/ADC.xml"));
        final ModulflexModule pwm = new ModulflexModule(0, "PWM", new File("./modules/PWM.xml"));
        final ModulflexModule digitalIO = new ModulflexModule(0, "DigitalIO", new File("./modules/DigitalIO.xml"));
        final List<ModulflexModule> modules = new ArrayList<>(5);
        modules.add(systemConfig);
        modules.add(debug);
        modules.add(adc);
        modules.add(pwm);
        modules.add(digitalIO);

        final ModulflexNode simpleSlave = new ModulflexNode(0, "simpleSlave", modules);
        List<ModulflexNode> nodes = new ArrayList<>(1);
        nodes.add(simpleSlave);

        final ModulflexNodeServer modulflexNodeServer1 = new ModulflexNodeServer(5, false, null, null, null, nodes);

        final ModulflexModule systemConfig2 = new ModulflexModule(0, "SystemConfig",
                                                                  new File("./modules/SystemConfig.xml"));
        final ModulflexModule debug2 = new ModulflexModule(0, "Debug", new File("./modules/Debug.xml"));
        final ModulflexModule adc2 = new ModulflexModule(0, "ADC", new File("./modules/ADC.xml"));
        final ModulflexModule pwm2 = new ModulflexModule(0, "PWM", new File("./modules/PWM.xml"));
        final ModulflexModule digitalIO2 = new ModulflexModule(0, "DigitalIO", new File("./modules/DigitalIO.xml"));
        final List<ModulflexModule> modules2 = new ArrayList<>(5);
        modules2.add(systemConfig2);
        modules2.add(debug2);
        modules2.add(adc2);
        modules2.add(pwm2);
        modules2.add(digitalIO2);

        final ModulflexNode simpleSlave2 = new ModulflexNode(0, "simpleSlave", modules);
        List<ModulflexNode> nodes2 = new ArrayList<>(1);
        nodes2.add(simpleSlave);

        final ModulflexNodeServer modulflexNodeServer2 = new ModulflexNodeServer(5, false, null, null, null, nodes2);

        final ModulflexNodeServer modulflexNodeServer3 = new ModulflexNodeServer(5, false, null, "", null,
                                                                                 new LinkedList<>());

        assertTrue(NODE_SERVER.equals(modulflexNodeServer1) && modulflexNodeServer1
                .equals(modulflexNodeServer2) && NODE_SERVER.equals(modulflexNodeServer2));
        assertTrue(NODE_SERVER.equals(NODE_SERVER));
        assertFalse(modulflexNodeServer1.equals(null));
        assertFalse(modulflexNodeServer3.equals(new Object()));
        assertFalse(modulflexNodeServer1.equals(modulflexNodeServer3));
    }

    @Test
    public void testHashCode()
    {
        final ModulflexModule systemConfig = new ModulflexModule(0, "SystemConfig",
                                                                 new File("./modules/SystemConfig.xml"));
        final ModulflexModule debug = new ModulflexModule(0, "Debug", new File("./modules/Debug.xml"));
        final ModulflexModule adc = new ModulflexModule(0, "ADC", new File("./modules/ADC.xml"));
        final ModulflexModule pwm = new ModulflexModule(0, "PWM", new File("./modules/PWM.xml"));
        final ModulflexModule digitalIO = new ModulflexModule(0, "DigitalIO", new File("./modules/DigitalIO.xml"));
        final List<ModulflexModule> modules = new ArrayList<>(5);
        modules.add(systemConfig);
        modules.add(debug);
        modules.add(adc);
        modules.add(pwm);
        modules.add(digitalIO);

        final ModulflexNode simpleSlave = new ModulflexNode(0, "simpleSlave", modules);
        List<ModulflexNode> nodes = new ArrayList<>(1);
        nodes.add(simpleSlave);

        final ModulflexNodeServer modulflexNodeServer = new ModulflexNodeServer(5, false, null, null, null, nodes);
        assertEquals(NODE_SERVER.hashCode(), modulflexNodeServer.hashCode());
    }

    /**
     * Tests marshalling by writing to a temporary file, reading then and eventually compare what was hold in
     * memory and what was saved on disk.
     */
    @Test
    public void testMarshalling()
    {
        try
        {
            File xmlFile = Files.createTempFile(null, ".xml").toFile();

            ModulflexNodeServer modulflexNodeServer = NODE_SERVER;

            JAXBContext jaxbContext = JAXBContext.newInstance(ModulflexNodeServer.class);

            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller
                    .setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, "src/main/resources/config/schema.xsd");
            marshaller.marshal(modulflexNodeServer, xmlFile);

            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            ModulflexNodeServer unmarshalled = (ModulflexNodeServer) unmarshaller.unmarshal(xmlFile);

            assertEquals(modulflexNodeServer, unmarshalled);
            assertEquals(modulflexNodeServer.hashCode(), unmarshalled.hashCode());
        } catch (Exception e)
        {
            fail(e.getMessage());
        }
    }

}