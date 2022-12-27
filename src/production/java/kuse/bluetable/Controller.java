package kuse.bluetable;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.skin.ScrollPaneSkin;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public static Controller CONTROLLER;

    @FXML
    public Pane scrollPane;
    public AnchorPane pane;
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
        AnchorPane anchor = (AnchorPane) event.getSource();
        anchor.setLayoutX(anchor.getLayoutX() + ((event.getX() - xInitial)*CONTROLLER.zoomAmount));
        anchor.setLayoutY(anchor.getLayoutY() + ((event.getY() - yInitial)*CONTROLLER.zoomAmount));
    }

    public static void logicToZoom(ScrollEvent e, AnchorPane pane){
        if (e.getDeltaY() < 0) {
            //zoom out
            CONTROLLER.zoomAmount += CONTROLLER.zoomAmount == 0.6 ? 0 : -0.05;
        } else {
            //zoom in
            CONTROLLER.zoomAmount += 0.05;
        }

        pane.setScaleX(CONTROLLER.zoomAmount);
        pane.setScaleY(CONTROLLER.zoomAmount);
   }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Controller.CONTROLLER = this;

        this.pane.addEventFilter(
                ScrollEvent.SCROLL,
                h -> {
                    logicToZoom(h,(AnchorPane) h.getSource());
                    h.consume();
                }
        );
    }
}
