import java.util.ArrayList;
import java.util.Iterator;
/**
 * A simple general-tree implementation provided only
 * for constructing and testing the PSS9 examples.
 *
 * This class is not taken from the lecture slides.
 *
 *
 * It supports addRoot() and addChild() so that test trees
 * can be constructed.
 */
public class LinkedTree<E> extends AbstractTree<E> {

    private static class Node<E> implements Position<E> {

        private E element;
        private Node<E> parent;
        private final ArrayList<Node<E>> children;
        private final Object owner;

        public Node(E element, Node<E> parent, Object owner) {
            this.element = element;
            this.parent = parent;
            this.owner = owner;
            children = new ArrayList<>();
        }

        @Override
        public E getElement() {
            return element;
        }

        @Override
        public String toString() {
            return String.valueOf(element);
        }
    }

    private Node<E> root;
    private int size;

    /**
     * Checks that p is a valid position belonging to this tree.
     */
    @SuppressWarnings("unchecked")
    private Node<E> validate(Position<E> p) {
        if (!(p instanceof Node<?>)) {
            throw new IllegalArgumentException("Invalid position");
        }

        Node<E> node = (Node<E>) p;

        if (node.owner != this) {
            throw new IllegalArgumentException(
                    "Position does not belong to this tree");
        }

        return node;
    }

    /**
     * Adds the root to an empty tree.
     */
    public Position<E> addRoot(E element) {
        if (!isEmpty()) {
            throw new IllegalStateException(
                    "The tree already has a root");
        }

        root = new Node<>(element, null, this);
        size = 1;

        return root;
    }

    /**
     * Adds a new last child to parentPosition.
     */
    public Position<E> addChild(
            Position<E> parentPosition,
            E element) {

        Node<E> parent = validate(parentPosition);
        Node<E> child = new Node<>(element, parent, this);

        parent.children.add(child);
        size++;

        return child;
    }

    @Override
    public Position<E> root() {
        return root;
    }

    @Override
    public Position<E> parent(Position<E> p) {
        return validate(p).parent;
    }

    @Override
    public Iterable<Position<E>> children(Position<E> p) {
        Node<E> node = validate(p);

        ArrayList<Position<E>> result = new ArrayList<>();

        for (Node<E> child : node.children) {
            result.add(child);
        }

        return result;
    }

    @Override
    public int numChildren(Position<E> p) {
        return validate(p).children.size();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterable<Position<E>> positions() {
        ArrayList<Position<E>> result = new ArrayList<>();

        if (!isEmpty()) {
            addPreorder(root, result);
        }

        return result;
    }

    private void addPreorder(
            Node<E> node,
            ArrayList<Position<E>> result) {

        result.add(node);

        for (Node<E> child : node.children) {
            addPreorder(child, result);
        }
    }

    @Override
    public Iterator<E> iterator() {
        ArrayList<E> elements = new ArrayList<>();

        for (Position<E> position : positions()) {
            elements.add(position.getElement());
        }

        return elements.iterator();
    }
}