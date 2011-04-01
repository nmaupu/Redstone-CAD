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

public enum BlockType {
	AIR {
		@Override
		AbstractBlock createBlock() {
			return new Air();
		}
	},
	LEVER {
		@Override
		AbstractBlock createBlock() {
			return new Lever();
		}
	},
	TORCH {
		@Override
		AbstractBlock createBlock() {
			return new Torch();
		}
	},
	WIRE {
		@Override
		AbstractBlock createBlock() {
			return new Wire();
		}
	},
	BLOCK {
		@Override
		AbstractBlock createBlock() {
			return new Block();
		}
	};
	
	abstract AbstractBlock createBlock();
	
	public static BlockType getBlockType(AbstractBlock block) {
		if (block instanceof Lever)
			return BlockType.LEVER;
		else if (block instanceof Torch)
			return BlockType.TORCH;
		else if (block instanceof Wire)
			return BlockType.WIRE;
		else if (block instanceof Block)
			return BlockType.BLOCK;
		
		return BlockType.AIR;
	}
}
