package kuse.bluetable.component;

import javafx.event.EventHandler;
import javafx.scene.ImageCursor;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import kuse.bluetable.Worktable;
import kuse.bluetable.tools.Tool;

public class Ground extends Source {

    public static final Tool GROUND_TOOL = new Tool() {

        static final EventHandler<MouseEvent> handle = e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)){
                new Ground(Worktable.table, e.getX(), e.getY());
            }
        };

        @Override
        public void activate() {
            Worktable.table.addEventHandler(MouseEvent.MOUSE_CLICKED, handle);
        }

        @Override
        public void deactivate() {
            Worktable.table.removeEventHandler(MouseEvent.MOUSE_CLICKED, handle);
        }

        @Override
        public ImageCursor getImageCursor() {
            return Tool.mergeDotInImageCursor("ground.png");
        }
    };

    public Ground(Pane where, double x, double y) {
        super(where, x, y);

        voltage = 0;
    }

    @Override
    public Shape[] initShapes() {
        return new Shape[]{
                new Circle(getX(), getY(), 20, Color.GREEN)
        };
    }
}
