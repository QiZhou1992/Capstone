package project;

public class Column {
	final private long identifier;
	private Table parentTable;//better use a parenttable identifier. 
	private long parentTableID=0000;
	//
	
	//
	public long Identifier(){
		return this.identifier;
	}
	
	public Column(long aidentifier)
	{identifier=aidentifier;
		
	}
	
	public long getParentTableID()
	 { 
		return parentTableID;
	 } 
	public void changeParentTableID(long aid)
	{  parentTableID=aid;
		
	}
	
	public boolean hasBelongto()
	{
	 return (parentTableID!=0000);	
	}
	
}