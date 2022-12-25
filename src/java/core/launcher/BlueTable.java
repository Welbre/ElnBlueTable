package core.launcher;

import core.controllers.Controller;
import core.controllers.WindowBasicsFunction;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class BlueTable extends Application {

    public static Stage primaryStage;
    public static Controller mainController;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader load = new FXMLLoader(getClass().getResource("/assets/fxml/MainScreen.fxml"));
        Parent root = load.load();

        //Setting statics
        BlueTable.primaryStage = primaryStage;
        BlueTable.mainController = load.getController();

        primaryStage.setTitle("Eln BlueTable");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.getIcons().add(new Image("/assets/image/bluetable.png"));
        primaryStage.setResizable(true);
        primaryStage.setMaximized(true);
        primaryStage.setScene(new Scene(root,1920,1080));

        //initFunctions
        WindowBasicsFunction.setWindowsToolBoxFunctions();
        WindowBasicsFunction.addResizeListener(primaryStage,640,360,1920,1080);

        primaryStage.show();
    }
}
