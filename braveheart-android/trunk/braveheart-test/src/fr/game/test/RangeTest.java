package fr.game.test;

import junit.framework.Assert;
import android.test.AndroidTestCase;
import fr.android.api.Api;
import fr.game.engine.basics.Range;

public class RangeTest extends AndroidTestCase {
	static final double DELTA = 0.0001;

	public void test() throws Throwable{
		Api.start();
		Range r = Api.make(Range.class, 10.0f, 20.0f);
		Assert.assertEquals(20.0, r.getMax(),DELTA);
		float value = r.getRandomValue();
		Assert.assertTrue(value >= 10.0 && value <= 20.0);
	}
}
