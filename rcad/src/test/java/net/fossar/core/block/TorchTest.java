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
