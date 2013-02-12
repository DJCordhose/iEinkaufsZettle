package eu.zeigermann.gwt.demo.client.statistics.flotwrapper;

import com.google.gwt.core.client.JavaScriptObject;

public final class FlotPosition extends JavaScriptObject {
	
	protected FlotPosition() {
	}
	
	public native int getPageX() /*-{
		return this.pageX;
	}-*/;

	public native int getPageY() /*-{
		return this.pageY;
	}-*/;

}
