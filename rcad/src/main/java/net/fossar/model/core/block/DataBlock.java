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

public class DataBlock {
	private int row;
	private int col;
	private int lay;
	private AbstractBlock block;
	
	public DataBlock(int row, int col, int lay, AbstractBlock block) {
		this.row = row;
		this.col = col;
		this.lay = lay;
		this.block = block;
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

	public void setRow(int row) {
		this.row = row;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public void setLay(int lay) {
		this.lay = lay;
	}

	public void setBlock(AbstractBlock block) {
		this.block = block;
	}

    public boolean isAt(int fromRow, int fromCol, int fromLay) {
        return fromRow == row && fromCol == col && fromLay == lay;
    }

    @Override
    public String toString() {
        return "DataBlock{" + "row=" + row + ", col=" + col + ", lay=" + lay + ", block=" + block + '}';
    }
}
