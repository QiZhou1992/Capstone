package data.model;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;


public class Table implements MyData{
	private long identifier;
	private String title;
	private String description;
	private DataSet parentDataSet;
	private Map<Long,MyData> columns;
	public Table(String name,DataSet D1){
		this.identifier=System.currentTimeMillis();
		this.parentDataSet=D1;
		this.title=name;
		this.description="";
		columns=new HashMap<Long,MyData>();
	}
	public boolean isEmptyColumns(){
		return this.columns.isEmpty();
	}
	public boolean hasColumn(Column c1){
		return this.columns.containsKey(new Long(c1.getIdentifier()));
	}
	public void addColumn(Column c1){
		this.columns.put(new Long(c1.getIdentifier()), c1);
		c1.setBelongsTo(this);
	}
	public void removeColumn(Column c1){
		this.columns.remove(new Long(c1.getIdentifier()));
		c1.removeBelongsTo();
	}
	public boolean hasBelongsTo(){
		if(this.parentDataSet==null){
			return false;
		}
		else{
			return true;
		}
	}
	public void addBelongsTo(DataSet d1){
		this.parentDataSet=d1;
	}
	public void removeBelongsTo(){
		this.parentDataSet=null;
	}
	public DataSet parentDataSet(){
		return this.parentDataSet;
	}
	public Map<Long,MyData> AllColumn(){
		return this.columns;
		
	}
	@Override
	public int dataType() {
		// TODO Auto-generated method stub
		return 1;
	}
	@XmlElement(name = "title")
	@Override
	public String getTitle(){
		// TODO Auto-generated method stub
		return this.title;
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
		return this.identifier;
	}
}
