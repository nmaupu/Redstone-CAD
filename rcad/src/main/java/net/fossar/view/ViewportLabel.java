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

import net.fossar.model.Direction;
import net.fossar.model.core.block.BlockType;
import net.fossar.model.core.block.DataBlock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class provided to map a graphic representation of a DataBlock
 * @author nmaupu
 *
 */
@SuppressWarnings("serial")
public class ViewportLabel extends JLabel implements IView {
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(ViewportLabel.class);
	
	public static final int LABEL_WIDTH  = 25;
	public static final int BORDER_WIDTH = 1;
	
	private IViewport parent;
	private DataBlock dataBlock;
	//private Map<Direction, DataBlock> currentAdjacentBlocks;

	public ViewportLabel(IViewport parent, DataBlock dataBlock) {
		super();
		
		this.parent = parent;
		//this.dataBlock = dataBlock;
		
		this.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_WIDTH));
		this.setBounds(0, 0, LABEL_WIDTH, LABEL_WIDTH);
		
		this.setOpaque(true);

		int top = BORDER_WIDTH;
		int bottom = BORDER_WIDTH;
		int left = BORDER_WIDTH;
		int right = BORDER_WIDTH;
		
		if (dataBlock.getRow() == 0) top  *= 2;
		if (dataBlock.getCol() == 0) left *= 2;
		if (dataBlock.getRow() == parent.getRows() - 1) bottom *= 2;
		if (dataBlock.getCol() == parent.getCols() - 1) right  *= 2;
		
		this.setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Colors.COLOR_VIEWPORT_BORDERS));
		this.setBackground(Colors.COLOR_AIR);
	}

	@Override
	public void paintComponent(Graphics g1) {
		super.paintComponent(g1);
		Graphics2D g = (Graphics2D)g1;
		
		// anti aliasing
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		renderViewportLabel(g);
	}
	
	public void renderViewportLabel(Graphics2D g) {
		if(dataBlock == null)
			return;
		
		switch (BlockType.getBlockType(dataBlock.getBlock())) {
		case AIR:
			drawAir(g);
			break;
		case BLOCK:
			drawBlock(g);
			break;
		case WIRE:
			/*List<Direction> ds = new ArrayList<Direction>();
			
			for(Entry<Direction, DataBlock> entry : dirs.entrySet()) {
				if(BlockType.getBlockType(entry.getValue().getBlock()) == BlockType.TORCH || 
				   BlockType.getBlockType(entry.getValue().getBlock()) == BlockType.WIRE)
						ds.add(entry.getKey());
			}
			*/
			drawWire(g, new ArrayList<Direction>(dataBlock.getBlock().getDirections()));
			break;
		case TORCH:
			/*
			Direction d = Direction.UNDEF;
			for(Entry<Direction, DataBlock> entry : dirs.entrySet()) {
				if(BlockType.getBlockType(entry.getValue().getBlock()) == BlockType.BLOCK) {
					d = entry.getKey();
					break;
				}
			}*/
			
			drawTorch(g, dataBlock.getBlock().getDirections().iterator().next());
			break;
		case LEVER:
			// TODO Draw a lever attach to first available adjacent block
			//drawLever(g, d);
			break;
		}
	}
	
	/**
	 * Draw a torch stick to adjacent block
	 * @param g
	 * @param dir
	 * @param width
	 */
	public void drawTorch(Graphics2D g, Direction dir) {
		int width = getWidth();
		int rectS = width/7 * 2;
		int rectB = width/2;
		int x=0, y=0, w=0, h=0;
		
		// Torch pointing opposite direction of given dir
		//Direction newDir = (dir == Direction.UNDEF) ? Direction.ABOVE : dir.getOpposite();
		// TODO Do it in model
		//dataBlock.getBlock().setDirection(newDir);
		
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
	
	public void drawWire(Graphics2D g, List<Direction> dirs) {
		int width = getWidth();
		
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
			drawWire(g, ds);
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

	public void drawBlock(Graphics2D g) {
		this.setBackground(Colors.COLOR_BLOCK);
	}

	public void drawAir(Graphics2D g) {
		this.setBackground(Colors.COLOR_AIR);
	}

	public void drawLever(Graphics2D g) {
		
	}

	public void drawButton(Graphics2D g) {
		
	}
	/*
	public void setAdjacentBlocks(Map<Direction, DataBlock> currentAdjacentBlocks) {
		this.currentAdjacentBlocks = currentAdjacentBlocks;
	}
	*/
	
	public void setDataBlock(DataBlock dataBlock) {
		this.dataBlock = dataBlock;
	}
	
	/*
	public int getRow() {
		return dataBlock.getRow();
	}
	public int getCol() {
		return dataBlock.getCol();
	}
	public int getLay() {
		return dataBlock.getLay();
	}
	*/
}
