import java.io.*;
import java.util.*;
//David Gundler

class Node
{
	//node to store numbers and next
	int data;
	Node next;
	Node(int data)
	{
		this.data = data;
	}
}

public class TopoPath {
	 public static boolean hasTopoPath(String filename) throws IOException{
		 
		 Scanner sc = new Scanner(new File(filename));
	
		 int vec = 0, num = 0 , hold = 0 , some = 0 , next = 0 , j = 1 , m = 0;
		 vec = sc.nextInt();
		 int n = 0;
		 //AJ list setup
		 Node array[] = new Node [vec];
		 //to keep track of all edges.
		 int arrayC[] = new int[vec+1];
		 Node up;
		 
		 //Creating a AJ list = adjacency list.
		 for(n = 0 ; n < vec ; n++){
			 //taking in how many edges
			  num = sc.nextInt(); 

			  if(num != 0){
				  hold = sc.nextInt();
				  array[n] = new Node(hold);
				  up = array[n];
				  arrayC[hold] = arrayC[hold]+1;
				  //add nodes to the list
			  for(int k = 1 ; k < num ; k++){
				  hold = sc.nextInt();
				  up.next = new Node(hold);
				  //adding to the edge count.
				  arrayC[hold] = arrayC[hold]+1;
				  up = up.next;
			  }
			  }else{
				  //add a dead end node.
				  array[n] = new Node(num);
				  arrayC[num] = arrayC[num]+1;
			  }
			 
		 }
		 
		 sc.close();
		 //if not equal to one dead end node return false
		 if(arrayC[0] > 1 || arrayC[0] < 1){
			 return false;
		 }
		 m = 1;
		 //looking for a head node and setting m to  the location in the array if found.
		 while((j != 0) && m <= vec){
			 if(arrayC[m]==0){
				  j = arrayC[m];
			 }else{
				m++; 	 
			 }	 
		 }
		 m--;
		 //if no head found return 0;
		 if(j != 0){
			 return false;
		 }
		 
		 do{
			up = array[m];
			
			//Subtracting one from edges of the node.
			 while(up != null){
				 --arrayC[up.data];
				 up = up.next;
			 }
			 //running though the AJ list and seeing if any of the edge values hit 
			 //Zero if not returning false.
			 up = array[m];
			 while(up != null){
				 if(arrayC[up.data] == 0){
					 //if we hit zero we got to a dead end.
					 next = up.data;
					 //setting up the next list.
					  m = up.data-1;
					 up = null;
				 }else{
					 up = up.next;
					 if(up == null){
						 return false;
					 }
				 }
			 }
			some ++;
			 
		 }while( next != 0);
		 
		 //if we don't hit all nodes returning false.
		 if(some != vec){
			return false; 
		 }
		 
		 
		 return true;
	 }
	

}

