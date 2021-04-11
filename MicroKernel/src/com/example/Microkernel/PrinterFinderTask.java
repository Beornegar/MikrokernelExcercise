package com.example.Microkernel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.stream.Stream;

import com.example.Microkernel.api.Printer;
import com.example.Microkernel.printerImport.PrinterImporter;

public class PrinterFinderTask extends TimerTask {

	private PrinterImporter importer;

	private List<Printer> allPrinter;
	private List<File> importedJars = new ArrayList<>();

	public PrinterFinderTask(PrinterImporter importer, List<Printer> allPrinter) {
		this.importer = importer;
		this.allPrinter = allPrinter;
	}

	@Override
	public void run() {

		String workingDirectory = new File(".").getAbsolutePath();

		try (Stream<Path> paths = Files.walk(Paths.get(workingDirectory))) {
			paths.filter(this::isPathImportFolder).forEach(this::addAllNewJars);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean isPathImportFolder(Path path) {
		return path.toFile().getName().contains("com.example.Microkernel.importJars")
				&& !path.toString().contains("src");
	}

	private void addAllNewJars(Path path) {
		for (File jar : path.toFile().listFiles()) {
			addSerializerFromJarToList(jar);
		}
	}

	private void addSerializerFromJarToList(File jarFile) {
		System.out.println(jarFile);
		try {
			List<Printer> foundPrinter = importer.importFile(jarFile);

			if (foundPrinter != null && !importedJars.contains(jarFile)) {
				synchronized (allPrinter) {
					allPrinter.addAll(foundPrinter);
				}

				importedJars.add(jarFile);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
