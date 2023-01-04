package kuse.bluetable.component;

import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import kuse.bluetable.component.grid.BiPinComponent;
import kuse.bluetable.component.grid.ComponentGrid;
import kuse.bluetable.tools.WireTool;
import mods.eln.sim.mna.component.Component;
import mods.eln.sim.mna.component.Resistor;

public class Wire extends BiPinComponent {

    public static final WireTool WIRE_TOOL= new WireTool();

    public static final int DEFAULT_LINE_WIDTH = 2;

    public final Resistor component;

    public Wire(ComponentGrid grid , double x0, double y0, double x1, double y1) {
        super(grid, x0, y0, x1, y1);
        component = new Resistor(getPins()[0], getPins()[1]);
        component.setR(0.001);
    }

    @Override
     public Shape[] initShapes(){

        //todo Cannot read field "x" because "kuse.bluetable.component.Wire.getPins()[0]" is null
        Line line = new Line(getPins()[0].getX(), getPins()[0].getY(), getPins()[1].getX(), getPins()[1].getY());
        line.setStrokeWidth(DEFAULT_LINE_WIDTH);

        return new Shape[]{line};
    }

    @Override
    public Component getElectricalComponent() {
        return null;
    }
}
