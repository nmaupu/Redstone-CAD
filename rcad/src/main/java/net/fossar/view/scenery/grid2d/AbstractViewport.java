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

import net.fossar.model.core.block.DataBlock;
import net.fossar.view.Colors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final Logger logger = LoggerFactory.getLogger(AbstractViewport.class);
    public static final int LABEL_WIDTH  = 25;
	public static final int BORDER_WIDTH = 1;

    protected AbstractViewportLabel[][] labels = null;

    protected int rows;
	protected int cols;
	protected final int layerIdx;

	public AbstractViewport(int rows, int cols, int layerIdx) {
		this.rows = rows;
		this.cols = cols;
        this.layerIdx = layerIdx;

		super.setLayout(new GridLayout(rows, cols));
        super.setBorder(BorderFactory.createLineBorder(Colors.COLOR_VIEWPORT_BORDERS, AbstractViewport.BORDER_WIDTH));
        labels = new AbstractViewportLabel[rows][cols];

		initLabels();
	}

    protected abstract void initLabels();
    /**
     * Get corresponding datagrid row coordinate from viewport coordinates
     * @param row
     * @param col
     * @param lay
     * @return corresponding datagrid row coordinate
     */
    protected abstract int getCorrespondingGridRow(int row, int col, int lay);

    /**
     * Get corresponding datagrid col coordinate from viewport coordinates
     * @param row
     * @param col
     * @param lay
     * @return corresponding datagrid col coordinate
     */
    protected abstract int getCorrespondingGridCol(int row, int col, int lay);

    /**
     * Get corresponding datagrid layer coordinate from viewport coordinates
     * @param row
     * @param col
     * @param lay
     * @return corresponding datagrid layer coordinate
     */
    protected abstract int getCorrespondingGridLay(int row, int col, int lay);

    public void drawBlock(DataBlock dataBlock) {
		logger.debug("Repainting label "+this);
		// Draw block in specified location (given by dataBlock)
        int realRow = getCorrespondingGridRow(dataBlock.getRow(), dataBlock.getCol(), dataBlock.getLay());
        int realCol = getCorrespondingGridCol(dataBlock.getRow(), dataBlock.getCol(), dataBlock.getLay());
        int realLay = getCorrespondingGridLay(dataBlock.getRow(), dataBlock.getCol(), dataBlock.getLay());
        // If real layer is not layeridx, label MUST NOT be repaint because it's hidden
        // Moreover, current label could be another one !
        if(realLay == layerIdx) {
		    AbstractViewportLabel label = getViewportLabel(realRow, realCol);
		    label.repaint(dataBlock);
        }
	}

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
