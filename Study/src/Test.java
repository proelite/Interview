import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;

public class Test {

	public static class TreeNode {
		TreeNode left;
		TreeNode right;
		TreeNode next;
		String val;

		public TreeNode(String v) {
			val = v;
		}

		@Override
		public String toString() {
			return val;
		}
	}

	public static class Node {
		Node next;
		String val;

		public Node(String v) {
			val = v;
		}

		@Override
		public String toString() {
			return val;
		}
	}

	public static String currentMinWindow = "";
	public static HashSet<Character> tMap = new HashSet<Character>();

	public static void main(String[] args) {
		LongParser.testStringToLong();
		TrinaryTree.TestTrinaryTree();
	}

	public static void reverseNodesKGroup() {
		Node A = new Node("A");
		Node B = new Node("B");
		Node C = new Node("C");
		Node D = new Node("D");
		Node E = new Node("E");
		Node F = new Node("F");
		Node G = new Node("G");
		A.next = B;
		B.next = C;
		C.next = D;
		D.next = E;
		E.next = F;
		F.next = G;

		recursivelyReverseNodesKGroup(A, 2);

		System.out.println();
	}

	
	
	
	public static void recursivelyReverseNodesKGroup(Node node, int k) {
		
		if (k < 2)
		{
			return;
		}
		
		Node nextNode = iterativelyReverseLinkedList(node, k);
  		
	}

	public static Node iterativelyReverseLinkedList(Node node, int k) {
		Node previousNode = null;

		if (node == null || node.next == null) {
			return node;
		}

		Node currentNode = node;
		Node nextNode = null;

		int counter = 0;

		while (counter < k) {
			nextNode = currentNode.next;
			
			currentNode.next = previousNode;

			previousNode = currentNode;
			currentNode = nextNode;
			counter++;
			

		}

		return nextNode;
	}

	public static void reverseLinkedList(Node node) {
		Node tail = recursivelyReverseLinkedList(node);
		if (tail != null) {
			tail.next = null;
		}
	}

	public static Node recursivelyReverseLinkedList(Node node) {
		if (node == null) {
			return null;
		}

		if (node.next != null) {
			Node returnNode = recursivelyReverseLinkedList(node.next);
			returnNode.next = node;
		}

		return node;
	}

	public static void mostFrequentSubstring() {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(System.in));
		try {

			HashMap<String, Integer> substringMap = new HashMap<String, Integer>();

			int n = Integer.parseInt(bufferedReader.readLine());
			String klm = bufferedReader.readLine();
			String[] klms = klm.split(" ");

			int k = Integer.parseInt(klms[0]);
			int l = Integer.parseInt(klms[1]);
			int m = Integer.parseInt(klms[2]);

			String string = bufferedReader.readLine();

			if (n == string.length()) {
				for (int i = 0; i < string.length(); i++) {
					for (int j = k; j <= l; j++) {
						if (i + j < string.length()) {
							String substring = string.substring(i, i + j);
							if (substringMap.containsKey(substring)) {
								int occurence = substringMap.get(substring);
								substringMap.put(substring, occurence + 1);
							} else {
								if (countUniqueCharacters(substring) <= m) {
									substringMap.put(substring, 1);
								}
							}
						}
					}
				}
			}

			Iterator<Entry<String, Integer>> it = substringMap.entrySet()
					.iterator();
			int maxOccurence = 0;

			while (it.hasNext()) {
				maxOccurence = Math.max(maxOccurence, it.next().getValue());
			}

			System.out.println(maxOccurence);

		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
	}

	public static int countUniqueCharacters(String input) {
		boolean[] isItThere = new boolean[26];
		for (int i = 0; i < input.length(); i++) {
			isItThere[input.charAt(i) - 'a'] = true;
		}

		int count = 0;
		for (int i = 0; i < isItThere.length; i++) {
			if (isItThere[i] == true) {
				count++;
			}
		}

		return count;
	}

	public static String minWindow(String S, String T) {
		// for (int i = 0; i < T.length(); i++)
		// {
		// tMap.add(T.charAt(i));
		// }
		// int beginIndex = 0;
		// int counter = 0;
		//
		// boolean checkedToggleBoolean = true;
		//
		// for (int i = 0; i < S.length(); i++)
		// {
		// char tempC = S.charAt(i);
		//
		// if (tMap.containsKey(tempC) && tMap.get(tempC) !=
		// checkedToggleBoolean)
		// {
		// counter++;
		//
		// if (counter == tMap.size())
		// {
		// String minWindow = S.substring(beginIndex, i);
		//
		// if (minWindow.length() < currentMinWindow.length())
		// {
		// currentMinWindow = minWindow;
		// }
		// else
		// {
		// checkedToggleBoolean = !checkedToggleBoolean;
		// counter = 0;
		// }
		// }
		// else if (counter == 1)
		// {
		// beginIndex = i;
		// }
		//
		// tMap.put(tempC, checkedToggleBoolean);
		// }
		// }
		//
		// return currentMinWindow;
		return null;
	}

	public static void findCombinations(String s, Set<String> combinations) {
		if (!combinations.contains(s)) {
			combinations.add(s);

			for (int i = 0; i < s.length(); i++) {
				StringBuilder sb = new StringBuilder(s);
				findCombinations(sb.deleteCharAt(i).toString(), combinations);
			}
		}
	}

	public static void linkLevelNodes(TreeNode node) {
		if (node == null) {
			return;
		}

		Stack<TreeNode> stack = new Stack<TreeNode>();
		stack.push(node);

		while (true) {
			List<TreeNode> list = new ArrayList<TreeNode>();

			while (!stack.empty()) {
				list.add(stack.pop());
			}

			linkNodes(list);

			for (int i = list.size() - 1; i >= 0; i--) {
				TreeNode listNode = list.get(i);
				if (listNode.right != null) {
					stack.push(listNode.right);
				}
				if (listNode.left != null) {
					stack.push(listNode.left);
				}
			}

			if (stack.isEmpty()) {
				break;
			}
		}
	}

	public static void linkNodes(List<TreeNode> list) {
		if (list.size() < 1) {
			return;
		}

		for (int i = 1; i < list.size(); i++) {
			if (i == list.size()) {
				list.get(i - 1).next = null;
			} else {
				list.get(i - 1).next = list.get(i);
			}
		}
	}

}
