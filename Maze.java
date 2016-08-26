//92%David Gundler

//8% disjoint Sean Szumlanski

public class Maze {
	private static int last;
	private static int arrayrandom[];
	private static int disJoint[];
	private static int rank[];
	private static char array[][];
	private static int kone;
	private static int yone;
	
	private static void make(){
		//simple method to to make values for the randomization.
		 for (int i = 0 ; i< arrayrandom.length ;i++)
			 arrayrandom[i] = i;   
	}
	
	
	private static void makeSet(){
		
		 for (int i = 0 ; i< disJoint.length ;i++)
		   {//simple method to to make values for the disJoint elements and setting rank to 0.
		      
			 disJoint[i] = i;
		      rank[i] = 0;
		      
		   }
	}
	
	private static int findset(int x)
	{
		//if no parents must be root return
	   if (disJoint[x] == x)
	      return x;
	   //looking for root of set and compressing path taken.
	   disJoint[x] = findset(disJoint[x]);
	   return disJoint[x];
	}
	
	private static void union(int x,int y)
	{
	   int setx = findset(x);
	   int sety = findset(y);
	   
	   //testing the two values to see the if they are in the same sets.
	   	if(disJoint[setx] != disJoint[sety]){
	   		array[yone][kone] =' ';
			last--;
			//union the two sets together by the rank give.
	   if (rank[setx] < rank[sety])
		   disJoint[setx] = sety;
	   else if (rank[sety] < rank[setx])
		   disJoint[sety] = setx;
	   else
	   {
		   //if rank = setting adding one to the other rank + one. 
		   disJoint[sety] = setx;
	      rank[setx]++;
	   }
	   
	   
}
	}
	
	public static char [][] create(int width, int height){
		//initializing values
		disJoint = new int[width * height];
		rank = new int[width * height];
		arrayrandom = new int[(width) * (height - 1)+(width-1) * (height)];
		last = height * width - 1;
		int x = 0 ,l = 0, value1,value2;
		int end = (width) * (height - 1)+(width-1) * (height) - 1 ;
		int w = width*2+1;
		int h = height*2+1;
		int test=0;
		array = new char[h][w];
		int k;
		
		//setting up arrays for joint sets and random array.
		makeSet();
		make();
		
		
		// setting the end and start 
		array[1][1] = 's';
		array[h-2][w-2] = 'e';
		//two for loop to set up the 2D array setting all walls
		for(int i = 0 ; i < h ; i++ ){
			if(i%2 == 0){
				for(int j = 0 ; j < w; j++){
					array[i][j] = '#' ;
				}
			}
		}
		//two for loop to set up the 2D array setting all walls
		for(int i = 0 ; i < w ; i++ ){
			if(i%2 == 0){
				for(int j = 0 ; j < h; j++){
					array[j][i] = '#' ;
				}
			}
		}
		
		
		while(end != -1){
			//Randomizing using and array. Pick value save switch to the back  lower end pointer.
		int ranm =(int)(Math.random() * (end + 1));
		test = arrayrandom[ranm] + 1;
		arrayrandom[ranm] = arrayrandom[end];
		arrayrandom[end] = 0;
		//
		int m = (width-1)+width;
		int y = 0; 
		int z;
		
		//using mod and the wall value i find the high.
		k = test % m;
		y = test - k;
		z = (y/m)*2;
		if( k <= width-1)
			z++;
		else
			z=z+2;
		
		if(k==0){
			z--;
		}
		//using the found height with the wall value to find the width value.
	int temp;
		if(z % 2 == 1 ){
			temp = (z-1)/2;
			k = (test - (temp * (width - 1) + temp *(width)))*2;
		}
		
		if(z % 2 == 0){
			temp = (z)/2;
			k = (test - (temp * (width - 1) + (temp-1) *(width)))*2-1;
		}
		
		
		
		//if it is on on odd row finding the cell to the left and right
		if(z % 2 == 1){
			
			//calculating the cells to be joint to gather.
		x=(k-1)/2;
		l=(z-1)/2;
		value1 = l * width + x;
		x=(k+1)/2;
		l=(z-1)/2;
		value2  = l * width + x;
		
			kone = k;
			yone = z;
		
			union(value1,value2);
			
		}
			//if it on an even row find the cells up and down.
		if(z % 2 == 0){
			
			//calculating the cells to be joint to gather.
			x=(k-1)/2;
			l=(z-1)/2;
			value1 = l * width + x;
			x=(k-1)/2;
			l=(z)/2;
			value2 = l * width + x;
			
			kone = k;
			yone = z;
			
				union(value1,value2);
			
		}
		
		 
		
		//if last hit zero  we should have all cells connected.
		if(last == 0){
			return array;
		}
		//end if all walls checked.
		--end;
		}
		
		return array;
	}
	
	
	
	
}
