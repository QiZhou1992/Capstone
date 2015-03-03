package data.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class InputFile {

	private static ArrayList<String> content = new ArrayList<String>();
	
	public static void read(File inputfile) throws FileNotFoundException {

		Scanner scanner = new Scanner(inputfile);
		 scanner.useDelimiter(",");       
	       
	       while (scanner.hasNext())
	       {
	    	   content.add(scanner.next());
	       }
	
	       scanner.close();

	}
	
}
