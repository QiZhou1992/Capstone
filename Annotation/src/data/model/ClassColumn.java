package data.model;


public class ClassColumn extends Column {
	private String represent;
	public ClassColumn(String name){
		super(name);
	}
	public void setRepresent(String represent){
		this.represent=represent;
	}
	public String Represent(){
		return this.represent;
	}
}

