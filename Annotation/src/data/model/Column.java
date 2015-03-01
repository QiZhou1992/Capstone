package data.model;

import javax.xml.bind.annotation.XmlElement;

public class Column implements MyData{
	private long identifier;
	private Table parentTable;
	private String title;
	private String description;
	/* for semanticRelation we will come back later */
	private String semanticRelation;
	public Column(String name){
		this.identifier=System.currentTimeMillis();
		this.title=name;
		this.description="";
	}
	public void modifiedDescription(String description){
		this.description=description;
	}
	public boolean hasBelongsTo(){
		if(this.parentTable==null){
			return false;
		}
		else{
			return true;
		}
	}
	public void setBelongsTo(Table t1){
		this.parentTable=t1;
	}
	public void removeBelongsTo(){
		this.parentTable=null;
	}
	public Table parentTable(){
		return this.parentTable;
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
		return 2;
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
