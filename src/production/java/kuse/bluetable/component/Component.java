package kuse.bluetable.component;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

public abstract class Component {
    public static final int GRID_GAP = 50;

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

    protected Component(Pane pane, double x, double y) {
        {
            int[] gridPos = convertToGrid(x, y);
            this.x = gridPos[0];
            this.y = gridPos[1];
        }
        this.shapes = initShapes();
        draw(pane);
    }

    abstract Shape[] initShapes();

    protected void draw(Pane pane){
        pane.getChildren().addAll(shapes);
    }

    protected static int[] convertToGrid(double x, double y){
        return new int[]{
                (x % GRID_GAP) > GRID_GAP / 2f ? (int) (x/GRID_GAP) + 1 : (int) (x/GRID_GAP)
                ,
                (y % GRID_GAP) > GRID_GAP / 2f ? (int) (y/GRID_GAP) + 1 : (int) (y/GRID_GAP)
        };
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
