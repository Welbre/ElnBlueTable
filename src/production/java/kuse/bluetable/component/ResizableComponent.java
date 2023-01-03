package kuse.bluetable.component;

import javafx.scene.layout.Pane;

public abstract class ResizableComponent extends Component {

    private final int x1;
    private final int y1;

    public ResizableComponent(Pane pane, double x, double y, double x1, double y1) {
        super(x, y);
        {
            int[] grid = convertToGrid(x1, y1);
            this.x1 = grid[0];
            this.y1 = grid[1];
        }
        shapes = initShapes();
        draw(pane);
    }


    public int getX1() {
        return x1 * Component.GRID_GAP;
    }

    public int getY1() {
        return y1 * Component.GRID_GAP;
    }

    public int getGridX1(){
        return x1;
    }

    public int getGridY1(){
        return y1;
    }
}
