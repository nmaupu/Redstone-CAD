package net.fossar.core.block;

import junit.framework.TestCase;


public class LeverTest extends TestCase {
    public void testPowerOn() throws Exception {
        Lever lever = new Lever();
        lever.powerOff();
        lever.powerOn();
        assertEquals(AbstractBlock.POWER_SOURCE, lever.getOutput());
    }

    public void testPowerOff() throws Exception {
        Lever lever = new Lever();
        lever.powerOn();
        lever.powerOff();
        assertEquals(AbstractBlock.POWER_OFF, lever.getOutput());
    }
}
