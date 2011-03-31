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
