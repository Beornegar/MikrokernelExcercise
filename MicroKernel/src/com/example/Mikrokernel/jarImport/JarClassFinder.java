package com.example.Mikrokernel.jarImport;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.example.Microkernel.jarUtils.ClassNameFinder;

public class JarClassFinder {

	public static List<Class<?>> importFile(File file, String interfaceName) {

		try {

			URL[] urls = { file.toURI().toURL() };
			URLClassLoader loader = new URLClassLoader(urls);
			
			Set<String> classNamesOfJar = ClassNameFinder.getClassNamesFromJarFile(file);		
			return getClassesAsPrinterFromJarWhoImplementPrinterInterface(loader, classNamesOfJar, interfaceName);
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return null;
	}
	
	private static List<Class<?>> getClassesAsPrinterFromJarWhoImplementPrinterInterface(URLClassLoader loader, Set<String> classNames, String interfaceName) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		
		List<Class<?>> allPrinterClasses = new ArrayList<>();
		
		for(String className : classNames) {
			Class<?> classOfJar = Class.forName(className, false, loader);
			
			for(Class<?> interfaceOfClass : classOfJar.getInterfaces()) {
				System.out.println("Interface: " + interfaceOfClass.getName());
				if(interfaceOfClass.getName().equals(interfaceName)) {
					allPrinterClasses.add(classOfJar);
					break;
				}
			}
			
		}
		return allPrinterClasses;
	}
	
}
