package project;
import java.text.DateFormat;
public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        System.out.println(System.currentTimeMillis());
        System.out.println(System.currentTimeMillis());
		DataSet d1=new DataSet("d1","07/30/1990");
		Table t1=new Table("Table1", d1);
		System.out.println(d1.modified());
	    //Table t2=d1.GetTable(t1.Identifier());
	    System.out.println(t1.getParentDataSetID());
	    //System.out.println(t2.Identifier());
	    
	    System.out.println(t1.Identifier()+"primitive ID");
         	
		System.out.println(d1.checkIfhasTable(t1));
		System.out.println(d1.IfEmptyKeyword());
		System.out.println(d1.IfHasKeyword("key1"));
		d1.addKeyword("key1");
		System.out.println(d1.IfEmptyKeyword());
		System.out.println(d1.IfHasKeyword("key1"));
		d1.removeKeyword("key1");
		d1.IfHasKeyword("key1");
		
		d1.checkIfhasTable(t1);
		d1.addTable(t1);
		System.out.println(t1.getParentDataSetID()+"changed"+d1.getId());//Sucessfully Changed The parent dataset. 
		d1.checkIfhasTable(t1);
		//d1.RemoveTable(t1);    //Could be used to change back belongings
		d1.RemoveTable(t1.Identifier());//could be used to change belongings
		System.out.println(t1.getParentDataSetID()+"changed1"+d1.getId());
	    d1.checkIfhasTable(t1);
	    System.out.println(d1.modified());
	    System.out.println(d1.Title());
	    System.out.println(d1.getId());//Dataset Identifier could be same
	    
	    d1.modifiedTitle("d2");
	    System.out.println(d1.Title());
	    
	    System.out.println(d1.issued());
	    System.out.println(d1.modified());
	    System.out.println(d1.Created());
	    
	    Column c1=new Column(12345);
	    System.out.println(c1.hasBelongto());
	    System.out.println(c1.getParentTableID());
	    t1.addColumn(c1);
	   
	    System.out.println(c1.getParentTableID());
	    System.out.println(c1.hasBelongto());
	    //t1.removeColumn(c1);
	    t1.removeColumn(c1.Identifier());
	    System.out.println(c1.getParentTableID());
	    System.out.println(c1.hasBelongto());
	    
	   
	   
	    
	    
	    

	}

}
