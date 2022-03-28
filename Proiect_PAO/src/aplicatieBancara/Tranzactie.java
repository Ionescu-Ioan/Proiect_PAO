package aplicatieBancara;

import javax.swing.*;
import java.util.Comparator;
import java.util.Date;

import static aplicatieBancara.TipTranzactie.*;


public class Tranzactie {

    private final TipTranzactie tipTranzactie; //depunere numerar/ retragere numerar/transfer prin IBAN
    private final String IBANSursa;
    private final String IBANDestinatie;
    private final double suma;
    private final String descriere;
    private final Date data;
    //private final String numarCard;

    public Tranzactie(String IBANSursa, String IBANDestinatie , double suma, String descriere, TipTranzactie tipTranzactie, String numarCard) throws Exception
    {

        if(suma <= 0)
            throw new Exception("Suma introdusa este prea mica!");

        switch(tipTranzactie){

            case DEPUNERE:
                this.IBANSursa = "";
                this.IBANDestinatie = IBANDestinatie;
                break;

            case RETRAGERE:
                this.IBANSursa = IBANSursa;
                this.IBANDestinatie = "";
                break;

            case TRANSFER:
                this.IBANSursa = IBANSursa;
                this.IBANDestinatie = IBANDestinatie;
                break;

            default :
                throw new Exception("Tipul de tranzactie selectata nu exista!");
        }

        this.suma = suma;
        this.descriere = descriere;
        this.data = new Date();
        this.tipTranzactie = tipTranzactie;

    }

    public TipTranzactie getTipTranzactie() {
        return tipTranzactie;
    }

    public String getIBANSursa() {
        return IBANSursa;
    }

    public String getIBANDestinatie() {
        return IBANDestinatie;
    }

    public double getSuma() {
        return suma;
    }

    public String getDescriere() {
        return descriere;
    }

    public Date getData() {
        return data;
    }


    @Override
    public String toString() {
        return "Tranzactie{" +
                "tipTranzactie=" + tipTranzactie +
                ", IBANSursa='" + IBANSursa + '\'' +
                ", IBANDestinatie='" + IBANDestinatie + '\'' +
                ", suma=" + suma +
                ", descriere='" + descriere + '\'' +
                ", data=" + data +
                '}';
    }
}
