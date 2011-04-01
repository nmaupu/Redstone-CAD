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
			AbstractBlock abstractBlock = entry.getValue().getBlock();
			int output = 0;
			if (abstractBlock instanceof Wire) {
                if(abstractBlock.getDirections().contains(entry.getKey()) || abstractBlock.getDirections().contains(entry.getKey().getOpposite()) ){
				    output = abstractBlock.getOutput();
				    abstractBlock.doUpdate(new AdjacentBlocks(adjacentBlocks, entry.getValue()));
                }
			}
			if (abstractBlock instanceof Lever) {
				output = abstractBlock.isPowered() ? AbstractBlock.POWER_MAX : AbstractBlock.POWER_OFF;
			}
			if (result < output) {
				result = output;
			}
		}
		setInput(result);
	}
}
