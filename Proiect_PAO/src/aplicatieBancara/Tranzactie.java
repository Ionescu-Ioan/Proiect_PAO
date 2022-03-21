package aplicatieBancara;

import java.util.Comparator;
import java.util.Date;

public class Tranzactie {

    private final String tipTranzactie; //depunere numerar/ retragere numerar/transfer prin IBAN/ plata
    private final String IBANSursa;
    private final String IBANDestinatie;
    private final String numarCard;
    private final double suma;
    private final String descriere;
    private final Date data;

    public Tranzactie(String IBANSursa, String IBANDestinatie , double suma, String descriere, String tipTranzactie, String numarCard) throws Exception {

        if(suma <= 0)
            throw new Exception("Suma introdusa este prea mica!");

        if(tipTranzactie == "depunere" || tipTranzactie == "retragere")
        {
            this.IBANSursa = "";
            this.IBANDestinatie = "";
        }
        else{
            this.IBANSursa = IBANSursa;
            this.IBANDestinatie = IBANDestinatie;
        }

        this.suma = suma;
        this.descriere = descriere;
        this.data = new Date();
        this.tipTranzactie = tipTranzactie;
        this.numarCard = numarCard;
    }

    public String getTipTranzactie() {
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
}
