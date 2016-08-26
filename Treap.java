import java.io.*;
import java.util.*;


class Node<AnyType>
{
	//A note that can store Generic Data and that can set up a tree with left and right
	//sub nodes
	AnyType data;
	int priority;
	Node<AnyType> left, right;
	Node(AnyType data, int priority)
	{
		this.priority = priority;
		this.data = data;
	}
}

public class Treap <AnyType extends Comparable<AnyType>>
{
	HashSet<AnyType> table = new HashSet<AnyType>();
	HashSet<Integer> tablep = new HashSet<Integer>();
	
	private Node<AnyType> root;
	private int count = 0;
	//get root method for the Traversals.
	public Node<AnyType> getRoot(){
		return root;		
	}
	
	private Node<AnyType> rowRight(Node<AnyType> root){
		//creating two pointers for the rotation.
		Node<AnyType> rootOne;
		Node<AnyType> rootTwo;
		
		//Rotating right.
		rootOne = root;
		root = root.left;
		rootTwo = root;
		rootOne.left = rootTwo.right;
		root.right = rootOne;
		
			
			return root;
		}
	
	private Node<AnyType> rowLeft(Node<AnyType> root){
		//creating two pointers for the rotation.
		Node<AnyType> rootOne;
		Node<AnyType> rootTwo;
		
		//Rotating left.	
		rootOne = root;
		root = root.right;
		rootTwo = root;
		rootOne.right = rootTwo.left;
		root.left = rootOne;
		
		return root;
		}
	
	
	public void add(AnyType data){
		int out = 0;
		//creating the priority and importing the data.
		//Making it so if we have the same priority it trys again.
		while(out == 0){
		int b = (int)(Math.random() * Integer.MAX_VALUE) + 1;
		if(tablep.contains(b) != true){
			tablep.add(b);
		add(data, b);
		out++;
		}
		}
		
	}
	
	public void add(AnyType data, int priority){
		//making it so we dont have 2 data types that are the same in the tree.
			if(table.contains(data) != true){
				table.add(data);
				root = add(root,data,priority);
			}
		
		
	}
	
	private Node<AnyType> add(Node<AnyType> root , AnyType data , int priority){
		
		if(root == null){
			//adding new node to the tree and adding 1 to count for our max elements method.
			count++;
			return new Node<AnyType>(data,priority);
		}
		//moving down the tree to the right spot to insert.
		int Val = data.compareTo(root.data);
		if(Val < 0){
			root.left = add(root.left,data,priority);
		}else{
			root.right = add(root.right,data,priority);
		}
		//moving up the tree and rotating if necessary.
		int Val2 = data.compareTo(root.data);
		if(root.priority > priority){
			
			if(Val2 < 0){
				return rowRight(root);
			}else{
				return rowLeft(root);
			}
			
			
		}
		
		return root;
		
	}
	
	public void remove(AnyType data){
		//importing the data from public to private.
		root = remove(data,root);
		
	}
	private Node<AnyType> remove(AnyType data,Node<AnyType> root){
		//end of the tree element is not in the treap.
		if (root == null)
			return null;
		int Val;
		//seeing if this node is to be removed.
		Val = data.compareTo(root.data);
		//if node is to be removed and is a leaf node remove.
		if(Val == 0 && root.right == null && root.left== null){
			count--;
			return null;
		}
	
		//move though the tree rotating left and right when needed. Pushing the node to a leaf node.
		if(Val == 0){
			if(root.right == null){
				root = rowRight(root);
				root.right = remove(data , root.right);
			}else{
				if(root.left == null){
					root = rowLeft(root);
					root.left = remove(data , root.left);
				}else{
					
					if(root.right.priority < root.left.priority){
						root = rowLeft(root);
						root.left = remove(data,root.left);
					}else{
						root = rowRight(root);
						root.right = remove(data,root.right);
					}
				}
			}
		}
		
		
		//moving down the treap to find the node to be removed. 
		if(Val != 0){
			if(Val < 0){
				root.left = remove(data,root.left);
			}else{
				root.right = remove(data,root.right);
			}
		}
		
		
		return root;
	}
	public int size(){
		//the elements in the treap.
		return count;
	}
	
	// Returns true if the value is contained in the Treap, false otherwise.
		public boolean contains(AnyType data)
		{
			//public to private.
			return contains(root, data);
		}

		private boolean contains(Node<AnyType> root, AnyType data)
		{
			//if not in tree return false.
			if (root == null)
			{
				return false;
			}
			//Calling compareTo passing in Generic data and comparing it to the current node
			//and initializing the value to it.
			 int Val = (root.data.compareTo(data));
			 
			 //Taking the value and searching for value. Return true if found. 
			 if (Val > 0)
			{
				return contains(root.left, data);
			}
			else if (Val < 0)
			{
				return contains(root.right, data);
			}
			else
			{
				return true;
			}
		}
 

		//Calls height private method and passes in the root.
 	public int height(){
 		return height(root);
 	}
 	
 	private int height(Node<AnyType> root){
 		//if no root then -1 is returned.
 		if(root == null)
 			return -1;
 		//keeping track of the left and right sub trees.
 		int i=0,j=0;
 		//counting up the left and right sub trees.
 		i = 1 + height(root.left);
 		j = 1 + height(root.right);
 		
 		//returning the higher of the two sub trees.
 		if(i>j){
 			return i;
 		}else{
 			if(j>i)
 				return j;
 		}
 		
 		return i;
 		
 	}


 	
	public static void main(String [] args)
	{
		
		

}
}
