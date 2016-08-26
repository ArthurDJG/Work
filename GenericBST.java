import java.io.*;
import java.util.*;


class Node<anyType>
{
	//A note that can store Generic Data and that can set up a tree with left and right
	//sub nodes
	anyType data;
	Node<anyType> left, right;
	Node(anyType data)
	{
		this.data = data;
	}
}


//Made Generic so any Data type that Implements Comparable my be passed in. 
public class GenericBST<anyType extends Comparable<anyType>>
{
	//creating the root
	private Node<anyType> root;

	public void insert( anyType data)
	{
		//Sending in the Generic data to the private method with the root.
		root = insert(this.root, data);
	}
	
	
	private Node<anyType> insert(Node<anyType> root, anyType data)
	{
		//inserting the Data at the proper node.
		if (root == null)
		{
			return new Node<anyType>(data);
		}
		
		//Calling compareTo passing in Generic data and comparing it  to the current node
		//and initializing the value to it.
		int Val = 0; 
		Val = (root.data.compareTo(data));
		
		
		//Taking the value and searching and inserting in to the tree based on the value. 
		 if (Val > 0)
		{
			root.left = insert(root.left, data);
		}
		else if (Val < 0)
		{
			root.right = insert(root.right, data);
		}
		else
		{
			// Stylistically, I have this here to explicitly state that we are
			// disallowing insertion of duplicate values.
			;
		}

		return root;
	}
	public void delete(anyType data)
	{
		//sending in the root and data to be delete from the public to private
		//method.
		root = delete(root, data);
	}

	private Node<anyType> delete(Node<anyType> root, anyType data)
	{
		//returning null if the node was not found
		if (root == null)
		{
			return null;
		}
		//Calling compareTo passing in Generic data and comparing it  to the current node
		//and initializing the value to it.
		int Val = 0; 
		Val = (root.data.compareTo(data));
		
		//Taking the value and searching for node to be deleted. 
		 if (Val > 0)
		{
			root.left = delete(root.left, data);
		}
		else if (Val < 0)
		{
			root.right = delete(root.right, data);
		}
		 //if value is not < or > then it is ==. found node to delete.  
		else
		{
			//Making sure not to drop any sub trees and nodes.
			//if right is null return left if left is null return right.
			//if nether is null finding and replace with the lefts max node.
			if (root.left == null && root.right == null)
			{
				return null;
			}
			else if (root.right == null)
			{
				return root.left;
			}
			else if (root.left == null)
			{
				return root.right;
			}
			else
			{
				root.data = findMax(root.left);
				root.left = delete(root.left, root.data);
			}
		}

		return root;
	}

	// This method assumes root is non-null, since this is only called by
	// delete() on the left subtree, and only when that subtree is non-empty.
	private anyType findMax(Node<anyType> root)
	{
		//finding the lefts max node.
		while (root.right != null)
		{
			root = root.right;
		}

		return root.data;
	}

	// Returns true if the value is contained in the BST, false otherwise.
	public boolean contains(anyType data)
	{
		//public to private.
		return contains(root, data);
	}

	private boolean contains(Node<anyType> root, anyType data)
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


	public void inorder()
	{
		//inorder Traversal of the tree public to private.
		System.out.print("In-order Traversal:");
		inorder(root);
		System.out.println();
	}

	private void inorder(Node<anyType> root)
	{
		//Traversal of tree and printing in inorder.
		if (root == null)
			return;

		inorder(root.left);
		System.out.print(" " + root.data);
		inorder(root.right);
	}

	public void preorder()
	{
		//preorder Traversal of the tree public to private.
		System.out.print("Pre-order Traversal:");
		preorder(root);
		System.out.println();
	}

	private void preorder(Node<anyType> root)
	{
		//Traversal of tree and printing in preorder.
		if (root == null)
			return;

		System.out.print(" " + root.data);
		preorder(root.left);
		preorder(root.right);
	}

	public void postorder()
	{
		//postorder Traversal of the tree public to private.
		System.out.print("Post-order Traversal:");
		postorder(root);
		System.out.println();
	}

	private void postorder(Node<anyType> root)
	{
		//Traversal of tree and printing in postorder.
		if (root == null)
			return;

		postorder(root.left);
		postorder(root.right);
		System.out.print(" " + root.data);
	}
	public static double difficultyRating(){
		return 3.6;	
	}
	
	public static double hoursSpent(){
		return 7.30;
	}
	

	public static void main(String [] args)
	{
		GenericBST<String> myTree = new GenericBST<String>();
		
		
			String r = "and";
			
			myTree.insert(r);
			r = "bat";
			
			myTree.insert(r);
				r = "cat";
			
			myTree.insert(r);
			r = "bat";
			
			myTree.delete(r);
			
		

		myTree.inorder();
		myTree.preorder();
		myTree.postorder();
		
		GenericBST<Integer> myTree2 = new GenericBST<Integer>();
		
		for (int i = 0; i < 5; i++)
		{
			int b = (int)(Math.random() * 100) + 1;
			System.out.println("Inserting " + b + "...");
			myTree2.insert(b);
		}

		myTree2.inorder();
		myTree2.preorder();
		myTree2.postorder();
		
GenericBST<Double> myTree3 = new GenericBST<Double>();
		
		for (int i = 0; i < 5; i++)
		{
			double c = (Math.random() * 100) + 1;
			System.out.println("Inserting " + c + "...");
			myTree3.insert(c);
		}

		myTree3.inorder();
		myTree3.preorder();
		myTree3.postorder();
	}
	}

