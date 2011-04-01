package net.fossar.presenter.event;

import java.util.EventObject;

@SuppressWarnings("serial")
public class PresenterEvent extends EventObject {
	public PresenterEvent(Object source) {
		super(source);
	}
}
