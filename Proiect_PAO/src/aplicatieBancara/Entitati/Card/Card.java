package aplicatieBancara.Entitati.Card;

import aplicatieBancara.Entitati.Cont.Cont;

import java.time.LocalDate;
import java.util.*;


public abstract class Card {

    protected final int cardId;
    protected final int CVV;
    protected String numar;
    protected String IBAN;
    protected double sold;
    protected Cont cont;
    protected final LocalDate dataExpirare;
    //protected String PIN;


    static private final HashSet<String> numereCardAlocate = new HashSet<>();

    abstract public String getTip();

    public Card(int cardId, Cont cont) {
        this.cardId = cardId;
        this.IBAN = cont.getIBAN();
        this.cont = cont;
        this.numar = generareNumarCard();

        //generare numar card
        while(numereCardAlocate.contains(this.numar))
            this.numar = generareNumarCard();
        numereCardAlocate.add(this.numar);

        //generare CVV
        this.CVV = generareCVV();

        //generare data expirare
        LocalDate data = LocalDate.now().plusYears(3);
        this.dataExpirare = data;
    }

    public Card(int cardId, Cont cont, int CVV, String numar, LocalDate data) {
        this.cardId = cardId;
        this.IBAN = cont.getIBAN();
        this.cont = cont;
        this.numar = generareNumarCard();
        this.numar = numar;
        this.CVV = CVV;
        this.dataExpirare = data;
    }

    public double interogareSold()
    {
        return this.cont.getSold();
    }

    abstract public String toCSV();


    @Override
    public String toString() {
        return  "cardId: " + cardId + "\n" +
                "CVV: " + CVV + "\n" +
                "Numar: " + numar + "\n" +
                "Nume titular: " + cont.getNumeTitular() + "\n" +
                "IBAN: " + IBAN + "\n" +
                "Data expirare: " + dataExpirare + "\n";
    }

    private static String generareNumarCard() {
        Random rand = new Random();
        int[] array = new int[16];
        for (int i = 0; i < array.length; ++i) {
            array[i] = rand.nextInt(10);
        }
        String s = "";
        for (int i = 0; i < array.length; ++i) {
            s = s + array[i];
        }

        return s;
    }
    private static int generareCVV(){
        Random rand = new Random();
        return 100 + rand.nextInt(900);
    }

    public int getCardId() {
        return cardId;
    }

    public String getNumar() {
        return numar;
    }

    public String getNumeTitular() {
        return this.cont.getNumeTitular();
    }

    public int getCVV() {
        return CVV;
    }

    public String getIBAN() {
        return IBAN;
    }

    public LocalDate getDataExpirare() {
        return dataExpirare;
    }

    public int getIdClient() {
        return this.cont.getIdClient();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return cardId == card.cardId && CVV == card.CVV && Double.compare(card.sold, sold) == 0 && numar.equals(card.numar) && IBAN.equals(card.IBAN) && cont.equals(card.cont) && dataExpirare.equals(card.dataExpirare);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardId, CVV, numar, IBAN, sold, cont, dataExpirare);
    }
}