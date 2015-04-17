package data.model;

import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TableType {

	private StringProperty title;
	private StringProperty typeString;
	private static Map<Integer,String> int2type;
	public TableType(String title, int type){
		setAllTypes();
		this.title = new SimpleStringProperty("default title");
		if(title.length()!=0){
			this.title.set(title);
		}
		
		if(int2type.containsKey(type)){
			this.typeString = new SimpleStringProperty(int2type.get(type));
		}else{
			this.typeString = new SimpleStringProperty(int2type.get(0));
		}
	}
	
	public StringProperty getTitle(){
		return this.title;
	}
	
	public void setTitle(String title){
		if(title.length()!=0){
			this.title.set(title);
		}
	}
	
	public StringProperty getType(){
		return this.typeString;
	}
	
	public void setType(int type){
		if(int2type.containsKey(type)){
			this.typeString.set(int2type.get(type));
		}
	}
	
	public static Map<Integer,String> allTypes(){
		setAllTypes();
		return TableType.int2type;
	}
	
	private static void setAllTypes(){
		if(int2type==null){
			int2type = new HashMap<Integer,String>();
			int2type.put(0,"normal");
			int2type.put(1,"joint");
		}
	}
}
