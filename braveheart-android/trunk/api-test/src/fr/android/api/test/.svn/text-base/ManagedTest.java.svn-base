package fr.android.api.test;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.test.AndroidTestCase;
import fr.android.api.Api;
import fr.android.api.annotation.Managed;
import fr.android.api.annotation.Singleton;
import fr.android.api.singleton.Accessor;
import fr.android.api.test.data.ManagedObject;
import fr.android.api.test.data.ManagedObject.TestEnum;
import fr.android.api.test.data.ManagedSubObject;

@Managed
public class ManagedTest extends AndroidTestCase {
	@Singleton
	Accessor accessor;

	@SuppressWarnings("unchecked")
	public void testSaveLoad() throws Throwable {
		Api.start();

		assertNull(accessor);
		Api.manage(this);
		assertNotNull(accessor);

		String save;
		{
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
			save = Api.save(m);
		}

		{
			ManagedObject m = Api.load(ManagedObject.class,save);
			assertEquals("test[]",m.getString());
			assertEquals(12.5,m.getDouble());
			assertEquals(10000000,m.getLong());
			assertEquals(TestEnum.Val2,m.getEnum());
			assertTrue(m.getCollection().contains(1.0));
			assertTrue(m.getCollection().contains(2.2));
			assertTrue(m.getCollection().contains(3.3));
			assertEquals(m.getArray()[0][0],1.0);
			assertEquals(m.getArray()[0][1],2.0);
			assertEquals(m.getArray()[1][0],3.0);
			assertEquals(m.getArray()[1][1],4.0);
			Iterator<Double[]> it = m.getColArr().iterator();
			Double[] o = it.next();
			assertEquals(o[0],1.0);
			assertEquals(o[1],3.2);
			o = it.next();
			assertEquals(o[0],1.0);
			assertEquals(o[1],4.2);
			assertEquals("mama",m.getObject().getString());
			assertEquals("testValue",Api.get(m,"testPrivate"));
			Collection<Collection<Double>> col = (Collection<Collection<Double>>) Api.get(m, "privateCollection");
			Iterator<Collection<Double>> it2 = col.iterator();
			assertEquals(it2.next().iterator().next(),5.0);
			assertEquals(it2.next().iterator().next(),4.0);
			assertEquals(m.getObject(),m.getObjectSame());
			Map<ManagedSubObject,ManagedSubObject> map = (Map<ManagedSubObject,ManagedSubObject>) Api.get(m, "privateMap");
			assertTrue(map.containsKey(m.getObject()));
			assertEquals(3,map.size());
		}
	}

	public void testCreate() throws Throwable {
		Api.start();
		ManagedObject m = Api.make(ManagedObject.class,"test", 0.1, 1000000000L, 0.2,0.5, TestEnum.VAl1);
		assertTrue(m != null);
		assertEquals(m.getString(),"test");
	}

	public void testRecycle() throws Throwable {
		Api.start();
		ManagedObject m = Api.make(ManagedObject.class,"test", 0.1, 1000000000L, 0.2,0.5, TestEnum.VAl1);
		Api.recycle(m);
		assertEquals(m,Api.make(ManagedObject.class,"test", 0.1, 1000000000L, 0.2,0.5, TestEnum.VAl1));
	}
}

