package tourism;

import tourism.TouristPackage;
import java.util.List;
import java.util.ArrayList;

public class Destination {
    private String tara;
    private String tipAtractie;
    private String activitati;
    private List<TouristPackage> pacheteDisponibile = new ArrayList<>();
    private String destinatie;

    public Destination(String destinatie) {
        this.destinatie = destinatie;
    }

    public String getNumeDestinatie() {
        return destinatie;
    }

    public void addTouristPackage(TouristPackage pachet) {
        pacheteDisponibile.add(pachet);
    }

    public List<TouristPackage> getPacheteDisponibile() {
        return pacheteDisponibile;
    }

    public String getTipAtractie() {
        return tipAtractie;
    }

    public void setTipAtractie(String tipAtractie){
        this.tipAtractie = tipAtractie;
    }

    public String getTara() {
        return tara;
    }

    public void setTara(String tara) {
        this.tara = tara;
    }

    public String getActivitati() {
        return activitati;
    }

    public void setActivitati(String activitati) {
        this.activitati = activitati;
    }

    public String toString() {
        return destinatie;
    }

}
