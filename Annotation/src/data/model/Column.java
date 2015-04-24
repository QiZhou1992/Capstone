package data.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Column implements MyData{
	private long identifier;
	private Table parentTable;
	private String title;
	private StringProperty titleProperty;
	private String description;
	private semanticRelations theSemanticRelation;
	private int ColumnType;
	public Column(String name) throws IOException, InterruptedException{
		this.identifier=System.currentTimeMillis();
		TimeUnit.MILLISECONDS.sleep(10);
		this.title=name;
		this.titleProperty = new SimpleStringProperty(name);
		this.description="";
		this.theSemanticRelation=semanticRelations.oneRepresent(0);
		this.ColumnType=0;
	}
	public Validation check(){
		Validation result=new Validation();
		if(this.title.equals("")){
			result.setFalse();
			result.addField("title");
		}
		if(this.description.equals("")){
			result.setFalse();
			result.addField("description");
		}
		if(this.parentTable.getTableType()==0){
			if(this.theSemanticRelation.getValue()==0){
				result.setFalse();
				result.addField("semanticRelation");
			}
		}
		return result;
	}
	public int ColumnType(){
		return this.ColumnType;
	}
	public void setColumnType(int ColumnType){
		this.ColumnType=ColumnType;
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
	public StringProperty getTitleProperty(){
		return this.titleProperty;
	}
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
		this.titleProperty.set(name);
	}
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
	public void outputcheck(OutputCheck result){
		Validation v1=this.check();
		if(!v1.result()){
			result.setFalse();
			result.addColumnError(this, v1);
		}
	}
}
