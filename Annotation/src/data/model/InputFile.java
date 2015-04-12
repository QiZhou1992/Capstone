package data.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class InputFile {

	private ArrayList<String> content = new ArrayList<String>();
	
	public ArrayList<String> read(File inputfile) throws IOException {

	       
		BufferedReader reader = new BufferedReader(new FileReader(inputfile));
		String line = reader.readLine();
        String temp = new String();
                
        int length = line.length();
        
        for(int i=0;i<length;i++){
        	
        	if((line.charAt(i)==('"'))&&(i<length-1)){
        		i++;
        	}
        	if(line.charAt(i)==(',')){
        		i++;
        		content.add(temp);
        		temp="";
        	}
        	
        	temp=temp+Character.toString(line.charAt(i));
        	
        }
        
        
        content.add(temp);

		return content;
	       

	}
	
}
