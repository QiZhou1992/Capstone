package data.model;

import java.util.HashSet;
import java.util.Set;

public class Validation {
	boolean result;
	Set<String> errorField;
	public Validation(){
		this.result=true;
		this.errorField=new HashSet<String>();
	}
	public void setFalse(){
		this.result=false;
	}
	public void addField(String field){
		this.errorField.add(field);
	}
	public boolean result(){
		return this.result;
	}
	public Set<String> ErrorField(){
		return this.errorField;
	}
	
}
