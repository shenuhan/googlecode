package fr.android.api.test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import android.test.AndroidTestCase;
import fr.android.api.Api;
import fr.android.api.Facade;
import fr.android.api.test.data.ManagedObject;
import fr.android.api.test.data.ManagedObject.TestEnum;
import fr.android.api.test.data.ManagedSubObject;

public class FacadeTest extends AndroidTestCase {
	@SuppressWarnings("unchecked")
	public void testFacade() throws Throwable {
		Api.start();

		ManagedObject m = Api.make(ManagedObject.class);
		m.setString("test[]");
		m.setDouble(12.5);
		m.setLong(10000000);
		m.setEnum(TestEnum.Val2);
		m.setCollection(Arrays.asList(1.0 , 2.2, 3.3));
		m.setColArr(Arrays.asList(new Double[]{1.0 , 3.2}, new Double[]{1.0 , 4.2}));
		m.setArray(new Double[][]{{1.0,2.0},{3.0,4.0}});
		m.setObject(Api.make(ManagedSubObject.class));
		m.getObject().setString("mama");
		m.setObjectSame(m.getObject());
		Api.set(m, "testPrivate", "testValue");
		Api.set(m, "privateCollection", Arrays.asList(Arrays.asList(5.0 , 3.2), Arrays.asList(4.0 , 4.2)));
		Map<ManagedSubObject,ManagedSubObject> map = new HashMap<ManagedSubObject, ManagedSubObject>();
		map.put(Api.make(ManagedSubObject.class), Api.make(ManagedSubObject.class));
		map.put(m.getObject(), Api.make(ManagedSubObject.class));
		map.put(Api.make(ManagedSubObject.class), m.getObject());
		Api.set(m, "privateMap", map);

		Facade<ManagedObject> facade = Api.facade(m);
		Set<String> fields = facade.getEditableFields();

		assertTrue(fields.contains("objectSame.string"));
		assertTrue(fields.contains("testPrivate"));
		assertTrue(fields.contains("string"));
		assertFalse(fields.contains("object.string"));
		assertTrue(fields.contains("enum"));
		assertTrue(fields.contains("long"));
		assertTrue(fields.contains("double"));
		assertFalse(fields.contains("collection"));

		assertEquals("mama",facade.getField("objectSame.string"));
		assertEquals("testValue",facade.getField("testPrivate"));
		assertEquals("test[]",facade.getField("string"));
		assertEquals("Val2",facade.getField("enum"));
		assertEquals("10000000",facade.getField("long"));
		assertEquals("12.5",facade.getField("double"));
	}
}

