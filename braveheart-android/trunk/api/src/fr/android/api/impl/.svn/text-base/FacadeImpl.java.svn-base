package fr.android.api.impl;

import java.util.Set;

import fr.android.api.Facade;
import fr.android.api.annotation.Singleton;
import fr.android.api.singleton.Accessor;
import fr.android.api.singleton.Formatter;

public class FacadeImpl<Managed> implements Facade<Managed>{
	@Singleton
	protected Accessor accessor;
	@Singleton
	protected Formatter formatter;

	private Managed object;


	@Override
	public void initialize(Managed implementation) {
		object = implementation;
	}

	@Override
	public Managed getObject() {
		return object;
	}

	@Override
	public String getField(String name) {
		return formatter.format(accessor.get(object, name));
	}

	@Override
	public void setField(String name, String value) {
		accessor.set(object, name, formatter.parse(accessor.getFieldsClass(object.getClass(), name), value));
	}

	@Override
	public Set<String> getEditableFields() {
		return accessor.getEditableFields(object.getClass());
	}

}
