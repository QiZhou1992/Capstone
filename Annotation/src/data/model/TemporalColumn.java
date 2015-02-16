
public class TemporalColumn extends ClassColumn{
	private int temporalType;
	private String temporalFormat;
	private String temporalGranularity;
	public TemporalColumn(String name){
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
}
