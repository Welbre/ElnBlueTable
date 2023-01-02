package kuse.core.controllers;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import kuse.bluetable.Worktable;

import java.net.URL;
import java.util.ResourceBundle;


public class MainController implements Initializable {

    public static MainController MAIN_CONTROLLER;

    @FXML
    public TabPane simViewer;
    public MenuBar mainMenuBar;
    public AnchorPane toolBox;
    public SplitPane mainSplitPane;

    public void mainBarSimViewerBuilder(Event e){
        Menu viewMenu = mainMenuBar.getMenus().get(2);
        Menu simViewerMainMenu = (Menu) viewMenu.getItems().get(0);

        simViewerMainMenu.getItems().clear();

        /*for (Tab tab : BlueTable.simViewer.tabs) {
            CheckMenuItem item = new CheckMenuItem(tab.getText());
            item.setSelected(simViewer.getTabs().contains(tab));

            simViewerMainMenu.getItems().add(item);
        }

         */
    }

    public void atTryCloseElectricalSim(Event e){
        TitledPane pane = new TitledPane();
        pane.setText("awdiojawdjiawdjiawjid");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MAIN_CONTROLLER = this;
        ((Pane) mainSplitPane.getItems().get(1)).getChildren().add(Worktable.table);
    }
}
