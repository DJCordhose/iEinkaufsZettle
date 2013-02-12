package eu.zeigermann.gwt.demo.client.statistics;

import com.google.gwt.core.client.JavaScriptObject;

import eu.zeigermann.gwt.demo.client.statistics.flotwrapper.FlotData;

public final class StatisticsJsni extends JavaScriptObject {
	protected StatisticsJsni() {

	}

	// {"Edeka":0,"Penny":1,"Aldi":2,"Rewe":1}
	public final native static StatisticsJsni createInstance(String json) /*-{
		return JSON.parse(json);
	}-*/;
	
//	[{"label": "Edeka", "data": 0},{"label":"Penny", "data":1}, ...]
	public final native FlotData toFlot() /*-{
		var flotData = [];
		for (var label in this) {
			if (this.hasOwnProperty(label)) {
				var value = this[label];
				flotData.push({label: label, data: value});
			}
        }
        return flotData;
	}-*/;
}
