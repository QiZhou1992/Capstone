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
	static DateFormat df;
	private long Identifier;
	private String title;
	private String description;
	private Date created;
	private Date issued;
	private Date modified;
	private Map<Long,Table> tables;
	private Set<String> keywords;
	private String landingPage;
	public DataSet(String name, String DateFormatString){
		this.title=name;
		this.Identifier=System.currentTimeMillis();
		this.created=new Date();
		this.issued=new Date();
		this.modified=new Date();
		this.tables=new HashMap<Long,Table>();
		this.keywords=new HashSet<String>();
		this.issued.setTime(System.currentTimeMillis());
		this.modified.setTime(System.currentTimeMillis());
		this.df=new SimpleDateFormat(DateFormatString);
		this.landingPage="";
	}
	public long getIdentifier(){
		return this.getIdentifier();
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
	public void ModifiedLandingPage(String url){
		this.landingPage=url;
	}
	public void addTable(Table t1){
		tables.put(new Long(t1.Identifier()), t1);
		t1.addBelongsTo(this);
		this.ChangeModified();
	}
	public void RemoveTable(Table t1){
		tables.remove(new Long(t1.Identifier()));
		t1.removeBelongsTo();
		this.ChangeModified();
	}
	public Map<Long, Table> AllTable(){
		return this.tables;
	}
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
}
