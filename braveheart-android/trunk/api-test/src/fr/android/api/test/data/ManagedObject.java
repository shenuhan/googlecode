package fr.android.api.test.data;

import java.util.Collection;

import fr.android.api.annotation.Field;
import fr.android.api.annotation.Managed;

@Managed
public interface ManagedObject {
	public enum TestEnum {
		VAl1,
		Val2
	}

	@Field
	String getString();
	void setString(String s);

	@Field
	Double getDouble();
	void setDouble(Double d);

	@Field
	long getLong();
	void setLong(long l);

	@Field
	TestEnum getEnum();
	void setEnum(TestEnum e);

	@Field(editable=false)
	Collection<Double> getCollection();
	void setCollection(Collection<Double> c);

	@Field
	Collection<Double[]> getColArr();
	void setColArr(Collection<Double[]> c);

	@Field
	Double[][] getArray();
	void setArray(Double[][] c);

	@Field(editable=false)
	ManagedSubObject getObject();
	void setObject(ManagedSubObject object);

	@Field
	ManagedSubObject getObjectSame();
	void setObjectSame(ManagedSubObject object);
}
