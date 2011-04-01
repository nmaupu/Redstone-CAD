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

public class Air extends AbstractBlock {
	public static final Air INSTANCE = new Air();

	protected Air() {
		super(-1, -1);
	}

	@Override
	public void addBlockPowerListener(BlockPowerListener listener) {
		return;
	}


	

	@Override
	public int getOutput() {
		return 0;
	}

	@Override
	public boolean isPowered() {
		return false;
	}

	@Override
	public boolean isUnpowered() {
		return true;
	}

	

	@Override
	public void setInput(int power) {
		return;
	}

	@Override
	public void doUpdate(AdjacentBlocks adjacentBlocks) {
		return;

	}

}
