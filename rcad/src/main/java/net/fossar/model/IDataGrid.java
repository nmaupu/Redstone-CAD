package net.fossar.model;

import java.util.Map;

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
}
