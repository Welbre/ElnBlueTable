package kuse.bluetable.component;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import kuse.bluetable.tools.WireTool;

public class Wire extends Component{

    public static final WireTool WIRE_TOOL= new WireTool();

    public static final int DEFAULT_LEN = 20;
    public static final Color POINT_COLOR = Color.RED;
    public static final int DEFAULT_LINE_WIDTH = 2;

    private final Shape[] shapes;
    private final double[] ini;
    private final double[] fin;

    public Wire(Pane where ,double x0, double y0, double x1, double y1) {
        this.ini = new double[]{x0, y0};
        this.fin = new double[]{x1, y1};

        shapes = draw(where);
    }

    private Shape[] draw(Pane pane){
        int half = DEFAULT_LEN / 2;

        Rectangle rec0 = new Rectangle(this.ini[0] - half, this.ini[1] - half, DEFAULT_LEN, DEFAULT_LEN);
        rec0.setFill(POINT_COLOR);

        Rectangle rec1 = new Rectangle(this.fin[0] - half, this.fin[1] - half, DEFAULT_LEN, DEFAULT_LEN);
        rec1.setFill(POINT_COLOR);

        Line line = new Line(this.ini[0], this.ini[1], this.fin[0], this.fin[1]);
        line.setStrokeWidth(DEFAULT_LINE_WIDTH);

        Shape[] shapes = {
                rec0,
                rec1,
                line
        };

        pane.getChildren().addAll(shapes);

        return shapes;
    }
}
