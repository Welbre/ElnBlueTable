package kuse.bluetable.component;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

import static kuse.bluetable.component.ComponentGrid.GRID_GAP;
import static kuse.bluetable.component.ComponentGrid.convertToGrid;

public abstract class Component {
    protected Shape[] shapes;
    private final int x;
    private final int y;

    protected Component(double x, double y){
        {
            int[] gridPos = convertToGrid(x, y);
            this.x = gridPos[0];
            this.y = gridPos[1];
        }
    }

    protected Component(ComponentGrid grid, double x, double y) {
        {
            int[] gridPos = convertToGrid(x, y);
            this.x = gridPos[0];
            this.y = gridPos[1];
        }
        this.shapes = initShapes();
        grid.addComponent(this);
        draw(grid);
    }

    abstract Shape[] initShapes();

    protected void draw(Pane pane){
        pane.getChildren().addAll(shapes);
    }

    //gets and setts
    public Shape[] getShapes() {
        return shapes;
    }

    public int getGridX(){
        return x;
    }

    public int getGridY(){
        return y;
    }

    public int getX() {
        return x * GRID_GAP;
    }

    public int getY() {
        return y * GRID_GAP;
    }
}
