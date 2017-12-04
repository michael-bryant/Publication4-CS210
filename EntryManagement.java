import java.util.Comparator;
import java.util.NoSuchElementException;

public class EntryManagement {

	AVLTree<Entry> entryAVLTree;
	
	public EntryManagement() {
		// TODO Auto-generated constructor stub
		entryAVLTree = new AVLTree<Entry>();
	}
	
	public EntryManagement(Comparator<Entry> c) {
		entryAVLTree = new AVLTree<Entry>(c);
	}

	public void add(Entry x) {
		entryAVLTree.insert(x);
	}


	public AVLNode<Entry> entryDeleter(String entry) {
		return entryAVLTree.deleteNode(entryAVLTree.root, entry);
	}
	
	
	public boolean search(String entry) {
		
		return entryAVLTree.search(entry);
		
	}
	
	public String[] toWrite()
	{
		return entryAVLTree.inorder();
		
	}

	public void print()
	{
		
		entryAVLTree.inorder();
		
	}
	
}