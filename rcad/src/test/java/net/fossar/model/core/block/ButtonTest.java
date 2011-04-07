/*
 * RCad is a software to help manipulating minecraft's redstone.
 * Copyright (C) 2011. mathieu_dot_grenonville_at_gmail_dot_com - nmaupu_at_gmail_dot_com
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.fossar.model.core.block;


import net.fossar.model.core.clock.Clock;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;


public class ButtonTest {
    @Test
    public void testPowerOn() throws Exception {
        Button button = new Button();
        button.powerOff();
        button.powerOn();
        assertEquals(AbstractBlock.POWER_SOURCE, button.getOutput());
    }

    @Test
    public void testPowerOff() throws Exception {
        Button button = new Button();
        button.powerOn();
        button.powerOff();
        assertEquals(AbstractBlock.POWER_OFF, button.getOutput());
    }

    @Test
    public void testAutoPowerOff() throws Exception {
        Button button = new Button();
        button.powerOff();
        button.powerOn();
        assertEquals(AbstractBlock.POWER_SOURCE, button.getOutput());
        for (int i = 0; i < 60; i++) {
            Clock.next();
            assertEquals(AbstractBlock.POWER_SOURCE, button.getOutput());
        }
        //next step, button will power off.
        Clock.next();
        assertEquals(AbstractBlock.POWER_OFF, button.getOutput());

    }


}
