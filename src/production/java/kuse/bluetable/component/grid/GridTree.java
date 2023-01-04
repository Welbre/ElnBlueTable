package kuse.bluetable.component.grid;

import com.sun.webkit.dom.CSSRuleImpl;
import kuse.bluetable.component.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

class Node<T> {
    public final int value;
    private final ArrayList<T> lift = new ArrayList<>();

    public Node(int value) {
        this.value = value;
    }

    public void add(T var){
        lift.add(var);
    }

    public void add(int index, T var){
        lift.add(index, var);
    }

    public T get(int index){
        if (lift.isEmpty() || index > lift.size() - 1)
            return null;

        return lift.get(index);
    }

    public Iterator<T> iterator(){
        return lift.iterator();
    }

    public int getMidIndex(){
        return lift.size() / 2;
    }

    public T getMid(){
        if (lift.isEmpty())
            return null;

        return lift.get(getMidIndex());
    }

    public int size(){
        return lift.size();
    }

    public T remove(int index){
        return lift.remove(index);
    }

    @Override
    public String toString() {
        return String.format("Node: {value: %d | lifts: %s }", value, lift);
    }
}

public class GridTree{

    public final Node<Node<Node<GridPin>>> root = new Node<>(-1);

    public void add(GridPin pin){
        int xPos = findPinPosByValue(root, pin.x);

        Node<Node<GridPin>> xNode = root.get(xPos);
        if (xNode == null || xNode.value != pin.x) {
            xNode = new Node<>(pin.x);
            root.add(xPos, xNode);
        }

        int yPos = findPinPosByValue(xNode, pin.y);

        Node<GridPin> yNode = xNode.get(yPos);
        if (yNode == null || yNode.value != pin.y){
            yNode = new Node<>(pin.y);
            xNode.add(yPos, yNode);
        }

        yNode.add(pin);
    }

    public GridPin get(int x, int y){
        Node<Node<GridPin>> xNode = root.get(findPinPosByValue(root, x));
        if (xNode != null && xNode.value == x) {
            Node<GridPin> yNode = xNode.get(findPinPosByValue(xNode, y));

            if (yNode != null && yNode.value == y)
                yNode.get(0);
        }

        return null;
    }

    public void remove(int x, int y){
        int xPos = findPinPosByValue(root, x);
        Node<Node<GridPin>> xNode = root.get(xPos);

        if (xNode != null && xNode.value == x) {
            Node<GridPin> yNode = xNode.get(findPinPosByValue(xNode, y));

            if (yNode != null && yNode.value == y){
                if (xNode.size() == 1)
                    root.remove(xPos);
                else
                    yNode.remove(0);
            }
        }
    }

    private <T extends Node<?>> int findPinPosByValue(Node<T> node, int value){
        Node<?> mid = node.getMid();

        if (mid == null)
            return 0;

        int index = node.getMidIndex();

        if (mid.value == value){
            return index;
        } else {
            int walker = value > mid.value ? 1 : -1;

            Node<?> next;
            index += walker;

            while (index < node.size() && index >= 0) {
                next = node.get(index);

                if (next.value == value) {
                    return index;
                } else {
                    if (walker > 0) {
                        if (next.value > value)
                            return index;
                    } else {
                        if (next.value < value)
                            return index + 1;
                    }
                }

                index += walker;
            }
            return walker > 0 ? node.size() : 0;
        }
    }

    public void print(){
        int space = 0;
        System.out.println("root: ");
        for (Iterator<Node<Node<GridPin>>> it = root.iterator(); it.hasNext(); ) {
            Node<Node<GridPin>> node = it.next();

            space++;
            System.out.println("-".repeat(space+1) + "> " + node.value);
            space++;

            for (Iterator<Node<GridPin>> it1 = node.iterator(); it1.hasNext(); ) {

                Node<GridPin> pinNode = it1.next();
                System.out.println("--".repeat(space+1) + "> " + pinNode.value);

            }
            space -= 2;
        }
    }


    protected static ComponentContainer populateTree(int amount ,int bound){
        ComponentContainer grid = new ComponentContainer() {
            final GridTree tree = new GridTree();
            final List<Component> componentList = new ArrayList<>();

            @Override
            public GridTree getPinTree() {
                return tree;
            }

            @Override
            public List<Component> getComponentsList() {
                return componentList;
            }
        };

        Random r = new Random();
        for (int i = 0; i < amount; i++) {
            GridPin.pinFactory(grid, r.nextInt(bound), r.nextInt(bound));
        }

        return grid;
    }

    protected static void testOutOfSequence(int amount, int bound){
        Iterator<Node<Node<GridPin>>> in = populateTree(amount, bound).getPinTree().root.iterator();
        int last = -99999999;
        while (in.hasNext()){
            Node<Node<GridPin>> next = in.next();
            Iterator<Node<GridPin>> in1 = next.iterator();

            int last1 = -999999999;
            while (in1.hasNext()){
                Node<GridPin> next1 = in1.next();

                if (last1 > next1.value)
                    throw new RuntimeException("Esta fora de ordem!");

                last1 = next1.value;
            }

            if (last > next.value)
                throw new RuntimeException("Esta fora de ordem!");

            last = next.value;
        }
    }

    protected static void testSpeedAdd(int amount, int bound){
        ComponentGrid container = new ComponentGrid();
        final int[][] samples = new int[amount][2];

        long nanoTime;

        {
            Random r = new Random();
            for (int[] ints : samples) {
                ints[0] = r.nextInt(bound);
                ints[1] = r.nextInt(bound);
            }
        }

        nanoTime = System.nanoTime();

        for (int[] sample : samples) {
            GridPin.pinFactory(container, sample[0], sample[1]);
        }

        long finalNanoTime = System.nanoTime();

        System.out.println("Add | DeltaT (nS): " + (finalNanoTime - nanoTime));
        System.out.println("Add | DeltaT per Pin (mS): " + ((finalNanoTime - nanoTime) / (1000f * amount)));
    }

    protected static void testSpeedGet(int amount, int bound){
        ComponentContainer component = populateTree(amount, bound);
        GridTree tree = component.getPinTree();

        final int samples_amount = 50;
        final int[][] samples = new int[samples_amount][2];

        long nanoTime;

        {
            //create samples
            Random r = new Random();
            int index = 0;
            while (index < samples_amount){

                Node<Node<GridPin>> xNode;
                do {
                    xNode = tree.root.get(r.nextInt(bound));
                } while (xNode == null);

                Node<GridPin> yNode;
                do {
                    yNode = xNode.get(r.nextInt(bound));
                } while (yNode == null);

                samples[index++] = new int[]{ xNode.value, yNode.value};
            }
        }

        nanoTime = System.nanoTime();

        for (int[] sample : samples) {
            tree.get(sample[0], sample[1]);
        }


        long finalNanoTime = System.nanoTime();

        System.out.println("Get | DeltaT (nS): " + (finalNanoTime - nanoTime));
        System.out.println("Get | DeltaT per Pin (mS): " + ((finalNanoTime - nanoTime) / (1000f * samples_amount)));
    }

    protected static void testSpeedRemove(int amount, int bound){
        final int[][] samples = new int[amount][2];

        ComponentContainer grid = populateTree(amount, bound);
        final Node<Node<Node<GridPin>>> root = grid.getPinTree().root;
        long nanoTime;

        {
            //create samples
            Random r = new Random();
            int index = 0;
            while (index < amount){

                Node<Node<GridPin>> xNode;
                do {
                    xNode = root.get(r.nextInt(bound));
                } while (xNode == null);

                Node<GridPin> yNode;
                do {
                    yNode = xNode.get(r.nextInt(bound));
                } while (yNode == null);

                samples[index++] = new int[]{ xNode.value, yNode.value};
            }
        }

        nanoTime = System.nanoTime();

        for (int[] sample : samples) {
            grid.removePin(sample[0], sample[1]);
        }


        long finalNanoTime = System.nanoTime();

        System.out.println("Rmv | DeltaT (nS): " + (finalNanoTime - nanoTime));
        System.out.println("Rmv | DeltaT per Pin (mS): " + ((finalNanoTime - nanoTime) / (1000f * amount)));
    }
}