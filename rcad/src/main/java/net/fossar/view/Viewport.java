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

package net.fossar.view;

import java.awt.GridLayout;

import javax.swing.JPanel;

import net.fossar.model.Direction;
import net.fossar.model.core.block.DataBlock;
import net.fossar.presenter.Director;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class Viewport extends JPanel implements IViewport, IDataGridDisplayer {
	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(Viewport.class);
	
	private ViewportLabel[][] labels = null;
	private int rows;
	private int cols;
	private final int layerIdx;
	
	public Viewport(int rows, int cols, int layerIdx) {
		this.layerIdx = layerIdx;
		this.rows = rows;
		this.cols = cols;
		
		super.setLayout(new GridLayout(rows, cols));
		initLabels();
	}
	
	private void initLabels() {
		if (labels == null)
			labels = new ViewportLabel[rows][cols];
		
		for(int i=0; i<rows; i++) {
			for(int j=0; j<cols; j++) {
				labels[i][j] = new ViewportLabel(this, Director.dataGridController.getDataGrid().getDataBlock(i,j,layerIdx));
				super.add(labels[i][j]);
			}
		}
	}
	
	public ViewportLabel getViewportLabel(int r, int c) {
		return labels[r][c];
	}
	
	@Override
	public int getLayerIdx() {
		return layerIdx;
	}

	@Override
	public int getRows() {
		return rows;
	}

	@Override
	public int getCols() {
		return cols;
	}

	@Override
	public void drawBlock(DataBlock dataBlock) {
		logger.debug("Repainting label "+this);
		// Draw block in specified location (given by dataBlock)
		ViewportLabel label = this.getViewportLabel(dataBlock.getRow(), dataBlock.getCol());
		label.setDataBlock(dataBlock);
		label.repaint();
	}	
}
