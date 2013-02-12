package eu.zeigermann.gwt.demo.client.statistics;

import java.util.Map;

import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import eu.zeigermann.gwt.demo.client.statistics.flotwrapper.Flot;
import eu.zeigermann.gwt.demo.client.statistics.flotwrapper.FlotClickHandler;
import eu.zeigermann.gwt.demo.client.statistics.flotwrapper.FlotData;
import eu.zeigermann.gwt.demo.client.statistics.flotwrapper.FlotHoverHandler;
import eu.zeigermann.gwt.demo.client.statistics.flotwrapper.FlotItem;
import eu.zeigermann.gwt.demo.client.statistics.flotwrapper.FlotPosition;

public class DefaultStatisticsView extends Composite implements StatisticsView {

	final Widget widget;
	final Label label;
	ViewHandler presenter;

	public DefaultStatisticsView() {
		FlowPanel panel = new FlowPanel();
		label = new Label();
		widget = new HTML(
				SafeHtmlUtils
						.fromSafeConstant("<div id=\"pie\" style=\"width:600px;height:300px\"></div>"));
		panel.add(widget);
		panel.add(label);
		initWidget(panel);
	}

	@Override
	public void setViewHandler(ViewHandler handler) {
		this.presenter = handler;
	}

	@Override
	public void show(Map<String, Integer> stats) {
		FlotData data = FlotData.getInstance(stats);
		Flot.renderAsPie(widget.getElement().getFirstChildElement(), data, new FlotHoverHandler() {

			@Override
			public void onHovered(FlotPosition pos, FlotItem item) {
				String color = item.getColor();
				String itemLabel = item.getLabel();
				int percent = item.getPercent();
				label.getElement().setInnerHTML(
						"<span style=\"font-weight: bold; color: "
								+ color + "\">" + itemLabel
								+ " (" + percent + "%)</span>");
			}
		}, new FlotClickHandler() {

			@Override
			public void onClicked(FlotPosition pos, FlotItem item) {
				String itemLabel = item.getLabel();
				presenter.click(itemLabel);
			}
		});
	}

}
