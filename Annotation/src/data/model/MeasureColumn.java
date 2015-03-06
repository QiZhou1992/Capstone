package data.model;

import java.io.IOException;
import java.io.PrintWriter;


public class MeasureColumn extends ClassColumn{
	private units theUnit;
	private dimensions theDimension;
	public MeasureColumn(String name) throws IOException, InterruptedException{
		super(name);
		this.setColumnType(2);
		this.theUnit=units.oneRepresent(0);
		this.theDimension=dimensions.oneRepresent(0);
	}
	public void setUnit(int value) throws IOException{
		this.theUnit.setValue(value);
	}
	public units Unit(){
		return this.theUnit;
	}
	public void setDimension(int value) throws IOException{
		this.theDimension.setValue(value);
	}
	public dimensions dimension(){
		return this.theDimension;
	}
	@Override
	public void output(PrintWriter output){
		output.println(this.getIdentifier()+" rdf:Type "+"dfo:Column");
		output.println(this.getIdentifier()+" dfo:belongsTo "+this.parentTable().getIdentifier());
		output.println(this.getIdentifier()+" dfo:isColumnType "+"dfo:MeasureType");
		output.println(this.getIdentifier()+" dfo:semanticRelation "+this.thisSemanticRelation().getString());
		output.println(this.getIdentifier()+" dct:title "+"\""+this.getTitle()+"\"");
		output.println(this.getIdentifier()+" dct:description "+"\""+this.getDescription()+"\"");
		output.println(this.getIdentifier()+" dfo:represents "+this.Represent().getString());
		output.println(this.getIdentifier()+" dfo:unit "+this.theUnit.getString());
		output.println(this.getIdentifier()+" dfo:dimension "+this.theDimension.getString());
	}
}

