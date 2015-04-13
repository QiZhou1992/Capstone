package data.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class JoinTable extends Table {
	private Map<Long,ClassColumn> columns;
	private Map<Integer,JoinRelation> relations;
    private int numberOfRealtion;
	public JoinTable(String name, DataSet D1) throws IOException {
		super(name, D1);
		super.setTableType(1);//joint table = 1
		this.relations=new HashMap<Integer,JoinRelation>();
		this.columns=new HashMap<Long,ClassColumn>();
		this.numberOfRealtion=0;
	}
	public void addNewRelation(JoinRelation NewRelation){
		this.numberOfRealtion++;
		this.relations.put(this.numberOfRealtion, NewRelation);
	}
	public void removeRelation(int key){
		this.relations.remove(key);
	}
	public void addOldRelation(int key, JoinRelation NewRelation){
		this.relations.put(key, NewRelation);
	}
	public Map<Integer,JoinRelation> allRelations(){
		return this.relations;
	}
	public boolean isEmptyColumns(){
		return this.columns.isEmpty();
	}
	public boolean hasColumn(ClassColumn c1){
		return this.columns.containsKey(new Long(c1.getIdentifier()));
	}
	public void addColumn(ClassColumn c1){
		this.columns.put(new Long(c1.getIdentifier()), c1);
		c1.setBelongsTo(this);
	}
	public void removeColumn(ClassColumn c1){
		this.columns.remove(new Long(c1.getIdentifier()));
		c1.removeBelongsTo();
	}
	public Map<Long,ClassColumn> AllColumn(){
		return this.columns;
	}
	@Override
	public void output(PrintWriter output){
		output.println(this.getIdentifier()+" rdf:Type "+"dfo:JoinTable");
		super.output(output);
		for(Map.Entry<Long, ClassColumn> element: this.columns.entrySet()){
			element.getValue().output(output);
		}
		for(Map.Entry<Integer, JoinRelation> element:this.relations.entrySet()){
			element.getValue().output(output);
		}
	}
	@Override
	public Validation check(){
		Validation result=super.check();
		Set<Long> AllIdentifier=new HashSet<Long>();
		for(Map.Entry<Long, ClassColumn> element: this.columns.entrySet()){
			long value=element.getKey();
			AllIdentifier.add(value);
		}
		for(Map.Entry<Integer, JoinRelation> element:this.relations.entrySet()){
			JoinRelation theOne=element.getValue();
			long thisIdentifier=theOne.source().getIdentifier();
			if(AllIdentifier.contains(thisIdentifier)){
				AllIdentifier.remove(thisIdentifier);
			}
			thisIdentifier=theOne.destiny().getIdentifier();
			if(AllIdentifier.contains(thisIdentifier)){
				AllIdentifier.remove(thisIdentifier);
			}
		}
		if(!AllIdentifier.isEmpty()){
			result.setFalse();
			result.addField("some column don't have relation");
		}
		return result;
	}

}
