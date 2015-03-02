package data.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class dimensions {
	static private Map<Integer,String> options;
	private int value;
	static boolean load;
	private dimensions(int value){
		this.value=value;
	}
	private static void load() throws IOException{
		options=new HashMap<Integer,String>();
		BufferedReader in=new BufferedReader(new FileReader("dimensions.txt"));
		int i=1;
		String represent;
		while((represent=in.readLine())!=null){
			options.put(i,represent);
			i++;
		}
		load=true;
	}
	public static dimensions oneRepresent(int value) throws IOException{
		if(!load){
			load();
			if(options.containsKey(value)){
				return new dimensions(value);
			}
			else{
				return new dimensions(0);
			}
		}
		else{
			if(options.containsKey(value)){
				return new dimensions(value);
			}
			else{
				return new dimensions(0);
			}
		} 
	}
	public static Map<Integer,String> allOptions() throws IOException{
		if(!load){
			load();
			return options;
		}
		else{
			return options;
		}
	}
	public void setValue(int value) throws IOException{
		if(!load){
			load();
			if(options.containsKey(value)){
				this.value=value;
			}
			else{
				this.value=0;
			}
		}
		else{
			if(options.containsKey(value)){
				this.value=value;
			}
			else{
				this.value=0;
			}
		}
	}
	public int getValue(){
		return this.value;
	}
	public String getString(){
		if(options.containsKey(this.value)){
			return "";
		}
		else{
			return options.get(this.value);
		}
	}
	/*public static int findValue(String name) throws IOException{
		if(!load){
			load();
		}
	} */
}
