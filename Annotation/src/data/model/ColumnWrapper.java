package data.model;

import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "columns")
public class ColumnWrapper {
	private Map<Long,Column> columns;
	
	@XmlElement(name = "columnEntry")
	public Map<Long,Column> getColumns(){
		return this.columns;
	}
	
	public void setColumns(Map<Long,Column> columns){
		this.columns = columns;
	}
}
