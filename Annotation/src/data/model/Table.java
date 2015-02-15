import java.util.HashMap;
import java.util.Map;


public class Table {
	private long identifier;
	private String title;
	private String description;
	private DataSet parentDataSet;
	private Map<Long,Column> columns;
	public Table(String name,DataSet D1){
		this.identifier=System.currentTimeMillis();
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
	public boolean isEmptyColumns(){
		return this.columns.isEmpty();
	}
	public boolean hasColumn(Column c1){
		return this.columns.containsKey(new Long(c1.Identifier()));
	}
	public void addColumn(Column c1){
		this.columns.put(new Long(c1.Identifier()), c1);
		c1.setBelongsTo(this);
	}
	public void removeColumn(Column c1){
		this.columns.remove(new Long(c1.Identifier()));
		c1.removeBelongsTo();
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
}
