import java.util.HashSet;
import java.util.Stack;


public class Maze {
	
	 public static void solve(char [][] maze){
		 
		 //initializing a HashSet Stack and the int , chars ect.
		 //hashSet to keep track of all spots visited
		 HashSet<Integer> hash = new HashSet<Integer>();
		 //stack to keep track of our path.
		 Stack<Integer> mazeList = new Stack<Integer>();
		 int i=1,j=1,peek=0;
		 int length = maze[0].length; 
		 char end = 's' ;
		 mazeList.push(maze[0].length*1+1);
		 hash.add(length*i+j);
		 
		 end = maze[1][1];
		 
		 // while loop that will run till we reach the end.
		 while(end != 'e'){
			 
			 //simple while loop for DPS that goes down as far as possible.
			 while(maze[i+1][j] != '#' && maze[i+1][j] != 'e' && !hash.contains(length*(i+1)+j)){
				 i++;
				 //saving the path.
				mazeList.push(length*i+j);
				//marking that we have all ready been here.
				hash.add(length*i+j);
				
				
			 }
			 
			//simple while loop for DPS that goes left as far as possible.
			 while(maze[i][j-1] != '#' && maze[i][j-1] != 'e' && !hash.contains(length*i+(j-1))){
					 j--;
					//saving the path.
					mazeList.push(length*i+j);
					//marking that we have all ready been here.
					hash.add(length*i+j);
					
					
				 }
			//simple while loop for DPS that goes right as far as possible.
			 while(maze[i][j+1] != '#' && maze[i][j+1] != 'e' && !hash.contains(length*i+j+1)){
					 j++;
					//saving the path.
					mazeList.push(length*i+j);
					//marking that we have all ready been here.
					hash.add(length*i+j);
					
				
				 }
			 
			//simple while loop for DPS that goes up as far as possible.
			 while(maze[i-1][j] != '#' && maze[i-1][j] != 'e' && !hash.contains(length*(i-1)+j)){
					 i--;
					//saving the path.
					mazeList.push(length*i+j);
					//marking that we have all ready been here.
					hash.add(length*i+j);
					
				 }
			 
			
			
			//Popping off the stack if we reach a dead end or can no long move to any new spots.
			 while(maze[i][j]!='s'&&(maze[i-1][j] == '#'|| hash.contains(length*(i-1)+j)) && (maze[i][j+1] == '#' || hash.contains(length*i+j+1))
					 &&(maze[i][j-1] == '#'||hash.contains(length*i+(j-1)))
					 &&(maze[i+1][j] == '#'|| hash.contains(length*(i+1)+j))){
				
				 mazeList.pop();
				 peek = mazeList.peek();
				 j = peek % length;
				 i = (peek-j)/length;
				 
				
				 
			 }
			 
			 //exiting the loop if we reach the end.
			 if(maze[i][j+1] == 'e'||maze[i+1][j] == 'e')
				 end = 'e';
			
		 }
		 
		 //popping off the stack to mark the way through the maze.
		 while(maze[i][j]!='s'&& maze[1][1] != 'e'){
			 maze[i][j]='.';
			 mazeList.pop();
			 peek = mazeList.peek();
			 j = peek % length;
			 i = (peek-j)/length;
			 
		 }
		 
		 
		 
		 
		 
	 }
	 
	

}
