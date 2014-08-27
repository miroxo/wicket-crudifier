/**
 * Copyright (C) 2014 Premium Minds.
 *
 * This file is part of wicket-crudifier.
 *
 * wicket-crudifier is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * wicket-crudifier is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with wicket-crudifier. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.wicket.crudifier.elements;

import java.io.Serializable;
import java.util.List;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.validation.IValidationError;

import com.premiumminds.webapp.wicket.bootstrap.BootstrapControlGroupFeedback;

public class ObjectChoiceControlGroup<T> extends AbstractControlGroup<T> {
	private static final long serialVersionUID = -8444849747715611613L;

	private DropDownChoice<T> dropDown;
	
	@SuppressWarnings("serial")
	public ObjectChoiceControlGroup(String id, IModel<T> model) {
		super(id, model);
		
		IModel<List<? extends T>> modelList = new LoadableDetachableModel<List<? extends T>>() {
			private static final long serialVersionUID = -3995535290067544541L;

			@SuppressWarnings("unchecked")
			@Override
			protected List<T> load() {
				return (List<T>) getConfiguration().getProviders().get(getPropertyName()).load();
			}
			
			
		};

		dropDown = new DropDownChoice<T>("input", getModel(), modelList){
			@Override
			public void error(IValidationError error) {
				MessageSource source = new MessageSource();
				Serializable message = error.getErrorMessage(source);
				
				super.error(message);
			}
		};
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		dropDown.setChoiceRenderer((IChoiceRenderer<T>) getConfiguration().getProviders().get(getPropertyName()).getRenderer());
		add(new BootstrapControlGroupFeedback("controlGroup").add(dropDown));
	}

	@Override
	public FormComponent<T> getFormComponent() {
		return dropDown;
	}
}
