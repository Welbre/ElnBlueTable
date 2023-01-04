package kuse.bluetable.component.grid;

import javafx.scene.layout.AnchorPane;
import kuse.bluetable.component.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ComponentGrid extends AnchorPane implements ComponentContainer {

    private final GridTree gridTree = new GridTree();
    private final List<Component> componentList = new ArrayList<>();

    public GridPin getState(Component c){
        return getState(c.getPins()[0].x, c.getPins()[0].y);
    }

    public GridPin getState(int x, int y){
        return gridTree.get(x,y);
    }

    @Override
    public List<Component> getComponentsList() {
        return componentList;
    }

    @Override
    public GridTree getPinTree() {
        return gridTree;
    }
}
