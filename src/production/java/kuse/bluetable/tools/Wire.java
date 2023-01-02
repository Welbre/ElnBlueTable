package kuse.bluetable.tools;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import kuse.bluetable.Worktable;

public class Wire extends Component {

    public static final int DEFAULT_LEN = 20;
    public static final Color POINT_COLOR = Color.RED;
    public static final int DEFAULT_LINE_WIDTH = 2;

    private static boolean IS_CREATING = false;
    private static double[] WIRE_CREATOR_HELPER;
    private static final EventHandler<MouseEvent> mouseEvent = h-> {
        if (h.getButton().equals(MouseButton.PRIMARY)){
            if (IS_CREATING) {
                if (WIRE_CREATOR_HELPER == null){
                    WIRE_CREATOR_HELPER = new double[]{h.getX(), h.getY()};
                } else {
                    new Wire(Worktable.table, WIRE_CREATOR_HELPER[0], WIRE_CREATOR_HELPER[1], h.getX(), h.getY());
                    WIRE_CREATOR_HELPER = null;
                }
            }
        }
    };

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

    public static void onUseIt(Pane where){
        if (!IS_CREATING){
            where.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent);
        } else {
            where.removeEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent);
        }
        IS_CREATING = !IS_CREATING;
    }
}
