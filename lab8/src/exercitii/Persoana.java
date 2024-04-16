package exercitii;

import java.io.Serializable;

public class Persoana implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nume;
    private String prenume;
    private int varsta;
    private double suma;
    private String valuta;

    public Persoana(String nume, String prenume, int varsta, double suma, String valuta) {
        this.nume = nume;
        this.prenume = prenume;
        this.varsta = varsta;
        this.suma = suma;
        this.valuta = valuta;
    }

    public String toString() {
        return nume +prenume+ varsta+suma+ valuta;
    }

    public boolean asemanare(String nume, String prenume) {
        return nume.equalsIgnoreCase(nume) && prenume.equalsIgnoreCase(prenume);
    }

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public int getVarsta() {
        return varsta;
    }

    public double getSuma() {
        return suma;
    }

    public String getValuta() {
        return valuta;
    }
}
