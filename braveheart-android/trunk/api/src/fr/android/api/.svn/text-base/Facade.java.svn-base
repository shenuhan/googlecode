package fr.android.api;

import java.util.Set;

import fr.android.api.annotation.Initializer;
import fr.android.api.annotation.Managed;

@Managed
public interface Facade<ManagedObject> {
	@Initializer
	void initialize(ManagedObject implementation);
	
	ManagedObject getObject();
	
	String getField(String name);
	void setField(String name, String value);
	
	Set<String> getEditableFields();
}
