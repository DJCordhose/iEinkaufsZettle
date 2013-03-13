package eu.zeigermann.gwt.demo.client.statistics.flotwrapper;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gwt.core.client.JavaScriptObject;

public final class FlotData extends JavaScriptObject {

	protected FlotData() {
	}

	// das soll raus kommen
	//	[{"label": "Edeka", "data": 0},{"label":"Penny", "data":1}, ...]
	public static FlotData getInstance(Map<String, Integer> data) {
		String dataAsJson = "[";
		Set<Entry<String, Integer>> entrySet = data.entrySet();
		int cnt = 1;
		for (Entry<String, Integer> entry : entrySet) {
			dataAsJson += format(entry);
			if (cnt < entrySet.size()) {
				dataAsJson += ",";
			}
			cnt++;
		}
		dataAsJson += "]";

		return getInstance(dataAsJson);
	}

	private static String format(Entry<String, Integer> entry) {
		String dataAsJson = "{";
		dataAsJson += "\"label\": \"" + entry.getKey() + "\", \"data\": "
				+ entry.getValue();
		dataAsJson += "}";
		return dataAsJson;
	}

	public native static FlotData getInstance(String dataAsJson) /*-{
		var data = JSON.parse(dataAsJson);
		return data;
	}-*/;
}
