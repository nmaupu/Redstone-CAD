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

import net.fossar.model.core.AdjacentBlocks;
import net.fossar.model.Direction;

import java.util.Map;

public class Block extends AbstractBlock implements PassiveBlock {
	
	public Block() {
		super(POWER_OFF, PROPAGATION_DELAY);
	}

	@Override
	public void doUpdate(AdjacentBlocks adjacentBlocks) {
		int result = 0;
		for (Map.Entry<Direction, DataBlock> entry : adjacentBlocks.entrySet()) {
			AbstractBlock block = entry.getValue().getBlock();
			int output = 0;
			if (block instanceof Wire) {
				output = block.getOutput();
				block.doUpdate(new AdjacentBlocks(adjacentBlocks, entry.getValue()));
			}
//			if (block instanceof Torch) {
//				output = block.isPowered() ? AbstractBlock.POWER_OFF : AbstractBlock.POWER_MAX;
//			}
			if (block instanceof Lever) {
				output = block.isPowered() ? AbstractBlock.POWER_MAX : AbstractBlock.POWER_OFF;
			}
			if (result < output) {
				result = output;
			}
		}
		setInput(result);
	}
}
