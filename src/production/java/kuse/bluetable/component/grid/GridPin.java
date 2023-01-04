package kuse.bluetable.component.grid;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import kuse.bluetable.component.Drawable;
import mods.eln.sim.mna.state.State;

public class GridPin extends State implements Drawable {

    public static final int GRID_GAP = 50;

    public static final int DEFAULT_LEN = 10;
    public static final Color DISCONNECTED_PIN = Color.RED;
    public static final Color CONNECTED_PIN = Color.GREEN;

    private boolean isConnected = false;

    public final int x;
    public final int y;

    private GridPin(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private GridPin(double x, double y){
        int[] ints = convertToGrid(x, y);
        this.x = ints[0];
        this.y = ints[1];
    }

    public static GridPin pinFactory(ComponentContainer container, double x, double y){
        return pinFactory(container, convertToGrid(x), convertToGrid(y));
    }

    public static GridPin pinFactory(ComponentContainer container, int x, int y) {
        return container.addPin(new GridPin(x, y));
    }

    @Override
    public Shape[] initShapes() {

        double half = DEFAULT_LEN / 2f;
        Rectangle pin = new Rectangle(
                x * GRID_GAP - half,
                y * GRID_GAP - half,
                DEFAULT_LEN,
                DEFAULT_LEN
        );

        if (isConnected)
            pin.setFill(CONNECTED_PIN);
        else
            pin.setFill(DISCONNECTED_PIN);

        return new Shape[]{pin};
    }

    protected static int[] convertToGrid(double[] cords){
        return convertToGrid(cords[0], cords[1]);
    }

    protected static int[] convertToGrid(double x, double y){
        return new int[]{
                convertToGrid(x)
                ,
                convertToGrid(y)
        };
    }

    protected static int convertToGrid(double value){
        return (value % GRID_GAP) > GRID_GAP / 2f ? (int) (value/GRID_GAP) + 1 : (int) (value/GRID_GAP);
    }


    public int getX() {
        return x * GRID_GAP;
    }

    public int getY() {
        return y * GRID_GAP;
    }
}
