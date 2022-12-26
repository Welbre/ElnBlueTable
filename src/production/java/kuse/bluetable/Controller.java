package kuse.bluetable;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public static Controller CONTROLLER;

    @FXML
    public AnchorPane anchor;
    public ScrollPane pane;
    public double zoomAmount = 1;

    private double xInitial, yInitial = 0;
    public void onMousePressed(MouseEvent event){
        xInitial = event.getX();
        yInitial = event.getY();
    }

    public void onScroll(ScrollEvent e){
        System.out.println("scrolll");
    }
    public void onMouseDragged(MouseEvent event){
        pane.setHvalue(pane.getHvalue() + ((event.getX() - xInitial) > 0 ? -10 : 10));
        pane.setVvalue(pane.getVvalue() + ((event.getY() - yInitial) > 0 ? -10 : 10));
    }

    public static void logicToZoom(ScrollEvent e, ScrollPane pane){
        if (e.getDeltaY() < 0) {
            //zoom out
            CONTROLLER.zoomAmount += CONTROLLER.zoomAmount == 1 ? 0 : -0.125;
        } else {
            //zoom in
            CONTROLLER.zoomAmount += 0.125;
        }


        pane.setScaleX(CONTROLLER.zoomAmount);
        pane.setScaleY(CONTROLLER.zoomAmount);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Controller.CONTROLLER = this;

        CONTROLLER.pane.addEventFilter(
                ScrollEvent.SCROLL,
                h -> {
                    logicToZoom(h,(ScrollPane) h.getSource());
                    h.consume();
                }
        );
    }
}
