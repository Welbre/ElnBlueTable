package kuse.bluetable.tools;

import javafx.event.EventHandler;
import javafx.scene.ImageCursor;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import kuse.bluetable.Worktable;
import kuse.bluetable.component.Source;


public class SourceTool implements Tool {

    private static final ImageCursor icon = Tool.mergeDotInImageCursor("/assets/image/tools/source.png");
    public static final EventHandler<MouseEvent> mouseEvent = e -> {
        if (e.getButton().equals(MouseButton.PRIMARY))
            new Source(Worktable.table ,e.getX(), e.getY());
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
