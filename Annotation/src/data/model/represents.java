package data.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class represents {
	static private Map<Integer,String> options;
	private int value;
	static boolean load;
	private represents(int value){
		this.value=value;
	}
	private static void load() throws IOException{
		options=new HashMap<Integer,String>();
		BufferedReader in=new BufferedReader(new FileReader("represents.txt"));
		int i=1;
		String represent;
		while((represent=in.readLine())!=null){
			options.put(i,represent);
			i++;
		}
		load=true;
	}
	public static represents oneRepresent(int value) throws IOException{
		if(!load){
			load();
			if(options.containsKey(value)){
				return new represents(value);
			}
			else{
				return new represents(0);
			}
		}
		else{
			if(options.containsKey(value)){
				return new represents(value);
			}
			else{
				return new represents(0);
			}
		} 
	}
	public Map<Integer,String> allOptions() throws IOException{
		if(!load){
			load();
			return this.options;
		}
		else{
			return this.options;
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
	/*public static int findValue(String name) throws IOException{
		if(!load){
			load();
		}
	} */
}
