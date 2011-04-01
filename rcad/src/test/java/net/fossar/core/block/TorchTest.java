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


import net.fossar.model.Direction;
import net.fossar.model.core.DataGrid;
import net.fossar.model.core.block.AbstractBlock;
import net.fossar.model.core.block.Block;
import net.fossar.model.core.block.Torch;
import net.fossar.model.core.block.Wire;
import org.junit.Test;

import java.util.Set;

import static junit.framework.Assert.*;

public class TorchTest {

    @Test
    public void test_block_init() {
        Torch torch = new Torch();
        assertEquals(AbstractBlock.POWER_SOURCE, torch.getOutput());
    }

    private <T> void assertContains(Set<T> directions, T direction) {
        System.out.println(directions);
        assertTrue("contains" + direction, directions.contains(direction));
    }




    @Test
    public void test_set_direction_undef() {

        DataGrid grid = new DataGrid(1, 1, 1);
        Torch    torch = new Torch();
        grid.setBlock(torch, 0, 0, 0);

        Set<Direction> directions = torch.getDirections();
        assertEquals(1, directions.size());

        assertContains(directions, Direction.UNDEF);

    }

    @Test
    public void test_set_direction_DOWN() {

        DataGrid grid = new DataGrid(2, 1, 1);
        Torch torch = new Torch();
        Block block = new Block();
        grid.setBlock(torch, 0, 0, 0);
        grid.setBlock(block, 1, 0, 0);

        Set<Direction> directions = torch.getDirections();

        assertEquals(1, directions.size());
        assertContains(directions, Direction.DOWN);
    }

    @Test
    public void test_set_direction_right() {

        DataGrid grid = new DataGrid(1, 2, 1);
        Torch torch = new Torch();
        Block block = new Block();

        grid.setBlock(torch, 0, 1, 0);
        grid.setBlock(block, 0, 0, 0);

        Set<Direction> directions = torch.getDirections();

        assertEquals(1, directions.size());
        assertContains(directions, Direction.LEFT);


    }
    

}
