package data.model;

import java.io.IOException;
import java.io.PrintWriter;

public class JoinRelation {
	private ClassColumn source;
	private semanticRelations relation;
	private ClassColumn destiny;
	public JoinRelation(ClassColumn source,int relation,ClassColumn destiny) throws IOException{
		this.source=source;
		this.destiny=destiny;
		this.relation=semanticRelations.oneRepresent(relation);
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
		output.println(this.source.getIdentifier()+" "+this.relation.getString()+" "+this.destiny.getIdentifier());
	}
	

}
