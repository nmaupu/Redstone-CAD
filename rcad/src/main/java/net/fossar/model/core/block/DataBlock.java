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

import net.fossar.presenter.event.GenericEvent;
import net.fossar.presenter.event.PresenterEvent;
import net.fossar.presenter.event.PresenterEventManager;

public class DataBlock {
	private int row;
	private int col;
	private int lay;
	private AbstractBlock block;
    PresenterEventManager dataBlockManager = new PresenterEventManager();
	
	public DataBlock(int row, int col, int lay, AbstractBlock block) {
		this.row = row;
		this.col = col;
		this.lay = lay;
		setBlock(block);
	}
	
	// Getters / setters
	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public int getLay() {
		return lay;
	}

	public AbstractBlock getBlock() {
		return block;
	}

    public PresenterEventManager getDataBlockManager() {
        return dataBlockManager;
    }

	public void setBlock(AbstractBlock block) {
        BlockType oldType = BlockType.getBlockType(this.block);
		this.block = block;

        // Notify listeners if this block is modified
        if(oldType != BlockType.getBlockType(block)) {
            PresenterEvent e = new GenericEvent(this);
            dataBlockManager.notifyPresenterListeners(e);
        }

	}

    public boolean isAt(int fromRow, int fromCol, int fromLay) {
        return fromRow == row && fromCol == col && fromLay == lay;
    }

    @Override
    public String toString() {
        return "DataBlock{" + "row=" + row + ", col=" + col + ", lay=" + lay + ", block=" + block + '}';
    }
}
