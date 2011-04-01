package net.fossar.presenter.event;


@SuppressWarnings("serial")
public class GridViewEvent extends PresenterEvent {
	private int row;
	private int col;
	private int lay;
	
	public GridViewEvent(Object source, int r, int c , int l) {
		super(source);
		this.row = r;
		this.col = c;
		this.lay = l;
	}
	
	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public int getLay() {
		return lay;
	}
}
