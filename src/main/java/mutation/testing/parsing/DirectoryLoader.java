package mutation.testing.parsing;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import mutation.testing.helper.PropertyLoader;

public class DirectoryLoader {
	private static final Log LOG = LogFactory.getLog(DirectoryLoader.class);
	private static DirectoryLoader instance;
	private String targetDir;

	private DirectoryLoader() {

	}

	public static DirectoryLoader getInstance() {
		if (instance == null) {
			instance = new DirectoryLoader();
		}

		return instance;
	}

	public void extractJarToDir(String pathToJar) {
		buildTargetDir();
		File targetFile = new File(targetDir);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
			LOG.info("target dir created at : " + targetDir);
		}
		LOG.info("begining the creation of the folder");
		File file = new File(pathToJar);
		try (ZipInputStream zis = new ZipInputStream(new FileInputStream(file))) {
			ZipEntry entry = zis.getNextEntry();
			while (entry != null) {
				File extract = new File(targetDir, entry.getName());
				if (entry.isDirectory()) {
					file.mkdirs();
				} else {
					File parent = extract.getParentFile();
					if (!parent.exists()) {
						parent.mkdirs();
					}
					try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(extract))) {
						byte[] buffer = new byte[Math.toIntExact(entry.getSize())];
						int location;

						while ((location = zis.read(buffer)) != -1) {
							bos.write(buffer, 0, location);
						}
					}
				}
				entry = zis.getNextEntry();
			}
			LOG.info("the extracting of the jar files finished");

		} catch (FileNotFoundException e) {
			LOG.error("unable to find requested file " + pathToJar, e);
		} catch (IOException e) {
			LOG.error("Not valid jar file " + pathToJar, e);
		}
	}

	private void buildTargetDir() {
		targetDir = System.getProperty("user.home") + File.separator
				+ PropertyLoader.getInstance().getProps("dir.name");
	}

	public String getTargetDir() {
		return targetDir;
	}
}
