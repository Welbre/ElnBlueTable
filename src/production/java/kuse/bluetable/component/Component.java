package kuse.bluetable.component;

import javafx.scene.shape.Shape;
import kuse.bluetable.component.grid.ComponentGrid;
import kuse.bluetable.component.grid.GridPin;

import java.util.Arrays;

public abstract class Component implements Drawable {
    protected Shape[] shapes;

    private final GridPin[] pin;

    protected Component(int pinAmount){
        pin = new GridPin[pinAmount];
    }

    protected Component(ComponentGrid grid, double x, double y) {
        pin = new GridPin[]{GridPin.pinFactory(grid, x, y)};

        draw(grid);
    }

    public GridPin[] getPins(){
        return pin;
    };
    public abstract mods.eln.sim.mna.component.Component getElectricalComponent();

    protected void draw(ComponentGrid grid){
        this.shapes = initShapes();
        grid.addComponent(this);
        grid.getChildren().addAll(shapes);
        Arrays.stream(getPins()).forEach(h -> grid.getChildren().addAll(h.initShapes()));
    }
    //gets and setts
    public Shape[] getShapes() {
        return shapes;
    }
}
