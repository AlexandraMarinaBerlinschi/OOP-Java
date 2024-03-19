package Interfete.impl;

import Interfete.Calcul;

public class Patrat implements Calcul {
    private double latura;

    public Patrat(double latura) {
        this.latura = latura;
    }

    @Override
    public double calculPerimetru() {
        return 4 * latura;
    }
}