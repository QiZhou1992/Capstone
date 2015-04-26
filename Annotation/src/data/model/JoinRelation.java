package data.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public class JoinRelation {
	private long identifier;
	private ClassColumn source;
	private semanticRelations relation;
	private ClassColumn destiny;
	public JoinRelation(ClassColumn source,int relation,ClassColumn destiny) throws IOException, InterruptedException{
		this.source=source;
		this.destiny=destiny;
		this.relation=semanticRelations.oneRepresent(relation);
		this.identifier=System.currentTimeMillis();
		TimeUnit.MILLISECONDS.sleep(10);
	}
	public ClassColumn source(){
		return this.source;
	}
	public ClassColumn destiny(){
		return this.destiny;
	}
	public semanticRelations relation(){
		return this.relation;
	}
	public void output(PrintWriter output){
		output.println(this.identifier+" rdf:type "+"dfo:ColumnRelation");
		output.println(this.identifier+" dfo:source "+this.source.getIdentifier());
		output.println(this.identifier+" dfo:destination "+this.destiny().getIdentifier());
		output.println(this.identifier+" dfo:semanticRelation "+this.relation.getString());
	}
	

}
