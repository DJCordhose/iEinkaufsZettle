package eu.zeigermann.gwt.demo.client.statistics.flotwrapper;

import com.google.gwt.dom.client.Element;


public class Flot {

	public static void renderAsPie(Element divToRender, FlotData data, FlotHoverHandler hoverHandler, FlotClickHandler clickHandler) {
		renderAsPie(divToRender.getId(), data, hoverHandler, clickHandler);
	}
	
	public static native void renderAsPie(String divToRenderId, FlotData data, FlotHoverHandler hoverHandler, FlotClickHandler clickHandler) /*-{
		var id = "#" + divToRenderId;
		var div = $wnd.$(id);
        $wnd.$.plot(div,
                data,
                {
                    series: {
                        pie: {
                            show: true
                        }
                    },
                    grid: {
                        hoverable: true,
                        clickable: true
                    }
                }
        );
        div.bind("plothover", function(event, pos, obj) {
        	if (hoverHandler && obj) {
        		$entry(hoverHandler.@eu.zeigermann.gwt.demo.client.statistics.flotwrapper.FlotHoverHandler::onHovered(Leu/zeigermann/gwt/demo/client/statistics/flotwrapper/FlotPosition;Leu/zeigermann/gwt/demo/client/statistics/flotwrapper/FlotItem;)(pos, obj));
        	}
        });
        div.bind("plotclick", function(event, pos, obj) {
        	if (clickHandler && obj) {
        		$entry(clickHandler.@eu.zeigermann.gwt.demo.client.statistics.flotwrapper.FlotClickHandler::onClicked(Leu/zeigermann/gwt/demo/client/statistics/flotwrapper/FlotPosition;Leu/zeigermann/gwt/demo/client/statistics/flotwrapper/FlotItem;)(pos, obj));
        	}
        });
        
	}-*/;
}
