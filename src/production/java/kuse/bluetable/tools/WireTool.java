package kuse.bluetable.tools;

import javafx.event.EventHandler;
import javafx.scene.ImageCursor;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import kuse.bluetable.Worktable;
import kuse.bluetable.component.Wire;

public class WireTool implements Tool {

    private static double[] WIRE_CREATOR_HELPER;
    public static final ImageCursor icon = Tool.mergeDotInImageCursor("/assets/image/wireTool.png");

    private static final EventHandler<MouseEvent> mouseEvent = h-> {
        if (h.getButton().equals(MouseButton.PRIMARY)){
            if (WIRE_CREATOR_HELPER == null){
                WIRE_CREATOR_HELPER = new double[]{h.getX(), h.getY()};
            } else {
                new Wire(Worktable.table, WIRE_CREATOR_HELPER[0], WIRE_CREATOR_HELPER[1], h.getX(), h.getY());
                WIRE_CREATOR_HELPER = null;
            }
        }
    };

    @Override
    public void activate() {
        Worktable.table.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent);
    }

    @Override
    public void deactivate() {
        Worktable.table.removeEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent);
    }

    @Override
    public ImageCursor getImageCursor() {
        return icon;
    }
}
