
public class MeasureColumn extends ClassColumn{
	private String unit;
	private String dimension;
	public MeasureColumn(String name){
		super(name);
	}
	public void setUnit(String unit){
		this.unit=unit;
	}
	public String Unit(){
		return this.unit;
	}
	public void setDimension(String dimension){
		this.dimension=dimension;
	}
	public String dimension(){
		return this.dimension;
	}
}
