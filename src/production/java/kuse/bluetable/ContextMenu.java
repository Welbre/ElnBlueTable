package kuse.bluetable;

import javafx.event.ActionEvent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import kuse.bluetable.component.Wire;
import kuse.bluetable.tools.Tool;

public class ContextMenu extends javafx.scene.control.ContextMenu {

    private static final MenuItem wire;

    private static final MenuItem source;
    private static final Menu resistor;

    private static final MenuItem resistor_static;
    private static final MenuItem resistor_dynamic;

    static {
        {
            wire = new MenuItem("wire");
            wire.setOnAction(ContextMenu::handle);
        }

        source = new MenuItem("source");
        resistor_dynamic = new MenuItem("Dynamic");
        resistor_static = new MenuItem("Static");

        resistor = new Menu("resistors");
        resistor.getItems().addAll(resistor_static, resistor_dynamic);

        ImageView view = new ImageView("/assets/image/icons/icons8_list_view_96px.png");
        view.setFitHeight(15);
        view.setFitWidth(15);
        resistor.setGraphic(view);

    }

    public ContextMenu() {
        this.getItems().addAll(wire, source, resistor);
    }

    private static void handle(ActionEvent e) {
        Tool tool = Worktable.table.getSelectedTool();
        if (tool != Wire.WIRE_TOOL)
            Worktable.table.setSelectedTool(Wire.WIRE_TOOL);
        else
            Worktable.table.setSelectedTool(null);
    }
}
