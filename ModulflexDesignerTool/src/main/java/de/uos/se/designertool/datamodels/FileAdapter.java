package de.uos.se.designertool.datamodels;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.io.File;

/**
 * Created by sem on 18.02.2016.
 */
public class FileAdapter
        extends XmlAdapter<String, File>
{
    @Override
    public File unmarshal(String v) throws Exception
    {
        return new File(v);
    }

    @Override
    public String marshal(File v) throws Exception
    {
        return v.toString().replace(File.separatorChar, '/');
    }
}
