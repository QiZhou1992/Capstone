package data.model;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class InputFile {

	private String inputFile;

	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}

//	public ArrayList<ArrayList> read() throws IOException {
//		File inputWorkbook = new File(inputFile);
//		Workbook w;
//		try {
//			w = Workbook.getWorkbook(inputWorkbook);
//
//			int length = w.getNumberOfSheets();
//
//			for (int i = 0; i < length; i++) {
//				Sheet sheet = w.getSheet(i);
//				String name = sheet.getName();
//				ArrayList<String> table = new ArrayList<String>();
//				for (int j = 0; j < sheet.getColumns(); j++) {
//
//					Cell cell = sheet.getCell(j, 0);
//
//					table.add(cell.getContents());
//
//				}
//				dataset.add(table);
//
//			}
//
//		} catch (BiffException e) {
//			e.printStackTrace();
//		}
//
//		return dataset;
//	}

	
	@SuppressWarnings("resource")
	public Map<String, List<String>> readCsv() throws IOException {
		Map<String,List<String>> map = new HashMap<String,List<String>>();
		List<String> content = new ArrayList<String>();
	       
		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
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
		map.put("untitled", content) ;
		return map;      

	}

	
	
	public String getFileExtension(String name) {
	   
	    int lastIndexOf = name.lastIndexOf(".");
	    if (lastIndexOf == -1) {
	        return ""; 
	    }
	    return name.substring(lastIndexOf);
	}
	
	public Map<String, List<String>> readExcel() throws IOException {
		Map<String,List<String>> map = new HashMap<String,List<String>>();
		File inputWorkbook = new File(inputFile);
		Workbook w;
		try {
			w = Workbook.getWorkbook(inputWorkbook);
			int length = w.getNumberOfSheets();
			for (int i = 0; i < length; i++) {
				Sheet sheet = w.getSheet(i);
				String name = sheet.getName();
				List<String> table = new ArrayList<String>();
				for (int j = 0; j < sheet.getColumns(); j++) {

					Cell cell = sheet.getCell(j, 0);

					table.add(cell.getContents());

				}
				map.put(name, table);

			}

		} catch (BiffException e) {
			e.printStackTrace();
		}

		return map;
	}
/*
	public static void main(String[] args) throws IOException {
		InputFile test = new InputFile();
		test.setInputFile("data/sample.xls");
//		ArrayList<ArrayList> dataset1 = test.read();
//		int length = dataset1.size();
//		for (int i = 0; i < length; i++) {
//			System.out.println("table" + i + "\n" + "**********");
//			int tableSize = dataset1.get(i).size();
//			for (int j = 0; j < tableSize; j++) {
//				System.out.println(dataset1.get(i).get(j));
//			}
//
//		}
		System.out.print("new");
		Map<String, List<String>> dataset2=test.readExcel();
		System.out.print(dataset2.get("choices").get(0));
		
	}
*/
}