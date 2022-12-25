package core.controllers;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;


public class Controller {

    @FXML
    public TabPane simViewer;
    public MenuBar mainMenuBar;
    public AnchorPane toolBox;

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
}
