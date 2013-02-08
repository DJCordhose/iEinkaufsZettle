/*
 * Copyright 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package eu.zeigermann.gwt.demo.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import eu.zeigermann.gwt.demo.shared.entity.ShoppingList;

public class MainView extends Composite {

	enum Mode {
		EDIT, CREATE
	};

	interface Binder extends UiBinder<Widget, MainView> {
	}

	@UiField(provided = true)
	ShoppingListTable table;

	@UiField(provided = true)
	SimplePager pager;

	@UiField
	TextBox nameTextBox;

	@UiField
	Button createButton;

	private MainPresenter presenter;

	private Mode mode = Mode.CREATE;

	public MainView(MainPresenter presenter) {
		this.presenter = presenter;
		initWidget(createWidget(presenter));
	}

	Widget createWidget(MainPresenter presenter) {
		table = new ShoppingListTable(presenter);

		SimplePager.Resources pagerResources = GWT
				.create(SimplePager.Resources.class);

		pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0,
				true);
		pager.setDisplay(table.getCellTable());
		Binder uiBinder = GWT.create(Binder.class);
		Widget widget = uiBinder.createAndBindUi(this);

		return widget;
	}

	@UiHandler("createButton")
	public void create(ClickEvent event) {
		createOrSave();
	}

	@UiHandler("nameTextBox")
	public void enter(KeyPressEvent event) {
		if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
			createOrSave();
		} else if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ESCAPE) {
			reset();
		}
	}

	private void createOrSave() {
		String text = nameTextBox.getText();
		if (text.length() != 0) {
			if (mode == Mode.EDIT) {
				presenter.saveList(text);
			} else {
				presenter.createList(text);
			}
		}
		reset();
	}

	public void reset() {
		nameTextBox.setText("");
		nameTextBox.setFocus(true);
		setMode(Mode.CREATE);
	}

	public void edit(ShoppingList list) {
		nameTextBox.setText(list.getName());
		nameTextBox.setFocus(true);
		setMode(Mode.EDIT);
	}

	private void setMode(Mode mode) {
		this.mode = mode;
		if (mode == Mode.EDIT) {
			createButton.setText("Save");
		} else {
			createButton.setText("Create");
		}
	}

}