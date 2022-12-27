package kuse.core.controllers;

import kuse.core.launcher.BlueTable;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class WindowBasicsFunction {

    public static HBox toolBox;


    private static void setMin(Stage primaryStage){
        primaryStage.setMaximized(false);
        ((ImageView) toolBox.getChildren().get(1)).setImage(new Image("/assets/image/icons8_maximize_button_96px.png"));
    }
    private static void setMax(Stage primaryStage){
        ((ImageView) toolBox.getChildren().get(1)).setImage(new Image("/assets/image/icons8_restore_down_96px.png"));
        primaryStage.setX(0);
        primaryStage.setY(0);
        primaryStage.setWidth(1920);
        primaryStage.setHeight(1080);
        primaryStage.setMaximized(true);
    }

    public static void setWindowsToolBoxFunctions() {
        toolBox = (HBox) MainController.MAIN_CONTROLLER.toolBox.getChildren().filtered(node -> node instanceof HBox).get(0);

        toolBox.getChildren().get(0).addEventHandler(MouseEvent.MOUSE_CLICKED, handle -> {
            BlueTable.primaryStage.setIconified(true);
            BlueTable.primaryStage.setMaximized(false);
        });

        toolBox.getChildren().get(1).addEventHandler(MouseEvent.MOUSE_CLICKED, handle -> {
            if (BlueTable.primaryStage.isMaximized()) {
                setMin(BlueTable.primaryStage);
            } else {
                setMax(BlueTable.primaryStage);
            }
        });

        toolBox.getChildren().get(2).addEventHandler(MouseEvent.MOUSE_CLICKED, handle -> BlueTable.primaryStage.close());
    }



    @Deprecated
    public static void addResizeListener(Stage stage, Parent parent) {
        addResizeListener(stage, parent, 1, 1, Double.MAX_VALUE, Double.MAX_VALUE);
    }

    public static void addResizeListener(Stage stage, Parent parent, double minWidth, double minHeight, double maxWidth, double maxHeight) {
        ResizeListener resizeListener = new ResizeListener(stage, minWidth, maxWidth, minHeight, maxHeight);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_MOVED, resizeListener);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_PRESSED, resizeListener);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_DRAGGED, resizeListener);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_EXITED, resizeListener);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, resizeListener);

        for (Node child : parent.getChildrenUnmodifiable()) {
            addListenerDeeply(child, resizeListener);
        }
    }

    private static void addListenerDeeply(Node node, EventHandler<MouseEvent> listener) {
        node.addEventHandler(MouseEvent.MOUSE_MOVED, listener);
        node.addEventHandler(MouseEvent.MOUSE_PRESSED, listener);
        node.addEventHandler(MouseEvent.MOUSE_DRAGGED, listener);
        node.addEventHandler(MouseEvent.MOUSE_EXITED, listener);
        node.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, listener);
        if (node instanceof Parent parent) {
            ObservableList<Node> children;
            if (parent instanceof SplitPane pane){
                children = pane.getItems();
            } else {
                children = parent.getChildrenUnmodifiable();
            }

            for (Node child : children) {
                addListenerDeeply(child, listener);
            }
        }
    }

    static class ResizeListener implements EventHandler<MouseEvent> {
        private final Stage stage;
        private Cursor cursorEvent = Cursor.DEFAULT;
        private boolean resizing = true;
        private double startX = 0;
        private double startY = 0;
        private double screenOffsetX = 0;
        private double screenOffsetY = 0;

        // Max and min sizes for controlled stage
        private final double minWidth;
        private final double maxWidth;
        private final double minHeight;
        private final double maxHeight;

        public ResizeListener(Stage stage, double minWidth, double maxWidth, double minHeight, double maxHeight) {
            this.stage = stage;
            this.minWidth = minWidth;
            this.maxWidth = maxWidth;
            this.minHeight = minHeight;
            this.maxHeight = maxHeight;
        }

        @Override
        public void handle(MouseEvent mouseEvent) {
            EventType<? extends MouseEvent> mouseEventType = mouseEvent.getEventType();
            Scene scene = stage.getScene();

            double mouseEventX = mouseEvent.getSceneX(),
                    mouseEventY = mouseEvent.getSceneY(),
                    sceneWidth = scene.getWidth(),
                    sceneHeight = scene.getHeight();

            int border = 4;
            if (MouseEvent.MOUSE_MOVED.equals(mouseEventType)) {
                if (mouseEventX < border && mouseEventY < border) {
                    cursorEvent = Cursor.NW_RESIZE;
                } else if (mouseEventX < border && mouseEventY > sceneHeight - border) {
                    cursorEvent = Cursor.SW_RESIZE;
                } else if (mouseEventX > sceneWidth - border && mouseEventY < border) {
                    cursorEvent = Cursor.NE_RESIZE;
                } else if (mouseEventX > sceneWidth - border && mouseEventY > sceneHeight - border) {
                    cursorEvent = Cursor.SE_RESIZE;
                } else if (mouseEventX < border) {
                    cursorEvent = Cursor.W_RESIZE;
                } else if (mouseEventX > sceneWidth - border) {
                    cursorEvent = Cursor.E_RESIZE;
                } else if (mouseEventY < border) {
                    cursorEvent = Cursor.N_RESIZE;
                } else if (mouseEventY > sceneHeight - border) {
                    cursorEvent = Cursor.S_RESIZE;
                } else {
                    cursorEvent = Cursor.DEFAULT;
                }
                scene.setCursor(cursorEvent);


            } else if (MouseEvent.MOUSE_EXITED.equals(mouseEventType)) {
                scene.setCursor(Cursor.DEFAULT);

            } else if (MouseEvent.MOUSE_PRESSED.equals(mouseEventType)) {
                startX = stage.getWidth() - mouseEventX;
                startY = stage.getHeight() - mouseEventY;

            } else if (MouseEvent.MOUSE_DRAGGED.equals(mouseEventType)) {

                if (!Cursor.DEFAULT.equals(cursorEvent)) {
                    resizing = true;
                    if (!Cursor.W_RESIZE.equals(cursorEvent) && !Cursor.E_RESIZE.equals(cursorEvent)) {
                        double minHeight = stage.getMinHeight() > (border * 2) ? stage.getMinHeight() : (border * 2);
                        if (Cursor.NW_RESIZE.equals(cursorEvent) || Cursor.N_RESIZE.equals(cursorEvent) || Cursor.NE_RESIZE.equals(cursorEvent)) {
                            if (stage.getHeight() > minHeight || mouseEventY < 0) {
                                setStageHeight(stage.getY() - mouseEvent.getScreenY() + stage.getHeight());
                                stage.setY(mouseEvent.getScreenY() );
                            }

                        } else {
                            if (stage.getHeight() > minHeight || mouseEventY + startY - stage.getHeight() > 0) {
                                setStageHeight(mouseEventY + startY);
                            }
                        }
                    }

                    if (!Cursor.N_RESIZE.equals(cursorEvent) && !Cursor.S_RESIZE.equals(cursorEvent)) {
                        double minWidth = stage.getMinWidth() > (border * 2) ? stage.getMinWidth() : (border * 2);
                        if (Cursor.NW_RESIZE.equals(cursorEvent) || Cursor.W_RESIZE.equals(cursorEvent) || Cursor.SW_RESIZE.equals(cursorEvent)) {
                            if (stage.getWidth() > minWidth || mouseEventX < 0) {
                                setStageWidth(stage.getX() - mouseEvent.getScreenX() + stage.getWidth());
                                stage.setX(mouseEvent.getScreenX());
                            }
                        } else {
                            if (stage.getWidth() > minWidth || mouseEventX + startX - stage.getWidth() > 0) {
                                setStageWidth(mouseEventX + startX);
                            }
                        }
                    }
                    resizing = false;
                }
            }

            if (MouseEvent.MOUSE_PRESSED.equals(mouseEventType) && Cursor.DEFAULT.equals(cursorEvent)) {
                resizing = false;
                screenOffsetX = stage.getX() - mouseEvent.getScreenX();
                screenOffsetY = stage.getY() - mouseEvent.getScreenY();
            }

            if (MouseEvent.MOUSE_DRAGGED.equals(mouseEventType) && Cursor.DEFAULT.equals(cursorEvent) && !resizing) {
                stage.setX(mouseEvent.getScreenX() + screenOffsetX);
                stage.setY(mouseEvent.getScreenY() + screenOffsetY);
                setMin(stage);
            }

        }

        private void setStageWidth(double width) {
            width = Math.min(width, maxWidth);
            width = Math.max(width, minWidth);
            stage.setWidth(width);
            setMin(stage);
        }

        private void setStageHeight(double height) {
            height = Math.min(height, maxHeight);
            height = Math.max(height, minHeight);
            stage.setHeight(height);
            setMin(stage);
        }

    }
}
