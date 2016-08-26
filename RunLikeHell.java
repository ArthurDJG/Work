
import java.io.*;

public class RunLikeHell {
	
	public static int maxGain(int [] blocks){
		int max = 0 ;
		int test1 = 0;
		int test2 = 0;
		
		//checking for a null array
		if(blocks == null)
			return 0 ;
		//setting up and array to hold new values
		int array[] = new int[blocks.length]; 
		//checking to see if anything is in the array
		if(blocks.length == 0)
			return 0;
		//if only one thing in the array then return it
		if(blocks.length<2)
			return blocks[0];
		
		//setting up the first two in the new array.
		 array[0] = blocks[0];
		 array[1] = blocks[1];
		 
		 //Moving though the array once.
		 for(int i = 0 ; i < blocks.length ; i++){
			 
			 //making sure the spot is valid
			 if(i + 2 < blocks.length){
				 
				 //setting a new possible value to 
				 test1 = array[i] + blocks[i+2];
				 
				 //seeing if the new value is better and then replace it.
				 if(array[i+2]<=test1)
					 array[i+2] = test1;
				 
			 }
			//making sure the spot is valid
			 if(i + 3 < blocks.length){
				 
				//setting a new possible value to 
				 test2 = array[i] + blocks[i+3];
				 
				//seeing if the new value is better and then replace it.
				 if(array[i+3]<=test2)
					 array[i+3] = test2;
				 
			 }
		 }
		 //finding the bigger int  and returning it.
		if(array[array.length-1] > array[array.length-2])
			max = array[array.length-1];
		else
			max = array[array.length-2];
		
		return max;
		
	}
	
	 

}
