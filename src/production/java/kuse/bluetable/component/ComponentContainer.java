package kuse.bluetable.component;

import java.util.HashMap;
import java.util.List;

public interface ComponentContainer{

    default void addComponent(Component component){
        getComponentsTree().put(new int[]{component.getGridX(),component.getGridY()}, component);
        getComponentsList().add(component);
    }

    default Component getComponent(int x, int y){
        return getComponentsTree().get(new int[]{x, y});
    }

    default void removeComponent(int x, int y){
        removeComponent(getComponent(x, y));
    }

    default void removeComponent(Component component){
        getComponentsTree().remove(new int[]{component.getGridX(), component.getGridY()});
        getComponentsList().remove(component);
    }

    HashMap<int[], Component> getComponentsTree();
    List<Component> getComponentsList();
}
