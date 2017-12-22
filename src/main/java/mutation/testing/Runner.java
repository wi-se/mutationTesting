package mutation.testing;

import mutation.testing.facade.Facade;
import mutation.testing.helper.PropertyLoader;

public class Runner {
	public static void main(String[] args) {
		Facade.getInstance().exec(PropertyLoader.getInstance().getProps("jar"));
	}
}
