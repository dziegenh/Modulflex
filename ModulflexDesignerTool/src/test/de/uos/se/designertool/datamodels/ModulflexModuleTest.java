package de.uos.se.designertool.datamodels;

import org.junit.Test;

import java.awt.*;
import java.io.File;

import static org.junit.Assert.*;

/**
 * Tests {@linkplain ModulflexModule}.
 */
public class ModulflexModuleTest
{
    private static final ModulflexModule m1;
    private static final ModulflexModule m2;
    private static final ModulflexModule m3;
    private static final ModulflexModule m4;

    static
    {
        m1 = new ModulflexModule(1, "one", new File("file"));
        m2 = new ModulflexModule(1, "one", new File("file"));
        m3 = new ModulflexModule(1, "two", new File("file"));
        m4 = new ModulflexModule(1, "one", new File("file"));
    }

    @Test
    public void testEquals() throws Exception
    {
        assertTrue(m1.equals(m2) && m2.equals(m1) && m1.equals(m1));
        assertTrue(m1.equals(m2) && m2.equals(m4) && m1.equals(m4));
        assertFalse(m1.equals(m3));
        assertFalse(m1.equals(null));
        assertFalse(m3.equals(new Point()));
    }

    @Test
    public void testHashCode() throws Exception
    {
        assertEquals(m1.hashCode(), m2.hashCode());
    }
}