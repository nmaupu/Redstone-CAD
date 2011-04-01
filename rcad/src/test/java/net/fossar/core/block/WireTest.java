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
import net.fossar.model.core.AdjacentBlocks;
import net.fossar.model.core.DataGrid;
import net.fossar.model.core.block.*;
import junit.framework.TestCase;

import java.util.Set;


public class WireTest extends TestCase {

    public void test_block_init() {
        Wire wire = new Wire();
        assertEquals(0, wire.getOutput());


    }

    private <T> void assertContains(Set<T> directions, T direction) {
        System.out.println(directions);
        assertTrue("contains" + direction, directions.contains(direction) );
    }


    public void test_set_input_with_propagation() {
        Wire wire = new Wire();
        assertEquals(0, wire.getOutput());
        wire.setInput(AbstractBlock.POWER_MAX);
        assertEquals(AbstractBlock.POWER_MAX - 1, wire.getOutput());
    }


    public void test_set_direction_cross() {

        DataGrid grid = new DataGrid(1, 1, 1);
        Wire wire = new Wire();
        grid.setBlock(wire, 0, 0, 0);

        Set<Direction> directions = wire.getDirections();
        assertEquals(4, directions.size());
        
        assertContains(directions, Direction.UP);
        assertContains(directions, Direction.DOWN);
        assertContains(directions, Direction.RIGHT);
        assertContains(directions, Direction.LEFT);
    }

    public void test_set_direction_up() {

        DataGrid grid = new DataGrid(2, 1, 1);
        Wire wire = new Wire();
        Wire wire2 = new Wire();
        grid.setBlock(wire, 0, 0, 0);
        grid.setBlock(wire2, 1, 0, 0);

        Set<Direction> directions = wire.getDirections();
        
        assertEquals(1, directions.size());
        assertContains(directions, Direction.DOWN);

        directions = wire2.getDirections();
        assertEquals(1, directions.size());
        assertContains(directions, Direction.UP);
    }

    public void test_set_direction_right() {

        DataGrid grid = new DataGrid(1, 2, 1);
        Wire wire = new Wire();
        Wire wire2 = new Wire();
        grid.setBlock(wire, 0, 1, 0);
        grid.setBlock(wire2, 0, 0, 0);

        Set<Direction> directions = wire.getDirections();

        assertEquals(1, directions.size());
        assertContains(directions, Direction.LEFT);

        directions = wire2.getDirections();
        assertEquals(1, directions.size());
        assertContains(directions, Direction.RIGHT);
    }


    public void test_set_direction_corner() {

        DataGrid grid = new DataGrid(2, 2, 1);
        Wire wire = new Wire();
        Wire wire2 = new Wire();
        Wire wire3 = new Wire();
        grid.setBlock(wire, 0, 1, 0);
        grid.setBlock(wire2, 0, 0, 0);
        grid.setBlock(wire3, 1, 0, 0);

        Set<Direction> directions = wire2.getDirections();

        assertEquals(2, directions.size());
        assertContains(directions, Direction.RIGHT);
        assertContains(directions, Direction.DOWN);

        directions = wire3.getDirections();
        assertEquals(1, directions.size());
        assertContains(directions, Direction.UP);

        directions = wire.getDirections();
        assertEquals(1, directions.size());
        assertContains(directions, Direction.LEFT);
    }




    public void test_long_wire() {
        DataGrid grid = new DataGrid(16, 1, 1);

        Lever start = new Lever();
        grid.addBlock(start, 0, 0, 0);
        for (int i = 1; i < 11; i++) {
            grid.addBlock(new Wire(), i, 0, 0);
        }
        Wire end = new Wire();
        grid.addBlock(end, 11, 0, 0);

        grid.doUpdate();
        start.powerOn();

        grid.doUpdate();
        assertEquals(AbstractBlock.POWER_MAX - 10, end.getOutput());
    }

    public void test_cycle() {
        DataGrid grid = new DataGrid(16, 16, 2);

        Lever start = new Lever();
        grid.addBlock(start, 0, 0, 0);
        grid.addBlock(new Wire(), 1, 0, 0);
        grid.addBlock(new Wire(), 1, 1, 0);
        grid.addBlock(new Wire(), 1, 2, 0);
        grid.addBlock(new Wire(), 1, 3, 0);

        grid.addBlock(new Wire(), 2, 0, 0);
        Wire upperWire = new Wire();
        grid.addBlock(upperWire, 2, 1, 1);
        grid.addBlock(new Block(), 2, 1, 0);
        
        grid.addBlock(new Wire(), 2, 3, 0);

        grid.addBlock(new Wire(), 3, 0, 0);
        grid.addBlock(new Wire(), 3, 1, 0);
        grid.addBlock(new Wire(), 3, 2, 0);
        grid.addBlock(new Wire(), 3, 3, 0);


        Wire end = new Wire();
        grid.addBlock(end, 4, 0, 0);

        grid.doUpdate();
        start.powerOn();

        grid.doUpdate();
        assertEquals(AbstractBlock.POWER_SOURCE - 4, end.getOutput());
        assertEquals(13, upperWire.getOutput());
    }

}
