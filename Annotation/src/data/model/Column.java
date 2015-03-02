package data.model;

import java.io.IOException;
import java.io.PrintWriter;

import javax.xml.bind.annotation.XmlElement;

public class Column implements MyData{
	private long identifier;
	private Table parentTable;
	private String title;
	private String description;
	private semanticRelations theSemanticRelation;
	public Column(String name) throws IOException{
		this.identifier=System.currentTimeMillis();
		this.title=name;
		this.description="";
		this.theSemanticRelation=semanticRelations.oneRepresent(0);
	}
	public void modifiedDescription(String description){
		this.description=description;
	}
	public void modifiedSemanticRelations(int value) throws IOException{
		this.theSemanticRelation.setValue(value);
	}
	public semanticRelations thisSemanticRelation(){
		return this.theSemanticRelation;
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
	public void output(PrintWriter output){
		output.println(this.identifier+" rdf:Type "+"dfo:Column");
		output.println(this.identifier+" dfo:belongsTo "+this.parentTable.getIdentifier());
		output.println(this.identifier+" dfo:isColumnType "+"dfo:PropertyType");
		output.println(this.identifier+" dfo:semanticRelation "+this.theSemanticRelation.getString());
		output.println(this.identifier+" dct:title "+"\""+this.title+"\"");
		output.println(this.identifier+" dct:description "+"\""+this.description+"\"");
		
	}
}
