package de.uos.se.designertool.logic.nodeserverlogic;

import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sem on 18.02.2016.
 */
public class ModulflexNodeServerTest
{
    @Test
    public void testMarshalling() throws Exception
    {

        List<ModulflexNode> children = new LinkedList<>();
        ModulflexNode modulflexNode = new ModulflexNode(0, "zero");
        children.add(modulflexNode);
        ModulflexNodeServer modulflexNodeServer = new ModulflexNodeServer(.3, null, null, null, null, children);

        JAXBContext jaxbContext = JAXBContext.newInstance(ModulflexNodeServer.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        marshaller.marshal(modulflexNodeServer, new File("nsTest.xml"));

    }
}