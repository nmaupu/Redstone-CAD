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

import net.fossar.view.Colors;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: nmaupu
 * Date: 02/04/11
 * Time: 00:45
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractViewport extends JPanel implements IViewport {
    public static final int LABEL_WIDTH  = 25;
	public static final int BORDER_WIDTH = 1;

    protected AbstractViewportLabel[][] labels = null;

    protected int rows;
	protected int cols;
	protected final int layerIdx;

	public AbstractViewport(int rows, int cols, int layerIdx) {
		this.layerIdx = layerIdx;
		this.rows = rows;
		this.cols = cols;

		super.setLayout(new GridLayout(rows, cols));
        super.setBorder(BorderFactory.createLineBorder(Colors.COLOR_VIEWPORT_BORDERS, AbstractViewport.BORDER_WIDTH));
        labels = new AbstractViewportLabel[rows][cols];

		initLabels();
	}

    protected abstract void initLabels();

    public AbstractViewportLabel getViewportLabel(int r, int c) {
		return labels[r][c];
	}

    public int getLayerIdx() {
		return layerIdx;
	}

	public int getRows() {
		return rows;
	}

	public int getCols() {
		return cols;
	}
}
