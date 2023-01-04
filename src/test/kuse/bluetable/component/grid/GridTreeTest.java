package kuse.bluetable.component.grid;

import org.junit.jupiter.api.Test;

class GridTreeTest {

    public static int bound = 301;
    public static int amount = 8_000;

    @Test
    void testOutOfSequence() {
        GridTree.populateTree(bound, amount);
    }

    @Test
    void testSpeedAdd() {
        GridTree.testSpeedAdd(amount, bound);
    }

    @Test
    void testSpeedGet() {
        GridTree.testSpeedGet(amount, bound);
    }

    @Test
    void testSpeedRemove() {
        GridTree.testSpeedRemove(amount, bound);
    }
}