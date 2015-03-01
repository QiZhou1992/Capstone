package data.model;

import java.io.IOException;


public class MeasureColumn extends ClassColumn{
	private units theUnit;
	private dimensions theDimension;
	public MeasureColumn(String name) throws IOException{
		super(name);
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
}

