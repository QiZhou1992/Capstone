package data.model;

import java.io.IOException;
import java.io.PrintWriter;


public class ClassColumn extends Column {
	private represents theRepresent;
	public ClassColumn(String name) throws IOException, InterruptedException{
		super(name);
		this.setColumnType(1);
		
	}
	public void setRepresent(int value) throws IOException{
		this.theRepresent=represents.oneRepresent(value);
	}
	public represents Represent(){
		return this.theRepresent;
	}
		@Override 
	public void output(PrintWriter output){
		output.println(this.getIdentifier()+" rdf:Type "+"dfo:Column");
		output.println(this.getIdentifier()+" dfo:belongsTo "+this.parentTable().getIdentifier());
		output.println(this.getIdentifier()+" dfo:isColumnType "+"dfo:ClassType");
		output.println(this.getIdentifier()+" dfo:semanticRelation "+this.thisSemanticRelation().getString());
		output.println(this.getIdentifier()+" dct:title "+"\""+this.getTitle()+"\"");
		output.println(this.getIdentifier()+" dct:description "+"\""+this.getDescription()+"\"");
		output.println(this.getIdentifier()+" dfo:represents "+this.theRepresent.getString());
	}
}

