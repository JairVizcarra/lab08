package avltree;

import actividades.ItemDuplicated;
import actividades.Node;

public class TestAVL {

	public static void main(String[] args) throws ItemDuplicated {
		AVLTree<Integer> avlTree = new AVLTree<>();
		
		avlTree.insert(10);
		avlTree.toString();
    }

    // Método para imprimir el árbol (preorder traversal con niveles)
    public static void printTree(Node node, String prefix, boolean isTail) {
        if (node != null) {
            System.out.println(prefix + (isTail ? "└── " : "├── ") + node.toString());
            printTree(node.getLeft(), prefix + (isTail ? "    " : "│   "), false);
            printTree(node.getRight(), prefix + (isTail ? "    " : "│   "), true);
        }
	}
}
