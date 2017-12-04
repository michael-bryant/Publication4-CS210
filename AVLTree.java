import java.util.Comparator;

/*
 *  Java Program to Implement AVL Tree
 *  
 *  Code taken from 'http://www.sanfoundry.com/java-program-implement-avl-tree/'
 */



/* Class AVLNode */
class AVLNode<T>
{    
	AVLNode<T> left, right;
	Entry entry;
	int height;

	/* Constructor */
	public AVLNode()
	{
		left = null;
		right = null;
		entry = null;
		height = 0;
	}
	/* Constructor */
	public AVLNode(Entry n)
	{
		left = null;
		right = null;
		entry = n;
		height = 0;
	}   


}

/* Class AVLTree */
class AVLTree<T extends Comparable<T>>
{
	public AVLNode<T> root; 
	public Comparator<Entry> comparator;

	/* Constructor */
	public AVLTree()
	{
		root = null;
	}
	/* Function to check if tree is empty */
	public AVLTree(Comparator<Entry> c) {
		this.comparator= c;
		root= null;
	}
	public boolean isEmpty()
	{
		return root == null;
	}
	/* Make the tree logically empty */
	public void makeEmpty()
	{
		root = null;
	}
	/* Function to insert data */
	public void insert(Entry data)
	{
		root = insert(data, root);
	}
	/* Function to get height of node */
	private int height(AVLNode<T> t )
	{
		return t == null ? -1 : t.height;
	}
	/* Function to find max height of left/right node */
	private int max(int lhs, int rhs)
	{
		return lhs > rhs ? lhs : rhs;
	}
	/* Function to insert data recursively */

	private AVLNode<T> insert(Entry x, AVLNode<T> root)
	{
		if (root == null)
			root = new AVLNode<T>(x);
		else if (compareThis(x, root.entry)<0)
		{
			root.left = insert( x, root.left );
			if( height( root.left ) - height( root.right ) == 2 )
				if(compareThis(x, root.left.entry)<0)
					root = rotateWithLeftChild( root );
				else
					root = doubleWithLeftChild( root );
		}
		else if(compareThis(x, root.entry)>0)
		{
			root.right = insert( x, root.right );
			if( height( root.right ) - height( root.left ) == 2 )
				if(compareThis(x, root.right.entry)>0)
					root = rotateWithRightChild( root );
				else
					root = doubleWithRightChild( root );
		}
		else
			;  // Duplicate; do nothing
		root.height = max( height( root.left ), height( root.right ) ) + 1;
		return root;
	}
	/* Rotate binary tree node with left child */     
	private AVLNode<T> rotateWithLeftChild(AVLNode<T> k2)
	{
		AVLNode<T> k1 = k2.left;
		k2.left = k1.right;
		k1.right = k2;
		k2.height = max( height( k2.left ), height( k2.right ) ) + 1;
		k1.height = max( height( k1.left ), k2.height ) + 1;
		return k1;
	}

	/* Rotate binary tree node with right child */
	private AVLNode<T> rotateWithRightChild(AVLNode<T> k1)
	{
		AVLNode<T> k2 = k1.right;
		k1.right = k2.left;
		k2.left = k1;
		k1.height = max( height( k1.left ), height( k1.right ) ) + 1;
		k2.height = max( height( k2.right ), k1.height ) + 1;
		return k2;
	}
	/**
	 * Double rotate binary tree node: first left child
	 * with its right child; then node k3 with new left child */
	private AVLNode<T> doubleWithLeftChild(AVLNode<T> k3)
	{
		k3.left = rotateWithRightChild( k3.left );
		return rotateWithLeftChild( k3 );
	}
	/**
	 * Double rotate binary tree node: first right child
	 * with its left child; then node k1 with new right child */      
	private AVLNode<T> doubleWithRightChild(AVLNode<T> k1)
	{
		k1.right = rotateWithLeftChild( k1.right );
		return rotateWithRightChild( k1 );
	}    
	/* Functions to count number of nodes */
	public int countNodes()
	{
		return countNodes(root);
	}
	private int countNodes(AVLNode<T> r)
	{
		if (r == null)
			return 0;
		else
		{
			int l = 1;
			l += countNodes(r.left);
			l += countNodes(r.right);
			return l;
		}
	}
	/* Functions to search for an element */
	public boolean search(String val)
	{
		return search(root, val);
	}
	private boolean search(AVLNode<T> r, String val)
	{
		boolean found = false;
		while ((r != null) && !found)
		{
			String rval = r.entry.nameRevised;
			if (compareThat(val,  rval)<0)
				r = r.left;
			else if (compareThat(val, rval)>0)
				r = r.right;
			else
			{
				found = true;
				break;
			}
			found = search(r, val);
		}
		return found;
	}
	String[] toReturn = new String[countNodes()];
	public String[] inorder()
    {
        inorder(root);
        return toReturn;
        
    }
    private String[] inorder(AVLNode<T> r)
    {
    	
    	 if (r != null)
         {
             inorder(r.left);
             System.out.print(r.entry +" ");
             inorder(r.right);
         }
        return toReturn;
    } 
	
	//Code taken from 'http://www.geeksforgeeks.org/avl-tree-set-2-deletion/'
	// Get Balance factor of node N
    int getBalance(AVLNode<T> t)
    {
        if (t == null)
            return 0;
        return height(t.left) - height(t.right);
    
    }
	public AVLNode<T> maxValueNode(AVLNode<T> root)
    {
        AVLNode<T> current = root;

        /* loop down to find the leftmost leaf */
        while (current.right != null) {
           current = current.right;
        }
        return current;
    }
	
	public AVLNode<T> minValueNode(AVLNode<T> root)
    {
        AVLNode<T> current = root;

        /* loop down to find the leftmost leaf */
        while (current.left != null) {
           current = current.left;
        }
        return current;
    }
	
	public AVLNode<T> deleteNode(AVLNode<T> root, String entry)
	{
		if (root == null) {
			return root;
		}
		
		if (compareThat(entry,  root.entry.nameRevised)>0) {
			root.left = deleteNode(root.left, entry);
			
		}else if (compareThat(entry,  root.entry.nameRevised)<0)
			root.right = deleteNode(root.right, entry);

		// if key is same as root's key, then this is the node
		// to be deleted
		else
		{

			// node with only one child or no child
			if ((root.left == null) || (root.right == null))
			{
				AVLNode<T> temp = null;
				if (temp == root.left)
					temp = root.right;
				else
					temp = root.left;

				// No child case
				if (temp == null)
				{
					temp = root;
					root = null;
				}
				else {  // One child case
					root = temp; // Copy the contents of
				// the non-empty child
				}
			}
			else
			{

				// node with two children: Get the inorder
				// successor (smallest in the right subtree)
				AVLNode<T> temp = minValueNode(root.right);

				// Copy the inorder successor's data to this node
				root.entry = temp.entry;

				// Delete the inorder successor
				root.right = deleteNode(root.right, temp.entry.nameRevised);
			}
		}

		// If the tree had only one node then return
		if (root == null)
			return root;

		// STEP 2: UPDATE HEIGHT OF THE CURRENT NODE
		root.height = max(height(root.left), height(root.right)) + 1;

		// STEP 3: GET THE BALANCE FACTOR OF THIS NODE (to check whether
		//  this node became unbalanced)
		int balance = getBalance(root);

		// If this node becomes unbalanced, then there are 4 cases
		// Left Left Case
		if (balance > 1 && getBalance(root.left) >= 0)
			return rotateWithRightChild(root);

		// Left Right Case
		if (balance > 1 && getBalance(root.left) < 0)
		{
			root.left = rotateWithLeftChild(root.left);
			return rotateWithRightChild(root);
		}

		// Right Right Case
		if (balance < -1 && getBalance(root.right) <= 0)
			return rotateWithLeftChild(root);

		// Right Left Case
		if (balance < -1 && getBalance(root.right) > 0)
		{
			root.right = rotateWithRightChild(root.right);
			return rotateWithLeftChild(root);
		}

		return root;
	}

	public int compareThis(Entry obj1, Entry obj2) {

		if(comparator != null) {
			return comparator.compare(obj1, obj2);
		}
		else {
			return obj1.compareTo(obj2);
		}

	}
	
	public int compareThat(String obj1, String obj2) {
		return obj1.compareTo(obj2);
		
	}

}


