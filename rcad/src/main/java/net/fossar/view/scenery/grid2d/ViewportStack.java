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
import net.fossar.presenter.event.EventType;
import net.fossar.presenter.event.GridViewEvent;
import net.fossar.presenter.event.PresenterEventManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to easily create a stack of viewports
 * User: nmaupu
 * Date: 10/04/11
 * Time: 00:05
 */
public class ViewportStack extends JPanel implements IViewportStack, MouseInputListener {
    private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(ViewportStack.class);

	protected List<AbstractViewport> viewports = null;
	private CardLayout layout = new CardLayout();
	protected int currentLayer = 0;
    protected PresenterEventManager gridViewEventManager = new PresenterEventManager();

    public ViewportStack() {
		super();
		super.setLayout(layout);

		viewports = new ArrayList<AbstractViewport>();
	}

    public void setLayer(int idx) {
		layout.show(this, String.valueOf(idx));
		currentLayer = idx;
	}

	public void nextLayer() {
        if(currentLayer < viewports.size() - 1) {
	    	layout.next(this);
	    	currentLayer++;
        }
	}

	public void previousLayer() {
        if(currentLayer > 0) {
		    layout.previous(this);
		    currentLayer--;
        }
	}

	public void firstLayer() {
		layout.first(this);
		currentLayer=0;
	}

	public void lastLayer() {
		layout.last(this);
		currentLayer = viewports.size() > 0 ? viewports.size()-1 : 0;
	}

	public int getCurrentLayer() {
		return currentLayer;
	}

	public List<AbstractViewport> getViewports() {
		return viewports;
	}

    public PresenterEventManager getGridViewEventManager() {
        return gridViewEventManager;
    }

	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mouseMoved(MouseEvent e) {}
	@Override
	public void mouseClicked(MouseEvent e) {}
    @Override
    public void mouseDragged(MouseEvent e) {
        // When mouse is dragged, act like a mouse pressed event
        mousePressed(e);
    }
    public void mousePressed(MouseEvent e) {
		int viewportRow = getRowFromPoint(e.getPoint());
		int viewportCol = getColFromPoint(e.getPoint());
        int viewportLay = currentLayer;

        int r = getViewports().get(currentLayer).getCorrespondingGridRow(viewportRow, viewportCol, viewportLay);
        int c = getViewports().get(currentLayer).getCorrespondingGridCol(viewportCol, viewportCol, viewportLay);
        int l = getViewports().get(currentLayer).getCorrespondingGridLay(viewportLay, viewportCol, viewportLay);

        gridViewEventManager.notifyPresenterListeners(new GridViewEvent(e.getSource(), r, c, l, EventType.INSERT));
	}

	private int getRowFromPoint(Point p) {
		Dimension dim = viewports.get(currentLayer).getViewportLabel(0,0).getSize();
		int labelH = (int)dim.getHeight();
		int y = (int)p.getY();
		int r = (int)(y / labelH);

		return r;
	}

	private int getColFromPoint(Point p) {
		Dimension dim = viewports.get(currentLayer).getViewportLabel(0,0).getSize();
		int labelW = (int)dim.getWidth();
		int x = (int)p.getX();
		int c = (int)(x / labelW);

		return c;
    }

    public void repaintCurrentViewport() {
        AbstractViewport v = this.viewports.get(currentLayer);
        for(int r=0; r<v.getRows(); r++) {
            for (int c=0; c<v.getCols(); c++) {
                gridViewEventManager.notifyPresenterListeners(new GridViewEvent(v, r, c, currentLayer, EventType.UPDATE));
            }
        }
    }

    public void repaintBlock(final DataBlock blk) {
        AbstractViewport v = this.viewports.get(currentLayer);
        int row = blk.getRow();
        int col = blk.getCol();
        int lay = blk.getLay();

        gridViewEventManager.notifyPresenterListeners(new GridViewEvent(v, row, col, lay, EventType.UPDATE));
    }

    @Override
    public void addViewport(AbstractViewport viewport) {
        viewports.add(viewport);
        super.add(viewport, String.valueOf(viewports.size()));
		viewport.addMouseListener(this);
		viewport.addMouseMotionListener(this);
    }

    @Override
    public void removeViewport(AbstractViewport viewport) {
        viewport.removeMouseListener(this);
		viewport.removeMouseMotionListener(this);
        viewports.remove(viewport);
    }
}
