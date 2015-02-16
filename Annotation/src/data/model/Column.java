<<<<<<< HEAD
=======
package data.model;

public class Column implements MyData{
	private long identifier;
	private Table parentTable;
	private String title;
	private String description;
	/* for semanticRelation we will come back later */
	private String semanticRelation;
	public Column(String name){
		this.identifier=System.currentTimeMillis();
		this.title=name;
		this.description="";
	}
	public void modifiedTitle(String name){
		this.title=name;
	}
	public String Title(){
		return this.title;
	}
	public void modifiedDescription(String description){
		this.description=description;
	}
	public String Description(){
		return this.description;
	}
	public long Identifier(){
		return this.identifier;
	}
	public boolean hasBelongsTo(){
		if(this.parentTable==null){
			return false;
		}
		else{
			return true;
		}
	}
	public void setBelongsTo(Table t1){
		this.parentTable=t1;
	}
	public void removeBelongsTo(){
		this.parentTable=null;
	}
	public Table parentTable(){
		return this.parentTable;
	}
	@Override
	public String name() {
		// TODO Auto-generated method stub
		return this.title;
	}
}
>>>>>>> FETCH_HEAD
