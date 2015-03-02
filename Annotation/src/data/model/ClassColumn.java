package data.model;

import java.io.IOException;


public class ClassColumn extends Column {
	private represents theRepresent;
	public ClassColumn(String name) throws IOException{
		super(name);
	}
	public void setRepresent(int value) throws IOException{
		this.theRepresent=represents.oneRepresent(value);
	}
	public represents Represent(){
		return this.theRepresent;
	}
	public void output(String path){
		
	}
}

