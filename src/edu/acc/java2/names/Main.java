package edu.acc.java2.names;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main {
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Usage: java -jar names.jar <path-to-data-set> <hash|tree>");
			return;
		}
		
		// 1) Select storage set
		Set<String> names = null;
		if (args[1].equalsIgnoreCase("tree")) names = new TreeSet<String>();
		else if (args[1].equalsIgnoreCase("hash")) names = new HashSet<String>();
		else {
			System.out.printf("Unrecognized set '%s'; using HashSet\n", args[1]);
			names = new HashSet<String>();
		}
		
		// 2) Open and load data set - time the loading process
		long start, stop, loadMillis, printMillis;
		try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
			String line;
			start = System.nanoTime();
			while ((line = br.readLine()) != null)
				names.add(line);
			stop = System.nanoTime();
		} catch (IOException ioe) {
			System.out.printf("Error reading %s: %s\n", args[0], ioe.getMessage());
			return;
		}
		loadMillis = TimeUnit.NANOSECONDS.toMillis(stop-start);
		
		// 3) Print data set - time the printing process
		start = System.nanoTime();
		for (String name : names)
			System.out.println(name);
		stop = System.nanoTime();
		printMillis = TimeUnit.NANOSECONDS.toMillis(stop-start);
		System.out.printf("\nLoad time: %d milliseconds.\n\n", loadMillis);
		System.out.printf("\nPrint time: %d milliseconds.\n\n", printMillis);
	}
}