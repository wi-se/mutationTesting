package mutation.testing.sample;

import java.io.File;

import mutation.testing.facade.Facade;
import mutation.testing.loaders.impl.LoaderImpl;
import mutation.testing.loaders.impl.Pair;

public class SampleRunner {
	public static void main(String[] args) {
		Pair pair = new Pair(new File("toto"), "mutation.testing.sample.Sample");
		pair.setAttachedClassTest("mutation.testing.sample.SampleTest");
		LoaderImpl.getInstance().add(pair);
		Facade.getInstance().exec("d:\\Profiles\\adjebarri\\Desktop\\mutation.testing-0.0.1-SNAPSHOT.jar");
	}
}
