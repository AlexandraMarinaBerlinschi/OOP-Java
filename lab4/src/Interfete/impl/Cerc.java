package Interfete.impl;

import Interfete.Calcul;

public class Cerc implements Calcul {
    private double raza;

    public Cerc(double raza) {
        this.raza = raza;
    }

    @Override
    public double calculPerimetru() {
        return 2 * Math.PI * raza;
    }
}