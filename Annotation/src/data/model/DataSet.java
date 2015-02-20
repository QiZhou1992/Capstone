package data.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;


public class DataSet implements MyData{
	/* for the dateFormat, we will decided if each DateSet is individual or all the same */
	static DateFormat df;
	private long Identifier;
	private String title;
	private String description;
	private Date created;
	private Date issued;
	private Date modified;
	private Map<Long,MyData> tables;
	private Set<String> keywords;
	private String landingPage;
	public DataSet(String name, String DateFormatString){
		this.title=name;
		this.Identifier=System.currentTimeMillis();
		this.description="";
		this.created=new Date();
		this.issued=new Date();
		this.modified=new Date();
		this.tables=new HashMap<Long,MyData>();
		this.keywords=new HashSet<String>();
		this.issued.setTime(System.currentTimeMillis());
		this.modified.setTime(System.currentTimeMillis());
		this.df=new SimpleDateFormat(DateFormatString);
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
		return tables.containsKey(new Long(t1.getIdentifier()));
	}
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
	public Set<String> KeyWords(){
		return this.keywords;
	}
	@XmlElement(name = "landingPage")
	public String getLandingPage(){
		return this.landingPage;
	}
	public void setLandingPage(String url){
		this.landingPage=url;
	}
	public void addTable(Table t1){
		tables.put(new Long(t1.getIdentifier()), t1);
		t1.addBelongsTo(this);
		this.ChangeModified();
	}
	public void RemoveTable(Table t1){
		tables.remove(new Long(t1.getIdentifier()));
		t1.removeBelongsTo();
		this.ChangeModified();
	}
	public Map<Long, MyData> AllTable(){
		return this.tables;
	}
	public void modifiedDescription(String description){
		this.description=description;
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
	
	@XmlElement(name = "title")
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return this.title;
	}
	@Override
	public int dataType() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void setTitle(String name) {
		// TODO Auto-generated method stub
		this.title = name;
	}
	
	@XmlElement(name = "description")
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return this.description;
	}
	@Override
	public void setDesription(String description) {
		// TODO Auto-generated method stub
		this.description = description;
	}
	@XmlElement(name = "identifier")
	@Override
	public long getIdentifier() {
		// TODO Auto-generated method stub
		return this.Identifier;
	}
	
	
}
