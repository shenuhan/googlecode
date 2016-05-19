package fr.android.api.test.data.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import fr.android.api.annotation.Field;
import fr.android.api.annotation.Initializer;
import fr.android.api.test.data.ManagedObject;
import fr.android.api.test.data.ManagedSubObject;

public class ManagedObjectImpl implements ManagedObject {
	private String s;
	private Double d;
	private Long l;
	private TestEnum e;
	private Collection<Double> c;
	private Collection<Double[]> ca;
	private Double[][] a;
	private ManagedSubObject object;
	private ManagedSubObject object2;
	
	@Field
	protected String testPrivate;
	
	@Field
	protected Collection<Collection<Double>> privateCollection;
	
	@Field
	protected Map<ManagedSubObject,ManagedSubObject> privateMap;

	
	public ManagedObjectImpl() {
	}
	
	@Initializer
	public void initialize(String s, Double d, Long l, Double doubles, Double doubles2, TestEnum e) {
		setString(s);
		setDouble(d);
		setLong(l);
		setEnum(e);
		setCollection(Arrays.asList(new Double[] {doubles,doubles2}));
	}
	
	@Override
	public String getString() {
		return s;
	}

	@Override
	public void setString(String s) {
		this.s = s;
	}

	@Override
	public Double getDouble() {
		return d;
	}

	@Override
	public void setDouble(Double d) {
		this.d = d;
	}

	@Override
	public long getLong() {
		return l;
	}

	@Override
	public void setLong(long l) {
		this.l = l;
	}

	@Override
	public Collection<Double> getCollection() {
		return c;
	}

	@Override
	public void setCollection(Collection<Double> c) {
		this.c = c;
	}

	@Override
	public ManagedSubObject getObject() {
		return object;
	}

	@Override
	public void setObject(ManagedSubObject object) {
		this.object = object;
	}

	@Override
	public ManagedSubObject getObjectSame() {
		return object2;
	}

	@Override
	public void setObjectSame(ManagedSubObject object) {
		this.object2 = object;
	}

	@Override
	public Double[][] getArray() {
		return a;
	}

	@Override
	public void setArray(Double[][] c) {
		a = c;
	}

	@Override
	public Collection<Double[]> getColArr() {
		return ca;
	}

	@Override
	public void setColArr(Collection<Double[]> c) {
		ca = c;
	}

	@Override
	public TestEnum getEnum() {
		return this.e;
	}

	@Override
	public void setEnum(TestEnum e) {
		this.e = e;
	}

}
