package mods.eln.sim.nbt;

import mods.eln.Eln;
import mods.eln.misc.Utils;

public class NbtElectricalGateInput extends NbtElectricalLoad {

    public NbtElectricalGateInput(String name) {
        super(name);
        Eln.instance.signalCableDescriptor.applyTo(this);
    }

    public String plot(String str) {
        return Utils.plotSignal(getU());
    }

    public boolean stateHigh() {
        return getU() > Eln.SVU * 0.6;
    }

    public boolean stateLow() {
        return getU() < Eln.SVU * 0.2;
    }

    public double getNormalized() {
        double norm = getU() * Eln.SVUinv;
        if (norm < 0.0) norm = 0.0;
        if (norm > 1.0) norm = 1.0;
        return norm;
    }

    public double getBornedU() {
        double U = this.getU();
        if (U < 0.0) U = 0.0;
        if (U > Eln.SVU) U = Eln.SVU;
        return U;
    }
}
