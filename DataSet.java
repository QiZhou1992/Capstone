package project;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class DataSet {
	/* for the dateFormat, we will decided if each DateSet is individual or all the same */
	static DateFormat df;//You have more than one dataset, probably you don't want them be all sharing one.  
	private long Identifier;
	private String title;
	private String description;
	private Date created;
	final private Date issued; //null pointer I also add final to your date
	private Date modified;//null pointer
	private Map<Long,Table> tables;//null pointer 
	private Set<String> keywords;
	private String landingPage;
	public DataSet(String name, String DateFormatString){
		this.title=name;
		this.Identifier=System.currentTimeMillis();//I test it when two functions called in near time, this two long variables could be same
		
		//
		keywords=new HashSet();
		issued=new Date();
		modified=new Date();
		created=new Date();
		//
		
		this.issued.setTime(System.currentTimeMillis());
		this.modified.setTime(System.currentTimeMillis());
		this.df=new SimpleDateFormat(DateFormatString);
		this.tables=new HashMap<Long,Table>();
		this.keywords=new HashSet<String>();
		this.landingPage="";
	}
	public void ChangeModified(){
		this.modified.setTime(System.currentTimeMillis());
	}
	/* I'm not sure this function works here. If there is a bug, we will come back later*/
	public void inputCreated(String date) throws ParseException{
		this.created=df.parse(date);
		
	}
	public boolean checkIfhasTable(Table t1){
		return tables.containsKey(new Long(t1.Identifier()));
	}
	
	
	////
	public Table GetTable(long tableidentifier)
	{try
	   {return tables.get(tableidentifier);}
	 catch(Exception e)
	   {
		 
	   }
	System.out.println("This table is not in the dataset");
	return null;
		
	}
	///// It could detect if the table is there or get the table using identifier
	
	public boolean IfEmptyKeyword(){
		return keywords.isEmpty();
	}
	public boolean IfHasKeyword(String word){
		return keywords.contains(word);
	}
	public void removeKeyword(String word){
		keywords.remove(word);
	}
	public void addKeyword(String word){
		keywords.add(word);
	}
	public void ModifiedLandingPage(String url){
		this.landingPage=url;
	}
	public void addTable(Table t1){
		//
		t1.changeParentDataSetID(Identifier);
		//
		tables.put(t1.Identifier(), t1);
		this.ChangeModified();
	}
	public void RemoveTable(Table t1){
	    t1.changeParentDataSetID(0000);
		tables.remove(t1.Identifier());	
		this.ChangeModified();
	}
	
	
	
	//
	
	public void RemoveTable(long tableidentifier)
	{
		try
		{tables.get(tableidentifier).changeParentDataSetID(0000);
		 tables.remove(tableidentifier);
		 this.ChangeModified();
			
		}
		catch(Exception e)
		{
		 System.out.println("This table is not in the dataset");
		}
		
	}
	//
	
	
	
	public void modifiedTitle(String name){
		this.title=name;
	}
	public String Title(){
		return this.title;
	}
	public void modifiedDescription(String description){
		this.description=description;
	}
	public String Description(){
		return this.description;
	}
	public String Created(){
		String DateString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.created);
		return DateString;
		
	}
	public String issued(){
		String DateString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.issued);
		return DateString;
	}

	public String modified(){
		String DateString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.modified);
		return DateString;
	}
	//
	public long getId()
	{
		return this.Identifier;
	}
	
}