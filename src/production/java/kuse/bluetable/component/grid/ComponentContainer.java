package kuse.bluetable.component.grid;

import kuse.bluetable.component.Component;

import java.util.*;
public interface ComponentContainer{

    default GridPin addPin(GridPin pin){
        GridPin pinC = getPin(pin.x,pin.y);

        if (pinC == null) {
            getPinTree().add(pin);
            return  pin;
        }
        return pinC;
    }

    default Component[] removePin(int x, int y){
        Component[] components = getComponents(x, y);

        if (components == null || components.length == 0) {
            return new Component[0];
        }
        else {
            Arrays.stream(components).forEach(this::removeComponent);
            return components;
        }
    }

    default GridPin getPin(int x, int y){
        return getPinTree().get(x, y);
    }

    default void addComponent(Component component){
        Arrays.stream(component.getPins()).forEach(getPinTree()::add);
        getComponentsList().add(component);
    }

    default Component[] getComponents(int x, int y){
        GridPin pin = getPin(x, y);

        List<Component> componentList = new ArrayList<>();

        for (Component component : getComponentsList()) {
            for (GridPin componentPin : component.getPins()) {
                if (componentPin == pin)
                    componentList.add(component);
            }
        }

        return componentList.toArray(new Component[0]);
    }

    default void removeComponent(int x, int y){
        for (Component component : getComponents(x, y)) {
            removeComponent(component);
        }
    }

    default void removeComponent(Component component){
        getComponentsList().remove(component);

        for (GridPin pin : component.getPins()) {
            for (Component c : getComponentsList()) {
                for (GridPin cPin : c.getPins()) {
                    if (pin == cPin)
                        return;
                }
            }
        }

    }

    GridTree getPinTree();
    List<Component> getComponentsList();
}
