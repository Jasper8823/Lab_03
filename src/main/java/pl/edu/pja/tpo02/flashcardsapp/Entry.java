package pl.edu.pja.tpo02.flashcardsapp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Entry {
    @Id
    @GeneratedValue
    private int EntryId;
    private String ang;
    private String ger;
    private String pol;


    public Entry(String ang, String ger, String pol) {
        this.ang = ang;
        this.ger = ger;
        this.pol = pol;
    }

    public Entry() {

    }

    public int getId(){return EntryId;}
    public String getEng() {
        return ang;
    }

    public String getGer() {
        return ger;
    }

    public String getPol() {
        return pol;
    }

    public void setEng(String w){ang = w;}

    public void setGer(String w){ger = w;}

    public void setPol(String w){pol = w;}
}
