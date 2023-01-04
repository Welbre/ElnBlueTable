package kuse.bluetable.component.grid;

import kuse.bluetable.component.Component;

public abstract class BiPinComponent extends Component {

    public BiPinComponent(ComponentGrid grid, double x0, double y0, double x1, double y1) {
        super(2);

        super.getPins()[0] = GridPin.pinFactory(grid, x0, y0);
        super.getPins()[1] = GridPin.pinFactory(grid, x1, y1);
        draw(grid);
    }
}
