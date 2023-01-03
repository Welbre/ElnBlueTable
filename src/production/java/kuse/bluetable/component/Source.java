package kuse.bluetable.component;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import kuse.bluetable.tools.SourceTool;

public class Source extends Component {

    public static final SourceTool SOURCE_TOOL = new SourceTool();

    //todo test
    public double voltage = 10;

    public Source(Pane where, double x, double y) {
        super(where, x, y);
    }

    @Override
    public Shape[] initShapes(){
        return new Shape[]{
                new Circle(getX(), getY(), 20, Color.BROWN)
        };
    }
}
