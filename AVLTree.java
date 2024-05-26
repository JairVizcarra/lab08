package avltree;

import actividades.ItemDuplicated;
import actividades.Node;
import treelink.BSTree;

public class AVLTree<E extends Comparable<E>> extends BSTree<E> {
	class NodeAVL extends Node<E> {
		protected int bf;

		public NodeAVL(E data) {
			super(data);
			this.bf = 0;
		}

		public String toString() {
			return super.toString() + "[" + bf + "]";
		}
	}

	private boolean height;

	public AVLTree() {
		super();
		height = false;
	}

	public void insert(E x) throws ItemDuplicated {
		this.height = false;
		root = insert(x, (NodeAVL) this.root);
	}

	protected Node<E> insert(E x, NodeAVL node) throws ItemDuplicated {
		NodeAVL fat = node;
		if (node == null) {
			this.height = true;
			fat = new NodeAVL(x);
		} else {
			int resC = node.getData().compareTo(x);
			if (resC == 0)
				throw new ItemDuplicated(x + " ya se encuentra en el arbol...");
			if (resC < 0) {
				fat.setRight(insert(x, (NodeAVL) node.getRight()));
				if (this.height) {
					switch (fat.bf) {
					case -1:
						fat.bf = 0;
						this.height = false;
						break;
					case 0:
						fat.bf = 1;
						this.height = true;
						break;
					case 1: //bf = 2
						fat = balanceToLeft(fat);
						this.height = false;
						break;
					}
				}
			} 
			else {
				fat.setLeft(insert(x, (NodeAVL) node.getLeft()));
				if(this.height) {
					switch(fat.bf) {
					case -1:
						fat.bf = 0;
						this.height = false;
						break;
					case 0:
						fat.bf = -1;
						this.height = true;
						break;
					case 1:
						fat = balanceToRight(fat);
						this.height = false;
						break;
					}
				}
			}
		}
		return fat;
	}

	private NodeAVL balanceToLeft(NodeAVL node) {
		NodeAVL hijo = (NodeAVL) node.getRight();
		switch (hijo.bf) {
		case 1:
			node.bf = 0;
			hijo.bf = 0;
			node = rotateSL(node);
			break;
		case -1:
			NodeAVL nieto = (NodeAVL) hijo.getLeft();
			switch (nieto.bf) {
			case -1:
				node.bf = 0;
				hijo.bf = 1;
				break;
			case 0:
				node.bf = 0;
				hijo.bf = 0;
				break;
			case 1:
				node.bf = 1;
				hijo.bf = 0;
				break;
			}
			nieto.bf = 0;
			node.setRight(rotateSR(hijo));;
			node = rotateSL(node);
		}
		return node;
	}
	
	private NodeAVL balanceToRight(NodeAVL node) {
        NodeAVL child = (NodeAVL) node.getLeft();
        switch (child.bf) {
            case -1:
                node.bf = 0;
                child.bf = 0;
                node = rotateSR(node);
                break;
            case 1:
                NodeAVL grandChild = (NodeAVL) child.getRight();
                switch (grandChild.bf) {
                    case 1:
                        node.bf = 0;
                        child.bf = -1;
                        break;
                    case 0:
                        node.bf = 0;
                        child.bf = 0;
                        break;
                    case -1:
                        node.bf = -1;
                        child.bf = 0;
                        break;
                }
                grandChild.bf = 0;
                node.setLeft(rotateSL(child));
                node = rotateSR(node);
                break;
        }
        return node;
    }

	private NodeAVL rotateSL(NodeAVL node) {   
		NodeAVL p = (NodeAVL) node.getRight();
		node.setRight(p.getLeft());
		p.setLeft(node);
		node = p;
		return node;
	}
	
	private NodeAVL rotateSR(NodeAVL node) {
        NodeAVL p = (NodeAVL) node.getLeft();
        node.setLeft(p.getRight());
        p.setRight(node);
        return p;
    }
}
