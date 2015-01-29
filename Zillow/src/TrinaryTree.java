public class TrinaryTree<K extends Comparable<K>> {

	public static class TrinaryNode<K extends Comparable<K>> {
		TrinaryNode<K> left;
		TrinaryNode<K> mid;
		TrinaryNode<K> right;
		K val;

		public TrinaryNode(K v) {
			val = v;
		}

		@Override
		public String toString() {
			return val.toString();
		}
	}

	TrinaryNode<K> mRoot;

	public TrinaryTree() {
	}

	public TrinaryTree(K[] values) {
		insert(values, 0, values.length - 1);
	}

	public TrinaryTree(TrinaryNode<K> root) {
		mRoot = root;
	}

	public TrinaryNode<K> getRoot() {
		return mRoot;
	}

	private void insert(K[] values, int start, int end) {
		if (start > end) {
			return;
		}

		int mid = (start + end) / 2;
		insert(values[mid]);
		insert(values, start, mid - 1);
		insert(values, mid + 1, end);
	}

	public void insert(K val) {
		if (mRoot == null) {
			mRoot = new TrinaryNode<K>(val);
			return;
		}

		insert(mRoot, val);
	}

	public TrinaryNode<K> insert(TrinaryNode<K> node, K val) {
		if (node == null) {
			return new TrinaryNode<K>(val);
		}

		if (node.val.compareTo(val) > 0) {
			node.left = insert(node.left, val);
		} else if (node.val.compareTo(val) == 0) {
			node.mid = insert(node.mid, val);
		} else {
			node.right = insert(node.right, val);
		}

		return node;
	}

	public void delete(K val) {
		mRoot = delete(mRoot, val);
	}

	public TrinaryNode<K> delete(TrinaryNode<K> node, K val) {
		if (node == null) {
			return null;
		}

		if (node.val.compareTo(val) < 0) {
			node.right = delete(node.right, val);
		} else if (node.val.compareTo(val) > 0) {
			node.left = delete(node.left, val);
		}
		// found val, but node has mid branch
		else if (node.mid != null) {
			node.mid = delete(node.mid, val);
		}
		// leaf node
		else if (node.left == null && node.right == null) {
			return null;
		} else if (node.left != null) {
			TrinaryNode<K> max = findMax(node.left);
			node.mid = max.mid;
			node.val = max.val;
			node.left = removeMax(node.left);
		} else if (node.right != null) {
			TrinaryNode<K> min = findMin(node.right);
			node.mid = min.mid;
			node.val = min.val;
			node.right = removeMin(node.right);
		}
		return node;
	}

	public TrinaryNode<K> find(K val) {
		return find(mRoot, val);
	}

	public TrinaryNode<K> find(TrinaryNode<K> node, K val) {
		if (node == null) {
			return null;
		}

		if (node.val.compareTo(val) > 0) {
			return find(node.left, val);
		} else if (node.val.compareTo(val) == 0) {
			return node;
		} else {
			return find(node.right, val);
		}
	}

	public TrinaryNode<K> findMax(TrinaryNode<K> node) {
		TrinaryNode<K> tempNode = node;

		if (node == null) {
			return null;
		}

		while (tempNode.right != null) {
			tempNode = tempNode.right;
		}

		return tempNode;
	}

	public TrinaryNode<K> removeMax(TrinaryNode<K> node) {
		if (node == null) {
			return null;
		} else if (node.right != null) {
			node.right = removeMax(node.right);
			return node;
		} else {
			return node.left;
		}
	}

	public TrinaryNode<K> removeMin(TrinaryNode<K> node) {
		if (node == null) {
			return null;
		} else if (node.left != null) {
			node.left = removeMax(node.left);
			return node;
		} else {
			return node.right;
		}
	}

	public TrinaryNode<K> findMin(TrinaryNode<K> node) {
		TrinaryNode<K> tempNode = node;

		if (node == null) {
			return null;
		}

		while (tempNode.left != null) {
			tempNode = tempNode.left;
		}

		return tempNode;
	}

	public static void TestTrinaryTree() {
		TestConstruction();
		TestInsert();
		TestDeleteRoot();
		TestDeleteRootWithChildren();
		TestDeleteRepeatedValues();
		TestDeleteLeafNode();
		TestDeleteWithLeftSubtreeMax();
		TestDeleteWithRightSubtreeMin();
		TestDeleteWithRightSubtreeMinWithMidVal();
	}

	public static void TestConstruction() {
		TrinaryTree<Integer> tree = new TrinaryTree<Integer>(new Integer[] { 1,
				2, 3, 4, 5, 6, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18,
				19, 20 });
		assert (tree.toString()
				.equals("1 2 3 4 5 6 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 "));
	}

	public static void TestInsert() {
		TrinaryTree<Integer> tree = new TrinaryTree<Integer>(
				new Integer[] { 1, 2, 3, 4, 5, 7, 8, 9, 10, 11, 12, 13, 14, 15,
						16, 17, 18, 19, 20 });
		tree.insert(6);
		assert (tree.toString()
				.equals("1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 "));
	}

	public static void TestInsertMid() {
		TrinaryTree<Integer> tree = new TrinaryTree<Integer>(new Integer[] { 1,
				2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
				20 });
		tree.insert(7);
		assert (tree.toString()
				.equals("1 2 3 4 5 6 7 7 8 9 10 11 12 13 14 15 16 17 18 19 20 "));
	}

	/*
	 * Tree 6
	 */

	public static void TestDeleteRoot() {
		TrinaryTree<Integer> tree = new TrinaryTree<Integer>();
		tree.insert(6);
		assert (tree.toString().equals("6 "));
	}

	/*
	 * Original Tree 2 / \ 1 3
	 * 
	 * Expected Result
	 * 
	 * 1 \ 3
	 */
	public static void TestDeleteRootWithChildren() {
		TrinaryTree<Integer> tree = new TrinaryTree<Integer>(new Integer[] { 1,
				2, 3 });
		tree.delete(2);
		assert (tree.toString().equals("1 3 "));
		assert (tree.getRoot().val == 1);
		assert (tree.getRoot().right.val == 3);
	}

	public static void TestDeleteRepeatedValues() {
		TrinaryTree<Integer> tree = new TrinaryTree<Integer>(new Integer[] { 1,
				2, 3, 3, 3 });
		tree.delete(3);
		assert (tree.find(3) != null);
		tree.delete(3);
		assert (tree.find(3) != null);
		tree.delete(3);
		assert (tree.find(3) == null);
	}

	public static void TestDeleteLeafNode() {

	}

	/*
	 * Original Tree: 5 \ 7 / \ 6 9 / \ 8 10
	 * 
	 * Original Tree: 5 \ 6 \ 9 / \ 8 10
	 */
	public static void TestDeleteWithLeftSubtreeMax() {
		TrinaryTree<Integer> tree = new TrinaryTree<Integer>();
		tree.insert(5);
		tree.insert(7);
		tree.insert(9);
		tree.insert(6);
		tree.insert(8);
		tree.insert(10);
		tree.delete(7);
		assert (tree.find(7) == null);
		assert (tree.toString().equals("5 6 8 9 10 "));
		assert (tree.getRoot().right.val == 6);
		assert (tree.getRoot().right.right.val == 9);
	}

	/*
	 * Original Tree: 5 \ 7 \ 9 / \ 8 10
	 * 
	 * Original Tree: 5 \ 8 \ 9 \ 10
	 */

	public static void TestDeleteWithRightSubtreeMin() {
		TrinaryTree<Integer> tree = new TrinaryTree<Integer>();
		tree.insert(5);
		tree.insert(7);
		tree.insert(9);
		tree.insert(8);
		tree.insert(10);
		tree.delete(7);
		assert (tree.toString().equals("5 8 9 10 "));
		assert (tree.getRoot().right.val == 8);
		assert (tree.getRoot().right.left == null);
		assert (tree.getRoot().right.right.val == 9);
	}

	public static void TestDeleteWithRightSubtreeMinWithMidVal() {
		TrinaryTree<Integer> tree = new TrinaryTree<Integer>();
		tree.insert(5);
		tree.insert(7);
		tree.insert(9);
		tree.insert(8);
		tree.insert(8);
		tree.insert(8);
		tree.insert(10);
		tree.delete(7);
		assert (tree.toString().equals("5 8 8 8 9 10 "));
		assert (tree.getRoot().right.val == 8);
		assert (tree.getRoot().right.mid.mid.val == 8);
		assert (tree.getRoot().right.left == null);
		assert (tree.getRoot().right.right.val == 9);
	}

	private void inOrderPrint(TrinaryNode<K> node, StringBuilder builder) {
		if (node != null) {
			inOrderPrint(node.left, builder);
			builder.append(node.val).append(" ");
			inOrderPrint(node.mid, builder);
			inOrderPrint(node.right, builder);
		}
	}

	@Override
	public String toString() {

		StringBuilder builder = new StringBuilder();
		inOrderPrint(mRoot, builder);
		return builder.toString();
	}
	
	public static void main(String[] args) {
		TrinaryTree.TestTrinaryTree();
	}
}