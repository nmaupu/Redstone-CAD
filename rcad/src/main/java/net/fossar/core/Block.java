package net.fossar.core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

import net.fossar.ui.ViewportLabel;

public enum Block {
	
	AIR(false, Colors.COLOR_AIR, "Air", BlockConstant.CONDUCT_AIR),
	BLOCK(false, Colors.COLOR_BLOCK, "Block", BlockConstant.CONDUCT_BLOCK),
	WIRE(false, Colors.COLOR_AIR, "Wire", BlockConstant.CONDUCT_WIRE),
	TORCH(true, Colors.COLOR_AIR, "Torch", BlockConstant.CONDUCT_WIRE),
	LEVER(false, Colors.COLOR_AIR, "Lever", BlockConstant.CONDUCT_WIRE),
	P_PLATE(false, Colors.COLOR_AIR, "Pressure Plate", BlockConstant.CONDUCT_WIRE),
	DOOR_O(false, Colors.COLOR_AIR, "Door", BlockConstant.CONDUCT_BLOCK),
	DOOR_C(false, Colors.COLOR_AIR, "Door", BlockConstant.CONDUCT_BLOCK),
	SHADOW(false, Colors.COLOR_SHADOW, "Shadow", BlockConstant.CONDUCT_AIR);
	
	String description;
	Color backgroundColor;
	int conductorType;
	boolean isOn;
	
	private Block(boolean isOn, Color backgroundColor, String description, int conductorType) {
		this.isOn = isOn;
		this.backgroundColor = backgroundColor;
		this.description = description;
		this.conductorType = conductorType;
	}
	
	public Color getBackgroundColor() {
		return backgroundColor;
	}
	
	public boolean isAir()        { return this == AIR || this == SHADOW; }
	public boolean isController() { return this == LEVER || this == P_PLATE; }
	public boolean isBlock()      { return this == BLOCK; }
	public boolean isOn()         { return isOn; }
	public boolean isOff()        { return ! isOn(); }
	
	public void paint(ViewportLabel parent, DataGrid grid, Graphics g1) {
		Graphics2D g = (Graphics2D)g1;
		
		// anti aliasing
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		System.out.println("paint Block state"+this);
		parent.setBackground(getBackgroundColor());

		// width == height
		int width = parent.getWidth();
		List<Direction> dir = null;
		
		switch(this) {
		case TORCH:
			// Drawing a torch
			dir = getAdjacentStatesDirection(grid, parent.getRow(), parent.getCol(), parent.getLay(), BLOCK);
			Direction d = dir != null && dir.size() > 0 ? dir.get(0) : Direction.UNDEF;
			
			drawTorch(g, d, width);
			
			break;
		case WIRE:
			//List<Direction> dirBlock = getAdjacentStatesDirection(grid, parent.getRow(), parent.getCol(), parent.getLay(), BLOCK);
			List<Direction> dirWire  = getAdjacentStatesDirection(grid, parent.getRow(), parent.getCol(), parent.getLay(), WIRE);
			
			//drawWire(g, dirBlock, width);
			drawWire(g, dirWire, width);
			
			break;
		}
	}
	
	/**
	 * Indicates if given coordinates data has an adjacent data corresponding to a given Block
	 * @param grid
	 * @param x
	 * @param y
	 * @param z
	 * @param state
	 * @return True if there is an adjacent state corresponding to a given Block, false otherwise
	 */
	public static boolean hasAdjacentState(DataGrid grid, int r, int c, int l, Block state) {
		List<Direction> res = getAdjacentStatesDirection(grid, r, c, l, state);
		return res != null;
	}
	
	public static List<Direction> getAdjacentStatesDirection(DataGrid grid, int r, int c, int l, Block state) {
		List<Direction> res = new ArrayList<Direction>();
		
		if 		(r < grid.getRows()-1 && grid.getState(r+1, c, l) == state)
			res.add(Direction.DOWN);
		if	(r > 0 && grid.getState(r-1, c, l) == state)
			res.add(Direction.UP);
		if (c < grid.getCols()-1 && grid.getState(r, c+1, l) == state)
			res.add(Direction.RIGHT);
		if (c > 0 && grid.getState(r, c-1, l) == state)
			res.add(Direction.LEFT);
		if (l < grid.getLayers()-1 && grid.getState(r, c, l+1) == state)
			res.add(Direction.ABOVE);
		if (l > 0 && grid.getState(r, c, l-1) == state)
			res.add(Direction.BELOW);
		
		return res.size() == 0 ? null : res;
	}
	
	public void drawTorch(Graphics2D g, Direction dir, int width) {
		int rectS = width/7 * 2;
		int rectB = width/2;
		int x=0, y=0, w=0, h=0;
		
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
		Color c = isOn() ? Colors.COLOR_WIRE_ON : Colors.COLOR_WIRE_OFF;
		g.setColor(c);
		x = width/2 - width/4;
		y = width/2 - width/4;
		w = width/4 * 2;
		h = width/4 * 2;
		g.fillOval(x, y, w, h);
	}
	
	public void drawWire(Graphics2D g, List<Direction> dirs, int width) {
		
		Color c = isOn() ? Colors.COLOR_WIRE_ON : Colors.COLOR_WIRE_OFF;
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
		
		int size = ds.size();
		for (int i=0; i<size; i++) {
			switch(ds.get(i)) {
			case UP :
			case DOWN :
				x = width/2 - rectS/2;
				y = 0;
				w = rectS;
				h = rectB;
				
				if(ds.contains(Direction.LEFT) || ds.contains(Direction.RIGHT)) {
					h = h/2;
					if(ds.get(i) == Direction.DOWN)
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
					w = w / 2;
					if(ds.get(i) == Direction.RIGHT)
						x = width/2 - rectS/2;
						w = w + rectS/2;
				}
				break;
			}
			
			g.fillRect(x, y, w, h);
		}
	}
}

abstract class BlockConstant {
	public static final int CONDUCT_AIR   = 0;
	public static final int CONDUCT_BLOCK = 1;
	public static final int CONDUCT_WIRE  = 2;
}
