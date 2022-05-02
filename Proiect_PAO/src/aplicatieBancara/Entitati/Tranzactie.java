package aplicatieBancara.Entitati;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class Tranzactie {

    private final TipTranzactie tipTranzactie; //depunere numerar/ retragere numerar/transfer prin IBAN
    private final String IBANSursa;
    private final String IBANDestinatie;
    private final double suma;
    private final String descriere;
    private LocalDate data;
    //private final String numarCard;

    public Tranzactie(String IBANSursa, String IBANDestinatie , double suma, String descriere, TipTranzactie tipTranzactie) throws Exception
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
        this.data = LocalDate.now();
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

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data){this.data = data;}

    public String toCSV(){
        String tip = null;
        if(this.tipTranzactie == TipTranzactie.DEPUNERE)
            tip = "DEPUNERE";
        else if(this.tipTranzactie == TipTranzactie.RETRAGERE)
            tip = "RETRAGERE";
        else if(this.tipTranzactie == TipTranzactie.TRANSFER)
            tip = "TRANSFER";

        return this.IBANSursa + "," + this.IBANDestinatie + "," + this.suma + "," + this.descriere + "," + tip + "," + this.data;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tranzactie that = (Tranzactie) o;
        return Double.compare(that.suma, suma) == 0 && tipTranzactie == that.tipTranzactie && IBANSursa.equals(that.IBANSursa) && IBANDestinatie.equals(that.IBANDestinatie) && descriere.equals(that.descriere) && data.equals(that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipTranzactie, IBANSursa, IBANDestinatie, suma, descriere, data);
    }
}
