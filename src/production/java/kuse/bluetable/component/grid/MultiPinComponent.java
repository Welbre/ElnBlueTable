package kuse.bluetable.component.grid;

import kuse.bluetable.component.Component;

public abstract class MultiPinComponent extends Component {

    public MultiPinComponent(ComponentGrid grid, double... cords) {
        super(cords.length / 2);

        for (int i = 0; i < cords.length; i += 2) {
            super.getPins()[i / 2] = GridPin.pinFactory(grid, cords[i], cords[i + 1]);
        }

        draw(grid);
    }

   public GridPin getPin(int index){
        return getPins()[index];
   }

    public int getPinAmount() {
        return getPins().length;
    }
}
