package datastructure;

public class Dictionary {	
	private Node root = null;

	public Node getNode(int key) {
		Node node = root;
		while(node != null) {
			if(node.key == key) return node;
			if(key < node.key) node = node.left;
			else node = node.right;
		}
		return null;
	}
	
	public void insertNode(int key, int value) {
		if(root == null) root = new Node(key, value);
		else {
			Node node = root;
			while(node != null) {
				if(key == node.key) throw new AssertionError("Cannot insert key because it already exists.");
				else if(key < node.key) {
					if(node.left == null) {
						node.left = new Node(key, value);
						node.left.parent = node;
						node.balance -= 1;
						if(node.balance == -1 && node != root) { // tree size increased
							upin(node);
						}
						break;
					}
					else node = node.left;
				}
				else {
					if(node.right == null) {
						node.right = new Node(key, value);
						node.right.parent = node;
						node.balance += 1;
						if(node.balance == 1 && node != root) { // tree size increased
							upin(node);
						}
						break;
					}
					else node = node.right;
				}
			}		
		}
	}
	
	public void removeNode(int key) {
		//TODO
	}
	
	
	private void upin(Node node) {
		Node p = node;
		Node q = node.parent;
		// update balances
		if(q.left == p) q.balance--; // size of left tree increased
		else q.balance++; // size of right tree increased
		// check AVL condition
		if(Math.abs(q.balance) == 1 && q != root) upin(q);
		else if(Math.abs(q.balance) == 2) repair(p);
	}
	
	private void repair(Node node) {
		Node p = node;
		Node q = node.parent;
		if(q.left == p) { // size of left tree increased
			if(p.balance == -1) { // left tree of left tree increased: right rotation
				Node b = p.right;
				// change root of tree to q
				if(q == root) {
					p.parent = null;
					root = p;
				}
				else {
					p.parent = q.parent;
					if(q.parent.left == q) q.parent.left = p;
					else q.parent.right = p;
				}
				// change b
				if(b != null) b.parent = q;
				q.left = b;
				// change q
				q.parent = p;
				p.right = q;
				// change balance
				p.balance = 0;
				q.balance = 0;
			}
			else if(p.balance == 1) { // right tree of left tree increased: double right rotation
				Node r = p.right;
				Node b1 = r.left;
				Node b2 = r.right;
				
				// change root of tree to r
				if(q == root) {
					r.parent = null;
					root = r;
				}
				else {
					r.parent = q.parent;
					if(q.parent.left == q) q.parent.left = r;
					else q.parent.right = r;
				}
				// change b1
				if(b1 != null) b1.parent = p;
				p.right = b1;
				// change b2
				if(b2 != null) b2.parent = q;
				q.left = b2;
				// change p
				p.parent = r;
				r.left = p;
				// change q
				q.parent = r;
				r.right = q;
				// change balance
				p.balance = r.balance == 1 ? -1 : 0;
				q.balance = r.balance == -1 ? 1 : 0;
				r.balance = 0;
			}
		}
		else {
			if(p.balance == 1) { // right tree of right tree increased: left rotation
				Node b = p.left;
				// change root of tree to q
				if(q == root) {
					p.parent = null;
					root = p;
				}
				else {
					p.parent = q.parent;
					if(q.parent.left == q) q.parent.left = p;
					else q.parent.right = p;
				}
				// change b
				if(b != null) b.parent = q;
				q.right = b;
				// change q
				q.parent = p;
				p.left = q;
				// change balance
				p.balance = 0;
				q.balance = 0;
			}
			else if(p.balance == -1) { // left tree of right tree increased: double left rotation
				Node r = p.left;
				Node b1 = r.right; // larger subtree
				Node b2 = r.left; // smaller subtree
				
				// change root of tree to r
				if(q == root) {
					r.parent = null;
					root = r;
				}
				else {
					r.parent = q.parent;
					if(q.parent.left == q) q.parent.left = r;
					else q.parent.right = r;
				}
				// change b1
				if(b1 != null) b1.parent = p;
				p.left = b1;
				// change b2
				if(b2 != null) b2.parent = q;
				q.right = b2;
				// change p
				p.parent = r;
				r.right = p;
				// change q
				q.parent = r;
				r.left = q;
				// change balance
				p.balance = r.balance == 1 ? 1 : 0;
				q.balance = r.balance == -1 ? -1 : 0;
				r.balance = 0;
			}
		}
	}
	
	public int computeHeight() {
		int height = 0;
		Node node = root;
		while(node != null) {
			height++;
			if(node.balance == 1) node = node.right;
			else node = node.left;
		}
		return height;
	}
	
	public void printDict() {
		int height = computeHeight();
		System.out.println("Height: " + height);
		System.out.println("AVL-Tree:");
		printTree(new Node[] {root}, height);
		System.out.println();
	}
	
	private void printTree(Node[] nodes, int height) {
		String offset1 = "";
		for(int i = 0; i < Math.pow(2, height); i++) {
			if(i == 0) offset1 += " ";
			else offset1 += "  ";
		}
		
		Node[] nextNodes = new Node[nodes.length * 2];
		boolean hasNext = false;
		System.out.print(offset1);
		for(int i = 0; i < nodes.length; i++) {
			if(i > 0) System.out.print(offset1 + (i % 2 == 0 ? "|" : " ") + offset1);
			if(nodes[i] != null) {
				System.out.print(nodes[i].key);
				if(nodes[i].left != null || nodes[i].right != null) {
					hasNext = true;
					nextNodes[2*i] = nodes[i].left;
					nextNodes[2*i + 1] = nodes[i].right;
				}
			}
			else System.out.print("_");
		}
		System.out.println();
		if(hasNext) printTree(nextNodes, height - 1);
	}

	public static class Node {
		int key = 0;
		int value = 0;
		int balance = 0;
		Node left, right, parent;
		Node(int type, int value) {
			this.key = type;
			this.value = value;
		}
	}
}
