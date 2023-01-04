package kuse.bluetable.component;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import kuse.bluetable.component.grid.ComponentGrid;
import kuse.bluetable.tools.SourceTool;
import mods.eln.sim.mna.component.VoltageSource;

public class Source extends Component {

    public static final SourceTool SOURCE_TOOL = new SourceTool();

    public final VoltageSource component;

    public Source(ComponentGrid where, double x, double y) {
        super(where, x, y);
        component = new VoltageSource("VoltageSource");
    }

    public Source(ComponentGrid where, double x, double y, double voltage) {
        super(where, x, y);
        component = new VoltageSource("VoltageSource");
        component.setU(voltage);
    }

    @Override
    public Shape[] initShapes(){
        return new Shape[]{
                new Circle(getPins()[0].x, getPins()[0].y, 20, Color.BROWN)
        };
    }

    @Override
    public mods.eln.sim.mna.component.Component getElectricalComponent() {
        return component;
    }
}
