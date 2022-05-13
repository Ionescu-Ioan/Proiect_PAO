package aplicatieBancara.Entitati.Card;


import aplicatieBancara.Entitati.Cont.Cont;
import aplicatieBancara.Entitati.TipCard;

import java.time.LocalDate;
import java.util.Objects;

import static aplicatieBancara.Entitati.TipCard.CREDIT;


public class CardCredit extends Card{

    private final static TipCard tip = CREDIT;
    private double sumaCreditata;


    public CardCredit(int cardId, Cont cont){
        super(cardId, cont);
        sold = this.cont.getSold();
        sumaCreditata = 0;
    }

    public CardCredit(int cardId, Cont cont, int CVV, String numar, LocalDate data, double sumaCreditata) {
        super(cardId, cont, CVV, numar, data);
        sold = this.cont.getSold();
        this.sumaCreditata = sumaCreditata;

    }

    public String toCSV()
    {
        return this.cardId + "," + this.IBAN + "," + this.getTip() + "," + this.sumaCreditata;
    }

    public void actualizareSumaCreditata(double suma)
    {
        sumaCreditata += suma;
    }


    public double getSumaCreditata(){
        return sumaCreditata;
    }

    public String getTip()
    {
        return "CREDIT";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CardCredit that = (CardCredit) o;
        return Double.compare(that.sumaCreditata, sumaCreditata) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), sumaCreditata);
    }
}
