package de.uos.se.designertool.datamodels;

import org.junit.Test;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests {@linkplain ModulflexNode}.
 */
public class ModulflexNodeTest
{
    private static final ModulflexNode m1;
    private static final ModulflexNode m2;
    private static final ModulflexNode m3;
    private static final ModulflexNode m4;

    static
    {
        ModulflexModule modulflexModule = new ModulflexModule(2, "two", new File("bla"));
        List<ModulflexModule> modulflexModules = new ArrayList<>(1);
        modulflexModules.add(modulflexModule);

        ModulflexModule modulflexModule2 = new ModulflexModule(2, "two", new File("bla"));
        List<ModulflexModule> modulflexModules2 = new ArrayList<>(1);
        modulflexModules2.add(modulflexModule2);

        m1 = new ModulflexNode(0, "zero", modulflexModules);
        m2 = new ModulflexNode(0, "zero", modulflexModules);
        m3 = new ModulflexNode(0, "zero", modulflexModules2);
        m4 = new ModulflexNode(1, "one");
    }

    @Test
    public void testEquals() throws Exception
    {
        assertTrue(m1.equals(m2) && m2.equals(m1) && m1.equals(m1));
        assertTrue(m1.equals(m2) && m2.equals(m3) && m1.equals(m3));
        assertFalse(m1.equals(m4));
        assertFalse(m1.equals(null));
        assertFalse(m4.equals(new Point()));
    }

    @Test
    public void testHashCode() throws Exception
    {
        assertEquals(m1.hashCode(), m3.hashCode());
    }
}