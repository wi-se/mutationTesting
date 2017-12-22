package mutation.testing.sample;

import org.junit.Before;
import org.junit.Test;

public class SampleTest {
	private Sample sample = new Sample();

	@Before
	public void init() {
		sample.setX(3);
		sample.setY(2);
	}

	@Test
	public void testIsXEqual() {
		assert (sample.isXEqual(3));
	}

	@Test
	public void testIsYEqual() {
		assert (sample.isYEqual(2));
	}

	@Test
	public void testAdd() {
		sample.add(1);
		assert (sample.getX() == 4);
	}

	@Test
	public void testSub() {
		sample.sub(0);
		assert (sample.getX() == 3);
	}

	@Test
	public void testMutl() {
		sample.mult(1);
		assert (sample.getX() == 3);
	}

	@Test
	public void testDiv() {
		sample.div(1);
		assert (sample.getX() == 3);
	}
}
