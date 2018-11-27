/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aufgabe.pkg2;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Alex
 */
public class Auftragsposition {
    
    private SimpleStringProperty artikelID;
    private SimpleStringProperty artikelbezeichnung;
    private SimpleIntegerProperty anzahl;
    private SimpleDoubleProperty einzelpreis;
    private SimpleDoubleProperty gesamtpreis;

    public Auftragsposition(String artikelID, String artikelbezeichnung, Integer anzahl, double einzelpreis, double gesamtpreis) {
        this.artikelID = new SimpleStringProperty(artikelID);
        this.artikelbezeichnung = new SimpleStringProperty(artikelbezeichnung);
        this.anzahl = new SimpleIntegerProperty(anzahl);
        this.einzelpreis = new SimpleDoubleProperty(einzelpreis);
        this.gesamtpreis = new SimpleDoubleProperty(anzahl * einzelpreis);
    }
   
    
    public String getArtikelID() {
        return artikelID.get();
    }

    public void setArtikelID(String artikelID) {
        this.artikelID = new SimpleStringProperty(artikelID);
    }
    
    public String getArtikelbezeichnung() {
        return artikelbezeichnung.get();
    }

    public void setArtikelbezeichnung(String artikelbezeichnung) {
        this.artikelbezeichnung = new SimpleStringProperty(artikelbezeichnung);
    }

   
    public int getAnzahl() {
        return anzahl.get();
    }
       
    public void setAnzahl(int anzahl) {
        this.anzahl = new SimpleIntegerProperty(anzahl);
    }
    
    public double getEinzelpreis() {
        return einzelpreis.get();
    }
       
    public void setEinzelpreis(double einzelpreis) {
        this.einzelpreis = new SimpleDoubleProperty(einzelpreis);
    }
    
    public double getGesamtpreis() {
        return gesamtpreis.get();
    }
    
    public String toString()
    {
        return String.format("%s %s", artikelID, artikelbezeichnung);
        
        
    }
    
}
    

