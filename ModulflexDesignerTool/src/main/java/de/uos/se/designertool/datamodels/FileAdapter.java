package de.uos.se.designertool.datamodels;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.io.File;

/**
 * An adapter class to marshal and unmarshal objects into JAXB and back.
 */
public class FileAdapter
        extends XmlAdapter<String, File>
{
    /**
     * {@inheritDoc}
     */
    @Override
    public File unmarshal(String v) throws Exception
    {
        return new File(v);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String marshal(File v) throws Exception
    {
        return v.toString().replace(File.separatorChar, '/');
    }
}
