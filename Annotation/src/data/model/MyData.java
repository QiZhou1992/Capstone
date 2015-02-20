package data.model;

public interface MyData {
	
	public String getTitle();
	
	public void setTitle(String name);
	
	public String getDescription();
	
	public void setDesription(String description);
	
	public long getIdentifier();
	
	/*
	 * return the type of this 
	 * 0 for dataset
	 * 1 for table
	 * 2 for column
	 */
	public int dataType();

}
