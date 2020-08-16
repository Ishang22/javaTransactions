package javaAssignmentFileRead;

import java.io.*; 
import java.util.*;
import java.time.*;


class transcation
{
	String transId;
	Boolean isdiff;
	long diffValue;
	transcation(String transId,Boolean isdiff,long diffValue)
	{
		this.transId=transId;
		this.isdiff=isdiff;
		this.diffValue=diffValue;
	}
	@Override
	public String toString() {
		return "transcation [transId=" + transId + ", isdiff=" + isdiff + ", diffValue=" + diffValue + "]";
	}
	
}

public class main {

	public static void main(String[] args) throws IOException {

		  /*
		    * reading file 
		    */
		 File file = new File("src/main/java/javaAssignmentFileRead/transaction.txt"); 
		 
		 HashMap<String,transcation> map=new HashMap<String,transcation>();//Creating HashMap    
 
         BufferedReader br = new BufferedReader(new FileReader(file)); 
		  
		 String st; 
		
		  while ((st = br.readLine()) != null) 
		  {
			  String[] arrOfStr = st.split(","); 
			    arrOfStr[0]=arrOfStr[0].trim();
			    
       		 String[] arrOfStr1 = arrOfStr[1].trim().split("–"); 
    		 
       		 String[] arrOfStr2 = arrOfStr[2].trim().split(" ");
       		 
       		 String[] arrOfStr3 = arrOfStr2[0].split(":");
       		 
       		 int noOfHr=Integer.parseInt(arrOfStr3[0]);
       		 
       		 if(arrOfStr2[1].equals("pm"))
       		 {
       			 noOfHr=noOfHr+12;
       		 }
       		 /*
  		    * logic to convert into mills
  		    */
       		 LocalDateTime date = LocalDateTime.of(Integer.parseInt(arrOfStr1[0].trim()),Integer.parseInt(arrOfStr1[1].trim()),Integer.parseInt(arrOfStr1[2].trim()),noOfHr,Integer.parseInt(arrOfStr3[1].trim()));
       		 long d = java.sql.Timestamp.valueOf( date ).getTime();
       		 
	        	if(map.containsKey(arrOfStr[0]) && !map.get(arrOfStr[0]).isdiff)
	        	{
	        		transcation t=new transcation(arrOfStr[0],true,d-map.get(arrOfStr[0]).diffValue);
	        		map.put(arrOfStr[0], t);
	        	}
	        	else
	        	{	 
	        		 transcation t=new transcation(arrOfStr[0],false,d);
	        		 map.put(arrOfStr[0], t);
	        	}
			  
		  }
		  
		  


		   Iterator<transcation> itr = map.values().iterator();
		   
		   /*
		    * to calculate total millis for each transaction
		    */
		   long millis=0;
		   /*
		    * total Number of transaction
		    */
		   int count=0;
		   
			while (itr.hasNext()) {
				transcation t=itr.next();
				if(t.isdiff)
				{
					millis=t.diffValue+millis;
					++count;
				}
			}
			   /*
			    * average transaction time
			    */
			millis=millis/(count*60*1000);
			System.out.println("Average minutes for transcation is "+millis+" minutes");

		
	}

}

