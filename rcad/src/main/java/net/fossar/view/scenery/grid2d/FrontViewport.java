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

import net.fossar.view.IDataGridDisplayer;

/**
 * Created by IntelliJ IDEA.
 * User: nmaupu
 * Date: 10/04/11
 * Time: 23:50
 * To change this template use File | Settings | File Templates.
 */
public class FrontViewport extends AbstractViewport implements IDataGridDisplayer, IViewport {

    public FrontViewport(int rows, int cols, int layerIdx) {
        super(rows, cols, layerIdx);
    }

    @Override
    protected void initLabels() {
        for(int i=0; i<rows; i++) {
			for(int j=0; j<cols; j++) {
				labels[i][j] = new FrontViewportLabel();
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
        return lay;
    }

    @Override
    protected int getCorrespondingGridLay(int row, int col, int lay) {
        return col;
    }
}
