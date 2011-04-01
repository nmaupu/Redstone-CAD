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

import java.util.Map;

import net.fossar.model.core.AdjacentBlocks;

public class Torch extends AbstractBlock implements ActiveBlock {

	public Torch() {
		super(POWER_SOURCE, 0);
		powerOn();
	}

	@Override
	public void powerOn() {
		setInput(AbstractBlock.POWER_SOURCE);
	}

	@Override
	public void powerOff() {
		setInput(AbstractBlock.POWER_OFF);
	}

	@Override
	public void doUpdate(AdjacentBlocks adjacentBlocks) {
		for (Map.Entry<Direction, DataBlock> entry : adjacentBlocks.entrySet()) {
            DataBlock dataBlock = entry.getValue();
            AbstractBlock block = dataBlock.getBlock();
			if (block instanceof Block) {
				if (block.isPowered()) {
					this.powerOff();
				} else {
					this.powerOn();
				}
			}
		}
        for (Map.Entry<Direction, DataBlock> entry : adjacentBlocks.entrySet()) {
            DataBlock dataBlock = entry.getValue();
            AbstractBlock block = dataBlock.getBlock();
			if (block instanceof Wire){
                block.setInput(getOutput());
                block.doUpdate(new AdjacentBlocks(adjacentBlocks, dataBlock));
            }
		}


	}
}
