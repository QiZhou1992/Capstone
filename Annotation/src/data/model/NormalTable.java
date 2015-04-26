package data.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class NormalTable extends Table{
	private Map<Long,Column> columns;
	private represents theRepresent;
	public NormalTable(String name, DataSet D1) throws IOException, InterruptedException {
		super(name, D1);
		super.setTableType(0);//normal table = 0
		columns=new HashMap<Long,Column>();
		theRepresent=represents.oneRepresent(0);
	}
	public void setRepresents(int value) throws IOException{
		this.theRepresent.setValue(value);
	}
	public represents theRepresent(){
		return this.theRepresent;
	}
	public boolean isEmptyColumns(){
		return this.columns.isEmpty();
	}
	public boolean hasColumn(Column c1){
		return this.columns.containsKey(new Long(c1.getIdentifier()));
	}
	public void addColumn(Column c1){
		this.columns.put(new Long(c1.getIdentifier()), c1);
		c1.setBelongsTo(this);
	}
	public void removeColumn(Column c1){
		this.columns.remove(new Long(c1.getIdentifier()));
		c1.removeBelongsTo();
	}
	public Map<Long,Column> AllColumn(){
		return this.columns;
		
	}
	@Override
	public void output(PrintWriter output){
		output.println(this.getIdentifier()+" rdf:Type "+"dfo:Table");
		super.output(output);
		output.println(this.getIdentifier()+" dfo:represents "+"\""+this.theRepresent.getString());
		for(Map.Entry<Long, Column> element: this.columns.entrySet()){
			element.getValue().output(output);
		}
	}
	@Override
	public Validation check(){
		Validation result=super.check();
		if(this.theRepresent.getValue()==0){
			result.setFalse();
			result.addField("theRepresent");
		}
		return result;
	}
	@Override
	public void outputcheck(OutputCheck result){
		Validation v1=this.check();
		if(!v1.result()){
			result.setFalse();
			result.addTableError(this, v1);
		}
		for(Map.Entry<Long, Column> element: this.columns.entrySet()){
			element.getValue().outputcheck(result);
		}
	}

}
