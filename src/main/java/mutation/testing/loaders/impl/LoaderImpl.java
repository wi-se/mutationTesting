package mutation.testing.loaders.impl;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javassist.ClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.LoaderClassPath;
import javassist.NotFoundException;
import mutation.testing.filters.IFilter;
import mutation.testing.helper.PropertyLoader;
import mutation.testing.loaders.ILoader;
import mutation.testing.parsing.DirectoryLoader;

public class LoaderImpl implements ILoader {
	private ClassPool pool;
	private List<Pair> loadedClazz = new ArrayList<>();
	private static ILoader instance;
	private static final Log LOG = LogFactory.getLog(LoaderImpl.class);
	private int index = 0;
	private String pomFile;

	private LoaderImpl() {
		if (pool == null) {
			pool = ClassPool.getDefault();
		}
	}

	public static ILoader getInstance() {
		if (instance == null) {
			instance = new LoaderImpl();
		}
		return instance;
	}

	@Override
	public String getPom() {
		return this.pomFile;
	}

	@Override
	public void load(String pathToJar) {
		// Method which load from the extraction directory the class
		DirectoryLoader dirLoader = DirectoryLoader.getInstance();
		dirLoader.extractJarToDir(pathToJar);
		String target = dirLoader.getTargetDir();
		File file = new File(target);
		try {
			URL[] urls = new URL[] { file.toURI().toURL() };
			ClassLoader cl = new URLClassLoader(urls);
			ClassPath cp = new LoaderClassPath(cl);
			LOG.info("class path appended to the class pool");
			pool.appendClassPath(cp);
			processDir(file);
			LOG.info("Class to mutate are loaded");
		} catch (MalformedURLException e) {
			LOG.error("maformed URL when trying to load class from folder to class loader", e);
		}
	}

	private void processDir(File file) {
		LOG.info("Retrieve the class to mute from folder r" + file);
		List<File> list = new ArrayList<>(Arrays.asList(file.listFiles()));
		list.stream().forEach(f -> {
			LOG.debug("process the file : " + file.getAbsolutePath() + File.separator + file.getName());
			if (f.isDirectory()) {
				processDir(f);
			} else {
				// ignore if the file isn't a . class file
				if (!ignoreNoClassFile(f)) {
					Pair pair = retriveFullClassName(f);
					if (!ignoreWhenNoTestClass(pair)) {
						loadedClazz.add(pair);
						LOG.debug("Added class : " + pair.getAttachedPath());
					}
				}
			}
		});
	}

	private boolean ignoreNoClassFile(File file) {
		if (StringUtils.isEmpty(pomFile)) {
			savePomXML(file);
		}
		return !file.getName().endsWith(".class");
	}

	private boolean ignoreWhenNoTestClass(Pair pair) {
		if (pair == null || pair.getAttachedPath() == null) {
			return true;
		}
		String className = pair.getAttachedPath();
		String[] tab = className.split("\\.");
		tab[tab.length - 1] = tab[tab.length - 1] + "Test";
		StringBuilder sb = new StringBuilder("");
		for (int i = 0; i < tab.length; i++) {
			if (i != 0) {
				sb.append(".");
			}
			sb.append(tab[i]);
		}
		try {
			pool.get(sb.toString());
			pair.setAttachedClassTest(sb.toString());
			return false;
		} catch (NotFoundException e) {
			LOG.warn("No test are attached to this class : " + sb.toString());
		}
		return true;

	}

	/***
	 * <p>
	 * This method allows to process a class file in order to retrieve the full
	 * name <br/>
	 * <u>Example</u> <br/>
	 * <ul>
	 * <li>/home/user/projet/package1/package2/Application.class</li>
	 * <li>return : prject
	 * </ul>
	 * </p>
	 * 
	 * @param file
	 * @return
	 */
	private Pair retriveFullClassName(File file) {
		String absoultePath = file.getAbsolutePath();
		// filter the absolute path
		String targetDir = DirectoryLoader.getInstance().getTargetDir();
		absoultePath = absoultePath.replace(targetDir, "");
		if (absoultePath.substring(0, 1).equals(File.separator)) {
			absoultePath = absoultePath.substring(1, absoultePath.length());
		}
		// replace the file sperator by the "."
		absoultePath = absoultePath.replace(File.separator, ".");
		String className = absoultePath.replaceAll(".class", "");
		Pair pair = new Pair(file, className);
		return pair;
	}

	@Override
	public Pair get() {
		Pair p = null;
		if (loadedClazz.size() > index) {
			p = loadedClazz.get(index);
		}
		++index;
		return p;
	}

	@Override
	public List<CtMethod> getMethods(CtClass cc, IFilter filter) {
		if (cc == null || cc.isInterface()) {
			LOG.warn("The requested class passed as parameter was null. Unable to perform the request");
			return null;
		}
		List<CtMethod> list = new ArrayList<>(Arrays.asList(cc.getDeclaredMethods()));
		return filter.doFilter(list);
	}

	@Override
	public CtClass getClass(String className) {
		try {
			return pool.get(className);
		} catch (NotFoundException e) {
			LOG.error("The requested class was not found : " + className, e);

		}
		return null;
	}

	private void savePomXML(File file) {
		if (file.getName().equals(PropertyLoader.getInstance().getProps("pom.file"))) {
			this.pomFile = file.getAbsolutePath();
		}
	}

	@Override
	public void add(Pair p) {
		LOG.warn("THIS METHOD IS USED ONLY TO SHOW AN EXAMPLE");
		this.loadedClazz.add(p);
	}
}
