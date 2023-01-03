package kuse.bluetable.component;

import static kuse.bluetable.component.ComponentGrid.GRID_GAP;
import static kuse.bluetable.component.ComponentGrid.convertToGrid;

public abstract class ResizableComponent extends Component {

    private final int x1;
    private final int y1;

    public ResizableComponent(ComponentGrid grid, double x, double y, double x1, double y1) {
        super(x, y);
        {
            int[] gridCord = convertToGrid(x1, y1);
            this.x1 = gridCord[0];
            this.y1 = gridCord[1];
        }
        shapes = initShapes();
        grid.addComponent(this);
        draw(grid);
    }


    public int getX1() {
        return x1 * GRID_GAP;
    }

    public int getY1() {
        return y1 * GRID_GAP;
    }

    public int getGridX1(){
        return x1;
    }

    public int getGridY1(){
        return y1;
    }
}
