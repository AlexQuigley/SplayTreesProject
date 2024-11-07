import java.util.*;
public class Homework5_SplayTree {
	public static void main(String[] args) {
		int key;
		Scanner input = new Scanner(System.in);
		SplayTree tree = new SplayTree();
		
		while (true) {
		    System.out.print("Enter a key (-1 to exit): ");
		    key = input.nextInt();
		    if (key < 0) break;
		    // 5, 9, 13, 11, 1
		    tree.insert(key, key);
		    //tree.printTree(tree.root, "", false);
		}
		
		//tree.printTree(tree.root, "", false);
	}
}

class SplayTree <Key extends Comparable<Key>, Value> {
    
    private class TreeNode {
        Key key;
        Value value;
        TreeNode left, right;
        
        TreeNode(Key key, Value value) {
            this.key = key;
            this.value = value;
        }
    }
    
    TreeNode root;
    
    public SplayTree() {
        root = null;
    }
    
    // 5, 9, 13, 11, 1
    public void insert(Key key, Value value) {
        if (root == null) {
            // Case when tree is empty, directly insert the root node
            root = new TreeNode(key, value);
            //printTree();
            return;
        }
    
        TreeNode current = root;
        TreeNode parent = null;
    
        // Traverse the tree to find the appropriate location to insert the new node
        // This replaces the initial splay operation in the original code
        while (current != null) {
            parent = current;
            if (key.compareTo(current.key) < 0) {
                current = current.left;
            } else if (key.compareTo(current.key) > 0) {
                current = current.right;
            } else {
                // If key already exists, update its value
                current.value = value;
                // Splay the node with the existing key to the root
                root = splay(root, key);
                //printTree();
                return;
            }
        }
    
        // Create the new node and insert it as a child of the identified parent node
        TreeNode newNode = new TreeNode(key, value);
        if (key.compareTo(parent.key) < 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
    
        // Splay the newly inserted node to bring it to the root
        root = splay(root, key);
    
        System.out.printf("Inserting Key: %d\n", value);
        printTree();
    }
    
    private TreeNode splay(TreeNode node, Key key) {
        if (node == null || node.key.compareTo(key) == 0) return node;
    
        // Key lies in the left subtree
        if (key.compareTo(node.key) < 0) {
            if (node.left == null) return node;  // Key not found, return the current node
            
            // Zig-Zig (left left)
            if (node.left.left != null && key.compareTo(node.left.key) < 0) {
                node.left.left = splay(node.left.left, key);
                node = rotateRight(node);
            }
            // Zig-Zag (left right)
            else if (node.left.right != null && key.compareTo(node.left.key) > 0) {
                node.left.right = splay(node.left.right, key);
                if (node.left.right != null)
                    node.left = rotateLeft(node.left);
            }
    
            // Perform a single right rotation if needed
            return (node.left == null) ? node : rotateRight(node);
        }
    
        // Key lies in the right subtree
        else {
            if (node.right == null) return node;  // Key not found, return the current node
    
            // Zig-Zig (right right)
            if (node.right.right != null && key.compareTo(node.right.key) > 0) {
                node.right.right = splay(node.right.right, key);
                node = rotateLeft(node);
            }
            // Zig-Zag (right left)
            else if (node.right.left != null && key.compareTo(node.right.key) < 0) {
                node.right.left = splay(node.right.left, key);
                if (node.right.left != null)
                    node.right = rotateRight(node.right);
            }
    
            // Perform a single left rotation if needed
            return (node.right == null) ? node : rotateLeft(node);
        }
    }
    
    private TreeNode rotateRight(TreeNode node) {
        TreeNode temp = node.left;
        node.left = temp.right;
        temp.right = node;
        return temp;
    }
    
    private TreeNode rotateLeft(TreeNode node) {
        TreeNode temp = node.right;
        node.right = temp.left;
        temp.left = node;
        return temp;
    }
    
    public void printTree() {
        printTree(root, "", true);
    }

    private void printTree(TreeNode node, String prefix, boolean isLeft) {
        if (node != null) {
            System.out.printf("%s%s%d\n", prefix, (isLeft ? "L── " : "R── "), node.key);
            printTree(node.left, prefix + (isLeft ? "|   " : "    "), true);
            printTree(node.right, prefix + (isLeft ? "|   " : "    "), false);
        }
    }
}
