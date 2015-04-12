package data.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;


public class Table implements MyData{
	private long identifier;
	private String title;
	private String description;
	private DataSet parentDataSet;
	public Table(String name,DataSet D1) throws IOException{
		this.identifier=System.currentTimeMillis();
		this.parentDataSet=D1;
		this.title=name;
		this.description="";
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
	@Override
	public int dataType() {
		return 1;
	}
	@XmlElement(name = "title")
	@Override
	public String getTitle(){
		return this.title;
	}
	@Override
	public void setTitle(String name) {
		this.title = name;
	}
	@XmlElement(name = "description")
	@Override
	public String getDescription() {
		return this.description;
	}
	@Override
	public void setDesription(String description) {
		this.description = description;
	}
	@XmlElement(name = "identifier")
	@Override
	public long getIdentifier() {
		return this.identifier;
	}
	public void output(PrintWriter output){
		output.println(this.identifier+" dfo:belongsTo "+this.parentDataSet.getIdentifier());
		output.println(this.identifier+" dct:title "+"\""+this.title+"\"");
		output.println(this.identifier+" dct:description "+"\""+this.description+"\"");
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
		return result;
	}
}
