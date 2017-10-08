package util.collections.tree;

import util.collections.tree.visitors.TreeNodeVisitor;

public interface VisitableElement<E> {
	
	void accept(TreeNodeVisitor<E> visitor);

}
