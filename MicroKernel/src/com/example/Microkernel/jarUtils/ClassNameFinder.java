package com.example.Microkernel.jarUtils;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassNameFinder {

	public static Set<String> getClassNamesFromJarFile(File givenFile) throws IOException {
	    Set<String> classNames = new HashSet<>();
	    try (JarFile jarFile = new JarFile(givenFile)) {
	        Enumeration<JarEntry> e = jarFile.entries();
	        while (e.hasMoreElements()) {
	            JarEntry jarEntry = e.nextElement();
	            if (jarEntry.getName().endsWith(".class")) {
	                String className = jarEntry.getName()
	                  .replace("/", ".")
	                  .replace(".class", "");
	                classNames.add(className);
	            }
	        }
	        return classNames;
	    }
	}
	
}
