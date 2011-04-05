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

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import net.fossar.presenter.event.GridViewEvent;

import net.fossar.presenter.event.GridViewEventManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ViewportStack extends JPanel implements MouseInputListener, IViewportStack {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(ViewportStack.class);
		
	private List<AbstractViewport> viewports = null;
	private CardLayout layout = new CardLayout();
	private int currentLayer = 0;
    private GridViewEventManager gridViewEventManager = new GridViewEventManager();
	
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
		layout.next(this);
		currentLayer++;
	}
	
	public void previousLayer() {
		layout.previous(this);
		currentLayer--;
	}
	
	public void firstLayer() {
		layout.first(this);
		currentLayer=0;
	}
	
	public void lastLayer() {
		layout.last(this);
		currentLayer = viewports.size()-1;
	}
	
	public int getCurrentLayer() {
		return currentLayer;
	}

	public List<AbstractViewport> getViewports() {
		return viewports;
	}

    public GridViewEventManager getGridViewEventManager() {
        return gridViewEventManager;
    }

	@Override
	public void mousePressed(MouseEvent e) {
		int r = getRowFromPoint(e.getPoint());
		int c = getColFromPoint(e.getPoint());

        gridViewEventManager.notifyPresenterListeners(
            new GridViewEvent(e.getSource(), r, c, currentLayer)
        );
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int r = getRowFromPoint(e.getPoint());
		int c = getColFromPoint(e.getPoint());
		
		gridViewEventManager.notifyPresenterListeners(
                new GridViewEvent(e.getSource(), r, c, currentLayer)
        );
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
