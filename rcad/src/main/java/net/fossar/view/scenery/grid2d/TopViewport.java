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

import net.fossar.view.IDataGridDisplayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class TopViewport extends AbstractViewport implements IDataGridDisplayer, IViewport {
	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(TopViewport.class);

    public TopViewport(int rows, int cols, int layerIdx) {
        super(rows, cols, layerIdx);
    }

    @Override
    protected void initLabels() {
		for(int i=0; i<rows; i++) {
			for(int j=0; j<cols; j++) {
				labels[i][j] = new TopViewportLabel();
				super.add(labels[i][j]);
			}
		}
    }

    @Override
    protected int getCorrespondingGridRow(int row, int col, int lay) {
        return row;
    }

    @Override
    protected int getCorrespondingGridCol(int row, int col, int lay) {
        return col;
    }

    @Override
    protected int getCorrespondingGridLay(int row, int col, int lay) {
        return lay;
    }
}
