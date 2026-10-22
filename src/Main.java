import java.util.ArrayList;

/**
 * Tests only the programming problems from PSS9:
 *
 * Problem 2: Separating internal and external positions
 * Problem 3: Counting positions at a given depth
 * Problem 4: Counting the deepest external positions
 */
public class Main {
    public static void main(String[] args) {

        /*
         * Construct the tree from Problem 1:
         *
         *                    A
         *              /     |     \
         *             B      C      D
         *           /  \     |      |
         *          E    F    G      J
         *                   / \      |
         *                  H   I     K
         */

        LinkedTree<String> tree = new LinkedTree<>();

        Position<String> a = tree.addRoot("A");

        Position<String> b = tree.addChild(a, "B");
        Position<String> c = tree.addChild(a, "C");
        Position<String> d = tree.addChild(a, "D");

        Position<String> e = tree.addChild(b, "E");
        Position<String> f = tree.addChild(b, "F");

        Position<String> g = tree.addChild(c, "G");
        Position<String> h = tree.addChild(g, "H");
        Position<String> i = tree.addChild(g, "I");

        Position<String> j = tree.addChild(d, "J");
        Position<String> k = tree.addChild(j, "K");


        /*
         * Problem 2 tests
         */
        ArrayList<Position<String>>[] separated = PSS9Solutions.separatePositions(tree);

        ArrayList<Position<String>> expectedInternal = new ArrayList<>();

        expectedInternal.add(a);
        expectedInternal.add(b);
        expectedInternal.add(c);
        expectedInternal.add(d);
        expectedInternal.add(g);
        expectedInternal.add(j);

        ArrayList<Position<String>> expectedExternal = new ArrayList<>();

        expectedExternal.add(e);
        expectedExternal.add(f);
        expectedExternal.add(h);
        expectedExternal.add(i);
        expectedExternal.add(k);

        check(
                separated != null,
                "Problem 2: The returned array is null"
        );

        check(
                separated.length == 2,
                "Problem 2: The returned array must have length 2"
        );

        checkSamePositions(separated[0], expectedInternal,
                "Problem 2: Incorrect internal positions"
        );

        checkSamePositions(separated[1], expectedExternal,
                "Problem 2: Incorrect external positions"
        );

        /*
         * Test Problem 2 on an empty tree.
         */
        LinkedTree<String> emptyTree = new LinkedTree<>();

        ArrayList<Position<String>>[] emptyResult = PSS9Solutions.separatePositions(emptyTree);

        check(
                emptyResult != null && emptyResult.length == 2,
                "Problem 2: Incorrect result for an empty tree"
        );

        check(
                emptyResult[0] != null && emptyResult[0].isEmpty(),
                "Problem 2: Internal list should be empty"
        );

        check(
                emptyResult[1] != null && emptyResult[1].isEmpty(),
                "Problem 2: External list should be empty"
        );

        System.out.println("Problem 2 tests passed.");


        /*
         * Problem 3 tests
         */
        check(
                PSS9Solutions.countAtDepth(tree, 0) == 1,
                "Problem 3: Depth 0 should contain 1 position"
        );

        check(
                PSS9Solutions.countAtDepth(tree, 1) == 3,
                "Problem 3: Depth 1 should contain 3 positions"
        );

        check(
                PSS9Solutions.countAtDepth(tree, 2) == 4,
                "Problem 3: Depth 2 should contain 4 positions"
        );

        check(
                PSS9Solutions.countAtDepth(tree, 3) == 3,
                "Problem 3: Depth 3 should contain 3 positions"
        );

        check(
                PSS9Solutions.countAtDepth(tree, 4) == 0,
                "Problem 3: Depth 4 should contain 0 positions"
        );

        check(
                PSS9Solutions.countAtDepth(emptyTree, 0) == 0,
                "Problem 3: An empty tree should return 0"
        );

        System.out.println("Problem 3 tests passed.");


        /*
         * Problem 4 tests
         */
        check(
                PSS9Solutions.countDeepestExternal(tree, a) == 3,
                "Problem 4: A should return 3 for H, I, and K"
        );

        check(
                PSS9Solutions.countDeepestExternal(tree, b) == 2,
                "Problem 4: B should return 2 for E and F"
        );

        check(
                PSS9Solutions.countDeepestExternal(tree, c) == 2,
                "Problem 4: C should return 2 for H and I"
        );

        check(
                PSS9Solutions.countDeepestExternal(tree, d) == 1,
                "Problem 4: D should return 1 for K"
        );

        check(
                PSS9Solutions.countDeepestExternal(tree, g) == 2,
                "Problem 4: G should return 2 for H and I"
        );

        check(
                PSS9Solutions.countDeepestExternal(tree, e) == 1,
                "Problem 4: A leaf should return 1"
        );

        System.out.println("Problem 4 tests passed.");

        System.out.println();
        System.out.println("All programming-problem tests passed.");
    }

    /**
     * Checks that two lists contain exactly the same positions.
     * The order of the positions does not matter.
     */
    private static <E> void checkSamePositions(
            ArrayList<Position<E>> actual,
            ArrayList<Position<E>> expected,
            String message) {

        if (actual == null
                || actual.size() != expected.size()
                || !actual.containsAll(expected)
                || !expected.containsAll(actual)) {

            throw new AssertionError(message);
        }
    }

    /**
     * Stops the program and displays the given message
     * when a test fails.
     */
    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}