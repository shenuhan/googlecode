package fr.android.api.test;

import android.test.AndroidTestCase;
import fr.android.api.Api;
import fr.android.api.singleton.Factory;
import fr.android.api.test.data.ManagedObject;

public class FactoryTest extends AndroidTestCase {
	public void test() throws Throwable {
		Api.start();
		Factory f = Api.singleton(Factory.class);
		assertNotNull("Cannot retrieve Factory", f);
		ManagedObject m = f.make(ManagedObject.class);
		assertNotNull("Cannot create Managed Object", m);
	}
}
