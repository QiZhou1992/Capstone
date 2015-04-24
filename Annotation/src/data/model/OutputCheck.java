package data.model;

import java.util.HashMap;
import java.util.Map;

public class OutputCheck {
	private boolean result;
	private Map<Table,Validation> tableError;
	private Map<Column,Validation> columnError;
	public OutputCheck(){
		this.result=true;
		this.tableError=new HashMap<Table,Validation>();
		this.columnError=new HashMap<Column,Validation>();
	}
	public void setFalse(){
		this.result=false;
	}
	public void addTableError(Table t1,Validation v1){
		this.tableError.put(t1, v1);
	}
	public void addColumnError(Column c1, Validation v1){
		this.columnError.put(c1, v1);
	}
	public boolean result(){
		return this.result;
	}
	public Map<Table,Validation> TableError(){
		return this.tableError;
	}
	public Map<Column,Validation> ColumnError(){
		return this.columnError;
	}

}
