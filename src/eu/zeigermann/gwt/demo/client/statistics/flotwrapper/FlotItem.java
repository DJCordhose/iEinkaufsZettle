package eu.zeigermann.gwt.demo.client.statistics.flotwrapper;

import com.google.gwt.core.client.JavaScriptObject;

public final class FlotItem extends JavaScriptObject {
	
	protected FlotItem() {
	}
	
	public native String getLabel() /*-{
		return this.series.label;
	}-*/;

	public native String getColor() /*-{
		return this.series.color;
	}-*/;
	
	public native int getPercent() /*-{
		return this.series.percent;
	}-*/;

}
