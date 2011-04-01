/*
 * <one line to give the program's name and a brief idea of what it does.>
 *     Copyright (C) 2011.  <name of author>
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.fossar.model.core.block;

import net.fossar.model.Direction;

import java.util.EnumSet;
import java.util.Map;

import net.fossar.model.core.AdjacentBlocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Wire extends AbstractBlock implements PassiveBlock, DirectedBlock {
    protected static Logger logger = LoggerFactory.getLogger(Wire.class);

    public Wire() {
        super(POWER_OFF, 0);
    }

    @Override
    public int getOutput() {
        return super.getOutput() == 0 ? 0 : super.getOutput() - 1;
    }

    @Override
    public void doUpdate(AdjacentBlocks adjacentBlocks) {

        for (Map.Entry<Direction, DataBlock> entry : adjacentBlocks.entrySet()) {
            DataBlock value = entry.getValue();
            AbstractBlock block = value.getBlock();
            if (block instanceof Wire) {
                logger.debug("dealing with " + value);
                AdjacentBlocks adjacents = new AdjacentBlocks(adjacentBlocks, value, true);

                if (!adjacents.getAlreadyProcessed().contains(
                        value) || ((block.getOutput() <= getOutput() - 1) && getOutput() != 0)) {
                    logger.debug("i'm  " + getOutput() + " going to " + block.getOutput());
                    block.setInput(getOutput());
                    block.doUpdate(adjacents);
                    logger.debug("destination : " + value + " is now " + block.getOutput());
                }
            }
            if (entry.getKey() == Direction.ABOVE) {
                if (BlockType.getBlockType(block) == BlockType.AIR) {
                    for (Direction cardinal : EnumSet.of(Direction.UP, Direction.DOWN, Direction.LEFT,
                            Direction.RIGHT)) {
//                        if (adjacentBlocks.get(cardinal) == BLOCK) {
//                            if (ABOVE_cardinal == wire) {
//                                abstractBlock.setInput(getOutput());
//                                abstractBlock.doUpdate();
//                            }
//                        }
                    }
                }
            }
        }
    }

    @Override
    public void updateDirection(AdjacentBlocks dirs) {
        initDirections();
        for (Map.Entry<Direction, DataBlock> entry : dirs.entrySet()) {
            AbstractBlock block = entry.getValue().getBlock();
            if (block instanceof Torch || block instanceof Wire) {
                addDirection(entry.getKey());
            }
        }
        if (direction.isEmpty()) {
            direction.add(Direction.LEFT);
            direction.add(Direction.RIGHT);
            direction.add(Direction.UP);
            direction.add(Direction.DOWN);
        }
    }

    public void resetBlockPower() {
        super.setPower(POWER_OFF);
    }
}
