package project2;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.awt.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;



public class DataBase{
	
	@SuppressWarnings("deprecation")
	public static void main(String [ ] args) throws IOException
	{   DateFormat df = new SimpleDateFormat("yyyy/mm/dd");
	
	
	    
	    turnOnID();
	    //System.out.println(IDlist.toString());
		DataSet []dlis=new DataSet[100];
	    for(int i=0;i<100;i++)
	        {dlis[i]=new DataSet("dataset"+(i+700),"yyyy/mm/dd" ,createID());
	    	 RecordDataSet(dlis[i]);
	    	 System.out.println(dlis[i].getId());
	        }
		//System.out.println(IDlist.toString());
		//turnOffID();
		//RecordDataSet(dlis[2]);
		//RecordDataSet(dlis[1]);
		
	      
	  /* BufferedWriter twr=new BufferedWriter(new FileWriter(tmDF));
  	  BufferedReader rd=new BufferedReader(new FileReader(DFile));
  	  
  	  String line;
  	  while((line=rd.readLine())!=null)
  	  {if(line.contains("dataset1"))
  		  {System.out.println(line);
  	       for(int i=0;i<5;i++)
  		      rd.readLine();
  	      }
  	   else 
  		  {twr.write(line);
  		   twr.newLine();
  		  }
  	  }
  	  
  	  
  	  twr.close();
  	  rd.close();
	  BufferedReader rrd=new BufferedReader(new FileReader(tmDF));
	  BufferedWriter wwd=new BufferedWriter(new FileWriter(DFile));
      while((line=rrd.readLine())!=null)
    	  {wwd.write(line);
           wwd.newLine();
           }
	  rrd.close();
	  wwd.close();
	}*/
	
	//ID Parts
	DeleteData("dataset747");
	DeleteData("dataset113");
	}
	static File myFile =  new File("Idlist.txt");
    
	
	public static ArrayList<String> IDlist=new ArrayList<String>();
    public static boolean IDflag=false; 
    
    public static boolean IDisOn()
    {
    	return IDflag;
    	
    }
    
    
    public static void turnOnID() throws IOException
    {   IDflag=true;
    	String ids="";
    	String temp;
        try {
		BufferedReader br=new BufferedReader(new FileReader(myFile));
		while((temp=br.readLine())!=null)
        {ids=ids+temp;
        }
		String tp[]=ids.split(":");
		for(int i=0;i<tp.length;i++)
		{ if(tp[i].length()>2)
		  IDlist.add(tp[i].substring(0, tp[i].length()));
		}
		br.close();
        } catch (FileNotFoundException e) {
		    // TODO Auto-generated catch block
		   System.out.println("filenotFound");
		   e.printStackTrace();
		    
	    }
    	
        
    }
    
    
    
     public static void turnOffID() throws IOException
      {   
    	PrintWriter writer = new PrintWriter(myFile);
    	writer.print("");
    	writer.close(); //clear the filefile before you write
    	
    	String towrite;
        BufferedWriter bw=new BufferedWriter(new FileWriter(myFile));
    	while (!IDlist.isEmpty())
        {towrite=IDlist.remove(0);
        
         bw.write(towrite+":");	 
        }
    	bw.close();
    	
      }
    
    
     public static long createID()
      {if(IDisOn())
           {long id=System.currentTimeMillis();
            while(IDlist.contains(id+""))
            	id=id+1;
            IDlist.add(id+"");
            return id;   
           }
       else
          {
    	   System.out.println("ID is not On");
    	   
    	   return 0;
          }
      
         }
      public void deleteID(long aid)
        {IDlist.remove(aid);
    	  
    	  
        }
    
    
    //Record Table Parts:
   
      static File DFile =  new File("DataSetlist.txt");
      static File tmDF=new File("tempdf.txt");
      
      public static void RecordDataSet(DataSet ad) throws IOException
      {   
    	  BufferedWriter wr=new BufferedWriter(new FileWriter(DFile,true));  
    	  wr.newLine();
    	  wr.write("DataSetTitle:"+ad.Title());wr.newLine();
    	  wr.write("Identifier:"+ad.getId());wr.newLine();
    	  wr.write("CreateDate:"+ad.issued());wr.newLine();
    	  wr.write("LastModifier:"+ad.modified());wr.newLine();
    	  wr.write("Discription:"+ad.Description());wr.newLine();
    	  wr.write("||");
          wr.close();
          
      }
      
      
    	 
      public static void DeleteData(String datatitle) throws IOException
      {    BufferedWriter cl=new BufferedWriter(new FileWriter(tmDF));
           cl.write("");
           cl.close();
    	   BufferedWriter twr=new BufferedWriter(new FileWriter(tmDF));
           BufferedReader rd=new BufferedReader(new FileReader(DFile));
  	       boolean flag=false;
  	       String line;
  	       while((line=rd.readLine())!=null)
  	           {if(line.contains(datatitle))
  		            {System.out.println(line);flag=true;
  	                 for(int i=0;i<5;i++)
  		                rd.readLine();
  	                }
  	            else 
  		            {twr.write(line);
  		             twr.newLine();
  		             }
  	            }
  	  
  	  
  	       twr.close();
  	       rd.close();
	       BufferedReader rrd=new BufferedReader(new FileReader(tmDF));
	       BufferedWriter wwd=new BufferedWriter(new FileWriter(DFile));
           while((line=rrd.readLine())!=null)
    	        {wwd.write(line);
                 wwd.newLine();
                }
	       rrd.close();
	       wwd.close();
          if(!flag) System.out.println("Correspoinding DataSet is not Found");
      }
    
    
}



