package com.example.Microkernel.printerImport;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.example.Microkernel.api.Printer;
import com.example.Mikrokernel.jarImport.JarClassFinder;

public class PrinterJarImporter implements PrinterImporter {

	private static final String PRINTER_INTERFACE = "com.example.Microkernel.api.Printer";

	@Override
	public List<Printer> importFile(File file) {
		
		List<Printer> allPrinterImplementations = new ArrayList<>();
		List<Class<?>> classes = JarClassFinder.importFile(file, PRINTER_INTERFACE);
		
		classes.forEach(c -> {
			try {
				allPrinterImplementations.add((Printer) c.getDeclaredConstructor().newInstance() );
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		});
		
		return allPrinterImplementations;
	}
	
	
	
}
