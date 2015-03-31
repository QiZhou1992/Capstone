package data.model;

import java.io.PrintWriter;

public interface MyData {
	
	public String getTitle();
	
	public void setTitle(String name);
	
	public String getDescription();
	
	public void setDesription(String description);
	
	public long getIdentifier();
	
	public void output(PrintWriter output);
	
	public Validation check();
	/*
	 * return the type of this 
	 * 0 for dataset
	 * 1 for table
	 * 2 for column
	 */
	public int dataType();

}
