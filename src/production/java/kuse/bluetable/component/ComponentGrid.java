package kuse.bluetable.component;

import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ComponentGrid extends AnchorPane implements ComponentContainer {

    public static final int GRID_GAP = 50;

    public final HashMap<int[], Component> componentHashMap = new HashMap<>();
    public final List<Component> componentList = new ArrayList<>();

    protected static int[] convertToGrid(double x, double y){
        return new int[]{
                (x % GRID_GAP) > GRID_GAP / 2f ? (int) (x/GRID_GAP) + 1 : (int) (x/GRID_GAP)
                ,
                (y % GRID_GAP) > GRID_GAP / 2f ? (int) (y/GRID_GAP) + 1 : (int) (y/GRID_GAP)
        };
    }

    @Override
    public List<Component> getComponentsList() {
        return componentList;
    }

    @Override
    public HashMap<int[], Component> getComponentsTree() {
        return componentHashMap;
    }
}
