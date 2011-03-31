package net.fossar.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import net.fossar.model.DataGrid;
import net.fossar.model.Direction;
import net.fossar.model.core.block.AbstractBlock;
import net.fossar.model.core.block.Block;
import net.fossar.model.core.block.DataBlock;
import net.fossar.model.core.block.Torch;
import net.fossar.model.core.block.Wire;
import net.fossar.presenter.Director;
import net.fossar.presenter.Presenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class provided to map a graphic representation of a DataBlock
 * @author nmaupu
 *
 */
@SuppressWarnings("serial")
public class ViewportLabel extends JLabel implements View {
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(ViewportLabel.class);
	public static final int   LABEL_WIDTH      = 25;
	public static final int   BORDER_WIDTH     = 1;
	
	private Presenter presenter;
	private DataBlock dataBlock;
	private DataGrid dataGrid;
	
	public ViewportLabel(Presenter presenter, DataBlock dataBlock) {
		super();
		
		this.presenter = presenter;
		dataGrid = (DataGrid) presenter.getModel(Director.DATAGRID);
		this.dataBlock = dataBlock;
		
		this.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_WIDTH));
		this.setBounds(0, 0, LABEL_WIDTH, LABEL_WIDTH);
				
		this.setOpaque(true);

		int top = BORDER_WIDTH;
		int bottom = BORDER_WIDTH;
		int left = BORDER_WIDTH;
		int right = BORDER_WIDTH;
		
		if (dataBlock.getRow() == 0) top  *= 2;
		if (dataBlock.getCol() == 0) left *= 2;
		if (dataBlock.getRow() == dataGrid.getRows() - 1) bottom *= 2;
		if (dataBlock.getCol() == dataGrid.getCols() - 1) right  *= 2;
		
		this.setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Colors.COLOR_VIEWPORT_BORDERS));
	}
	
	public DataBlock getDataBlock() {
		return dataBlock;
	}
	
	public void setDataBlock(DataBlock dataBlock) {
		this.dataBlock = dataBlock;
	}
	
	@Override
	public void paintComponent(Graphics g1) {
		super.paintComponent(g1);
		Graphics2D g = (Graphics2D)g1;
		
		// anti aliasing
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		setBackground(getBackgroundColor());

		// width == height
		int width = getWidth();
		Map<Direction,DataBlock> dirs = null;
		AbstractBlock b = dataBlock.getBlock();
		
		if(b instanceof Torch) {
			// Drawing a torch
			dirs = dataGrid.getAdjacentStatesDirection(dataBlock);
			
			// Use first Block found to place torch
			Direction d = Direction.UNDEF;
			for(Entry<Direction, DataBlock> entry : dirs.entrySet()) {
				if(entry.getValue().getBlock() instanceof Block)
					d = entry.getKey();
			}
			
			drawTorch(g, d, width);
			
		} else if(b instanceof Wire) {
			// Wires connect to torches and wires
			dirs = dataGrid.getAdjacentStatesDirection(dataBlock);
			
			// Filter for Torch or Wire
			List<Direction> list = new ArrayList<Direction>();
			for(Entry<Direction, DataBlock> entry : dirs.entrySet()) {
				AbstractBlock tmpBlk = entry.getValue().getBlock();
				
				if(tmpBlk instanceof Torch || tmpBlk instanceof Wire)
					list.add(entry.getKey());
			}
			
			// Draw Wire
			drawWire(g, list, width);
		}
	}
	
	public Color getBackgroundColor() {
		if(dataBlock.getBlock() instanceof Block)
			return Colors.COLOR_BLOCK;
		
		return Colors.COLOR_AIR;
	}
	
	/**
	 * Draw a torch stick to adjacent block
	 * @param g
	 * @param dir
	 * @param width
	 */
	public void drawTorch(Graphics2D g, Direction dir, int width) {
		int rectS = width/7 * 2;
		int rectB = width/2;
		int x=0, y=0, w=0, h=0;
		
		// Torch pointing opposite direction of given dir
		Direction currentDir = dataBlock.getBlock().getDirection();
		Direction newDir = (dir == Direction.UNDEF) ? Direction.ABOVE : currentDir.getOpposite(); 
		dataBlock.getBlock().setDirection(newDir);
		
		switch(dir) {
		case UP :
		case DOWN :
			x = width/2 - rectS / 2;
			y = dir.up() ? 0 : rectB;
			w = rectS;
			h = rectB;
			break;
		case RIGHT :
		case LEFT :
			x = dir.right() ? rectB : 0;
			y = width/2 - rectS/2;
			w = rectB;
			h = rectS;
			break;
		}
		
		// Draw base
		g.setColor(Colors.COLOR_TORCH_BASE);
		g.fillRect(x, y, w, h);
		
		// Oval in center
		Color c = dataBlock.getBlock().isPowered() ? Colors.COLOR_WIRE_ON : Colors.COLOR_WIRE_OFF;
		g.setColor(c);
		x = width/2 - width/4;
		y = width/2 - width/4;
		w = width/4 * 2;
		h = width/4 * 2;
		g.fillOval(x, y, w, h);
	}
	
	public void drawWire(Graphics2D g, List<Direction> dirs, int width) {
		
		Color c = dataBlock.getBlock().isPowered() ? Colors.COLOR_WIRE_ON : Colors.COLOR_WIRE_OFF;
		g.setColor(c);
		List<Direction> ds = dirs == null || dirs.size() == 0 ? new ArrayList<Direction>() : dirs ;
		
		int x = 0;
		int y = 0;
		int w = 0;
		int h = 0;
		int rectS = width/4;
		int rectB = width;
		
		if(dirs == null || dirs.size() == 0) {
			//Draw a cross wire
			ds.add(Direction.UP);
			ds.add(Direction.DOWN);
			ds.add(Direction.LEFT);
			ds.add(Direction.RIGHT);
			drawWire(g, ds, width);
		}
		
		for (Direction d : ds) {
			switch(d) {
			case UP :
			case DOWN :
				x = width/2 - rectS/2;
				y = 0;
				w = rectS;
				h = rectB;
				
				if(ds.contains(Direction.LEFT) || ds.contains(Direction.RIGHT)) {
					h = h/2;
					if(d == Direction.DOWN)
						y = width/2;
				}
				break;
			case RIGHT :
			case LEFT :
				x = 0;
				y = width/2 - rectS/2;
				w = rectB;
				h = rectS;
				
				if(ds.contains(Direction.UP) || ds.contains(Direction.DOWN)) {
					w = w / 2 + rectS/2;
					if(d == Direction.RIGHT)
						x = width/2 - rectS/2;
				}
				break;
			}
			
			g.fillRect(x, y, w, h);
		}
	}
}
