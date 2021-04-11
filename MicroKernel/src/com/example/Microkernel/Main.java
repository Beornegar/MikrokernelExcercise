package com.example.Microkernel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.example.Microkernel.api.Printer;
import com.example.Microkernel.printerImport.PrinterJarImporter;

public class Main {

	private static final Timer timer = new Timer();
	private static final Timer printTimer = new Timer();
	
	private static List<Printer> allSerializerOfAllJars = Collections.synchronizedList(new ArrayList<>());
	
	public static void main(String[] args) {
				
		timer.scheduleAtFixedRate(new PrinterFinderTask(new PrinterJarImporter(), allSerializerOfAllJars), 0, 10000);
		
		printTimer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				for(Printer p : allSerializerOfAllJars) {
					p.print();
				}
			}
		}, 0, 5000);
	}

}
