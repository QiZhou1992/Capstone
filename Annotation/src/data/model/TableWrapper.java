package data.model;

import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "tables")
public class TableWrapper {
	private Map<Long,Table> tables;
	
	@XmlElement(name = "entry")
	public Map<Long,Table> getTables(){
		return this.tables;
	}
	
	public void setTables(Map<Long,Table> tables){
		this.tables = tables;
	}

}
