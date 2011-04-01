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

package net.fossar.core.block;

import net.fossar.model.core.AdjacentBlocks;
import net.fossar.model.core.DataGrid;
import net.fossar.model.core.block.*;
import junit.framework.TestCase;

import org.mockito.Mockito;

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

    public void testUpdate() throws Exception {
        Lever lever = new Lever();

        DataGrid grid = new DataGrid(3, 3, 2);

        Wire wire = Mockito.mock(Wire.class);
        Block block = Mockito.mock(Block.class);
        Torch torch = Mockito.mock(Torch.class);

        grid.addBlock(wire, 0, 1, 0);
        grid.addBlock(block, 1, 0, 0);
        DataBlock datablock = new DataBlock(1, 1, 0, lever);
        grid.add(datablock);

        grid.addBlock(torch, 1, 2, 1);

        AdjacentBlocks adjacentBlocks = new AdjacentBlocks(grid, datablock);

        lever.powerOn();
        lever.doUpdate(adjacentBlocks);

        Mockito.verify(wire).setInput(16);
        Mockito.verify(block).setInput(16);
        Mockito.verify(torch).updateDirection(Mockito.<AdjacentBlocks>any());
        Mockito.verifyNoMoreInteractions(torch);
    }

}
