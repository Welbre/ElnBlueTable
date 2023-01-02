package kuse.bluetable;

import javafx.scene.Node;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import kuse.bluetable.tools.Component;
import kuse.bluetable.tools.ComponentContainer;

import java.util.List;

public class Worktable extends AnchorPane implements ComponentContainer {

    public static final Worktable table = new Worktable();

    public double zoomAmount = 1;

    private double xInitial, yInitial = 0;

    private final ContextMenu contextMenu;

    protected Worktable() {
        this.contextMenu = new ContextMenu();
        this.setPrefSize(1920,1400);
        this.setStyle("-fx-background-image: url(/assets/image/blueprint.png)");

        this.getChildren().add(new Circle(100,200,50, Color.BLUE));

        setOnMousePressed(this::onMousePressed);
        setOnMouseDragged(this::onMouseDragged);
        setOnContextMenuRequested(this::onContextMenuRequested);
        setOnScroll(this::onScroll);
    }

    @Override
    public List<Component> getComponents() {
        return null;
    }

    private void onMousePressed(MouseEvent event){
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            xInitial = event.getX();
            yInitial = event.getY();
        }
    }
    private void onMouseDragged(MouseEvent event){
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            AnchorPane anchor = (AnchorPane) event.getSource();
            anchor.setLayoutX(anchor.getLayoutX() + ((event.getX() - xInitial)*zoomAmount));
            anchor.setLayoutY(anchor.getLayoutY() + ((event.getY() - yInitial)*zoomAmount));
        }
    }

    private void onContextMenuRequested(ContextMenuEvent event){
        if (contextMenu.isShowing())
            contextMenu.hide();

        contextMenu.show((Node) event.getSource(), event.getX(), event.getY());
    }

    private void onScroll(ScrollEvent e){
        logicToZoom(e, this);
        e.consume();
    }

    public static void logicToZoom(ScrollEvent e, Worktable table){
        if (e.getDeltaY() < 0) {
            //zoom out
            table.zoomAmount += table.zoomAmount == 0.6 ? 0 : -0.05;
        } else {
            //zoom in
            table.zoomAmount += 0.05;
        }

        table.setScaleX(table.zoomAmount);
        table.setScaleY(table.zoomAmount);
    }
}
