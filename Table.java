package project;

import java.util.HashMap;
import java.util.Map;


public class Table {
	private long identifier;
	private String title;
	private String description;
	private DataSet parentDataSet; //you are unable to get this, try use the parent data set identifier. 
	private long  parentDataSetID=0000;
	private Map<Long,Column> columns;
	public Table(String name,DataSet D1){
		this.identifier=System.currentTimeMillis();//can be duplicate
		//this.parentDataSet=D1; //This certainly gives a misstake here, where my colum points to null pointer
		
		this.title=name;
		columns=new HashMap<Long,Column>();
	}
	public long Identifier(){
		return this.identifier;
	}
	public void modifiedName(String name){
		this.title=name;
	}
	public String name(){
		return this.title;
	}
	public boolean isEmptyColumns(){
		return this.columns.isEmpty();
	}
	public boolean hasColumn(Column c1){
		return this.columns.containsKey(new Long(c1.Identifier()));
	}
	
	
	
	//////
	public Column GetColumn(long columnidentifier)
	{ try
	    {return columns.get(columnidentifier);}
	  catch(Exception e){
		  System.out.println("This column does not exist");
	  }
	   return null;
		
	}
	
	public void CopyTable(Table vt,Table ct)
	{
	 
	
		
	}
	
	public long getParentDataSetID()
	{
		return this.parentDataSetID;
	}
	
	public void changeParentDataSetID(long aid)
	{
		this.parentDataSetID=aid;
		
		
	}
	////////
	
	
	
	
	
	public void addColumn(Column c1){
		//
		c1.changeParentTableID(this.identifier);
		//
		
		this.columns.put(new Long(c1.Identifier()), c1);
		
	}
	public void removeColumn(Column c1){
		try{
		c1.changeParentTableID(0000);
		this.columns.remove(new Long(c1.Identifier()));
		}catch(Exception e)
		{System.out.println("Column does not exist");}
		
	}
	
	public void removeColumn(long columnid)
	 {  columns.get(columnid).changeParentTableID(0000);
		this.columns.remove(columnid);
	 }
	
	
	
	
	
	//
	public boolean hasBelongTo()
	{ if(this.parentDataSetID==0000)
	    {return false;}
	 else
		 return true;
	}
	//
	
	public boolean hasBelongsTo(){
		if(this.parentDataSet==null){
			return false;
		}
		else{
			return true;
		}
	}
	public void addBelongsTo(DataSet d1){//why don't we do it in DataSet automatically when we put a table into Dateset
		this.parentDataSet=d1;
	}
	public void removeBelongsTo(){//We can also do it automatically in the DataSet 
		this.parentDataSet=null;
	}
	public Table parentTable(){
		return this.parentTable();
	}
}