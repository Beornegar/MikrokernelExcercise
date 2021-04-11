package com.example.Microkernel.printerImport;

import java.io.File;
import java.util.List;

import com.example.Microkernel.api.Printer;

public interface PrinterImporter {

	public List<Printer> importFile(File file) throws Exception;
	
}
