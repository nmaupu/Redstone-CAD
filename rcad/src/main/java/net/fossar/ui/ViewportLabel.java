package net.fossar.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import net.fossar.core.Block;
import net.fossar.core.BlockType;
import net.fossar.core.Colors;
import net.fossar.core.DataGrid;
import net.fossar.core.Direction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class ViewportLabel extends JLabel {
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(ViewportLabel.class);
	public static final Color HOVER_COLOR      = Color.ORANGE;
	public static final int   LABEL_WIDTH      = 25;
	public static final int   BORDER_WIDTH     = 1;
	private Block block = new Block(BlockType.AIR);
	private int row, col, lay;
	private DataGrid grid;
	
	public ViewportLabel(int row, int col, int lay, DataGrid grid) {
		super();
		this.row = row;
		this.col = col;
		this.lay = lay;
		this.grid = grid;
		
		this.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_WIDTH));
		this.setBounds(0, 0, LABEL_WIDTH, LABEL_WIDTH);
				
		this.setOpaque(true);
		//this.addMouseListener(this);

		int top = BORDER_WIDTH;
		int bottom = BORDER_WIDTH;
		int left = BORDER_WIDTH;
		int right = BORDER_WIDTH;
		
		if (row == 0) 					top    *= 2;
		if (col == 0) 					left   *= 2;
		if (row == grid.getRows() - 1) 	bottom *= 2;
		if (col == grid.getCols() - 1) 	right  *= 2;
		
		this.setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Colors.COLOR_VIEWPORT_BORDERS));
	}
	
	public Block getBlock() {
		return block;
	}
	
	@Override
	public void paintComponent(Graphics g1) {
		super.paintComponent(g1);
		Graphics2D g = (Graphics2D)g1;
		
		// anti aliasing
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		setBackground(getBlock().getType().getBackgroundColor());

		// width == height
		int width = getWidth();
		Map<Direction,Block> dirs = null;
		
		Block s = getBlock();
		
		switch(s.getType()) {
		case TORCH:
			// Drawing a torch
			dirs = grid.getAdjacentStatesDirection(this, EnumSet.of(BlockType.BLOCK));
			Direction d = dirs != null && dirs.size() > 0 ? (Direction)dirs.entrySet().iterator().next().getKey() : Direction.UNDEF;
			s.drawTorch(g, d, width);
			break;
		case WIRE:
			// Wires connect to torches and wires
			dirs = grid.getAdjacentStatesDirection(this, EnumSet.of(BlockType.WIRE, BlockType.TORCH));
			List<Direction> list = dirs != null && dirs.size() > 0 ? new ArrayList<Direction>(dirs.keySet()) : null;
			s.drawWire(g, list, width);
			break;
		}
	}
	
	public void repaintAdjacents() {
		if (grid == null)
			return;
		
		int r = this.getRow();
		int c = this.getCol();
		int l = this.getLay();
		
		if (r>0)
			grid.getLabels()[r-1][c][l].repaint();
		
		if (r<grid.getRows() - 1)
			grid.getLabels()[r+1][c][l].repaint();
		
		if (c>0)
			grid.getLabels()[r][c-1][l].repaint();
		
		if (c<grid.getCols() - 1)
			grid.getLabels()[r][c+1][l].repaint();
	}
	
	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public int getLay() {
		return lay;
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
}
