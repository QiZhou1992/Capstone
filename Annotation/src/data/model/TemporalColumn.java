package data.model;

import java.io.IOException;
import java.io.PrintWriter;


public class TemporalColumn extends ClassColumn{
	private int temporalType;
	private String temporalFormat;
	private String temporalGranularity;
	public TemporalColumn(String name) throws IOException{
		super(name);
		this.temporalType=1;
	}
	public void setTemporalType(int Type){
		this.temporalType=Type;
	}
	public int TemporalType(){
		return this.temporalType;
	}
	public void setTemporalFormat(String temporalFormat){
		this.temporalFormat=temporalFormat;
	}
	public String TemporalFormat(){
		return this.temporalFormat;
	}
	public void setTemporalGranularity(String temproalGranularity){
		this.temporalGranularity=temporalGranularity;
	}
	public String TemporalGranularity(){
		return this.temporalGranularity;
	}
	@Override
	public void output(PrintWriter output){
		output.println(this.getIdentifier()+" rdf:Type "+"dfo:Column");
		output.println(this.getIdentifier()+" dfo:belongsTo "+this.parentTable().getIdentifier());
		output.println(this.getIdentifier()+" dfo:isColumnType "+"dfo:TemporalType");
		if(this.temporalType==1){
			output.println(this.getIdentifier()+" dfo:isTemporalTypePartition "+"dfo:Interval");
		}
		else{
			output.println(this.getIdentifier()+" dfo:isTemporalTypePartition "+"dfo:TimeStamp");
		}
		output.println(this.getIdentifier()+" dfo:semanticRelation "+this.thisSemanticRelation().getString());
		output.println(this.getIdentifier()+" dct:title "+"\""+this.getTitle()+"\"");
		output.println(this.getIdentifier()+" dct:description "+"\""+this.getDescription()+"\"");
		output.println(this.getIdentifier()+" dfo:represents "+this.Represent().getString());
		output.println(this.getIdentifier()+" dfo:temporalFormat"+"\""+this.temporalFormat+"\"");
		output.println(this.getIdentifier()+" dfo:temporalGranularity"+"\""+this.temporalGranularity+"\"");
	}
}

