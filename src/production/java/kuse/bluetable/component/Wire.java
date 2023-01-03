package kuse.bluetable.component;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import kuse.bluetable.tools.WireTool;

public class Wire extends ResizableComponent{

    public static final WireTool WIRE_TOOL= new WireTool();

    public static final int DEFAULT_LEN = 20;
    public static final Color POINT_COLOR = Color.RED;
    public static final int DEFAULT_LINE_WIDTH = 2;

    public Wire(ComponentGrid grid ,double x0, double y0, double x1, double y1) {
        super(grid, x0, y0, x1, y1);
    }

    @Override
     public Shape[] initShapes(){
        int half = DEFAULT_LEN / 2;

        Rectangle rec0 = new Rectangle(getX() - half, getY() - half, DEFAULT_LEN, DEFAULT_LEN);
        rec0.setFill(POINT_COLOR);

        Rectangle rec1 = new Rectangle(getX1() - half, getY1() - half, DEFAULT_LEN, DEFAULT_LEN);
        rec1.setFill(POINT_COLOR);

        Line line = new Line(getX(), getY(), getX1(), getY1());
        line.setStrokeWidth(DEFAULT_LINE_WIDTH);

        return new Shape[]{
                rec0,
                rec1,
                line
        };
    }
}
