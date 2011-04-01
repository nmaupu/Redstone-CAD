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

package net.fossar.view.scenery.grid2d;

import net.fossar.model.Direction;
import net.fossar.view.Colors;
import net.fossar.view.scenery.grid2d.AbstractViewportLabel;

import java.awt.*;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: nmaupu
 * Date: 02/04/11
 * Time: 01:15
 * To change this template use File | Settings | File Templates.
 */
public class TopViewportLabel extends AbstractViewportLabel {
    /**
	 * Draw a torch stick to adjacent block
	 * @param g
	 * @param dir
	 */
    @Override
	public void drawTorch(Graphics2D g, Direction dir) {
		int width = getWidth();
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
		Color c = dataBlock.getBlock().isPowered() ? Colors.COLOR_WIRE_ON : Colors.COLOR_WIRE_OFF;
		g.setColor(c);
		x = width/2 - width/4;
		y = width/2 - width/4;
		w = width/4 * 2;
		h = width/4 * 2;
		g.fillOval(x, y, w, h);
	}

    @Override
	public void drawWire(Graphics2D g, java.util.List<Direction> dirs) {
		int width = getWidth();

		Color c = dataBlock.getBlock().isPowered() ? Colors.COLOR_WIRE_ON : Colors.COLOR_WIRE_OFF;
		g.setColor(c);
		java.util.List<Direction> ds = dirs == null || dirs.size() == 0 ? new ArrayList<Direction>() : dirs ;

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

    @Override
    public void drawBlock(Graphics2D g) {
		this.setBackground(Colors.COLOR_BLOCK);
	}

    @Override
	public void drawAir(Graphics2D g) {
		this.setBackground(Colors.COLOR_AIR);
	}

    @Override
	public void drawLever(Graphics2D g) {

	}

    @Override
	public void drawButton(Graphics2D g) {

	}
}
