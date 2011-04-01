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

package net.fossar.model.core;

import java.util.Map;

import net.fossar.model.Direction;
import net.fossar.model.Model;
import net.fossar.model.core.block.AbstractBlock;
import net.fossar.model.core.block.BlockType;
import net.fossar.model.core.block.DataBlock;

public interface IDataGrid extends Model {
	public int getRows();
	public int getCols();
	public int getLayers();
	public DataBlock getDataBlock(int r, int c, int l);
	public void setBlock(AbstractBlock block, int r, int c, int l);
	public Map<Direction, BlockType> getAdjacentTypeDirection(DataBlock block);
	public Map<Direction, DataBlock> getAdjacentStatesDirection(DataBlock block);

    void doUpdate();

    void tick();
}