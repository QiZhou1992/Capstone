package data.model;

import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Table {
	private long identifier;
	private String title;
	private String description;
	private DataSet parentDataSet;
	private Map<Long,Column> columns;
	public Table(String name,DataSet D1){
		this.identifier=System.currentTimeMillis(); //this seems wrong using system time (same ID)
		this.parentDataSet=D1;
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
	public String description(){
		return this.description;
	}
	public boolean isEmptyColumns(){
		return this.columns.isEmpty();
	}
	public boolean hasColumn(Column c1){
		return this.columns.containsKey(new Long(c1.Identifier()));
	}
	public void addColumn(Column c1){
		this.columns.put(new Long(c1.Identifier()), c1);
	}
	public void removeColumn(Column c1){
		this.columns.remove(new Long(c1.Identifier()));
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
	public Table parentTable(){
		return this.parentTable();
	}
	
	/*
	 * test function below
	 */
	public StringProperty findIdentifier(){
		StringProperty s = new SimpleStringProperty(String.valueOf(title));
		return s;
	}
}

