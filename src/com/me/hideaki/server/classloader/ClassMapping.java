package com.me.hideaki.server.classloader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Scanner;

import com.me.hideaki.server.http.servelet.HttpServletOnlyGet;
import static com.me.hideaki.server.framework.ConstDifinistion.*;

public class ClassMapping {

	public HttpServletOnlyGet load(String dir) throws Exception {
		String clazzName = getClassName(dir);
		if (clazzName == null) {
			return null;
		}
		Class<?> clazz = Class.forName(SERVELET_PACKEGE + clazzName);
		return (HttpServletOnlyGet) clazz.newInstance();
	}

	private String getClassName(String dir) throws FileNotFoundException {

		File file = new File(".", MAPPING_TXT);

		if (!file.exists()) {
			throw new FileNotFoundException("not mappingfile");
		}
		String clazzName = null;
		Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(new FileInputStream(file))));
		while (scanner.hasNext()) {
			String line = scanner.nextLine();
			if ((line.length() == 0) || (line.startsWith(SHARP))) {
				continue;
			}
			String[] split = line.split(SPACE, 2);
			if (split[0].equals(dir)) {
				clazzName = split[1];
			}
		}
		scanner.close();
		return clazzName;
	}

}
