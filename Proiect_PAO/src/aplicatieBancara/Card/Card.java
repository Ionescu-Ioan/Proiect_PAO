package aplicatieBancara.Card;

import aplicatieBancara.Cont.Cont;
import aplicatieBancara.TipCard;

import java.nio.charset.StandardCharsets;
import java.util.*;


public abstract class Card {

    protected final int cardId;
    protected final int CVV;
    protected String numar;
    protected String IBAN;
    protected double sold;
    protected Cont cont;
    protected final Date dataExpirare;
    //protected String PIN;


    static private final HashSet<String> usedNumbers = new HashSet<String>();

    abstract public String getTip();

    public Card(int cardId, Cont cont) {
        this.cardId = cardId;
        this.IBAN = cont.getIBAN();
        this.cont = cont;
        this.numar = this.generareNumarCard();

        //generare numar card
        while(usedNumbers.contains(this.numar))
            this.numar = this.generareNumarCard();
        usedNumbers.add(this.numar);

        //generare CVV
        this.CVV = this.generareCVV();

        //generare data expirare
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR, 3);
        this.dataExpirare = c.getTime();
    }


    public double interogareSold()
    {
        return this.cont.interogareSold();
    }

    @Override
    public String toString() {
        return  "cardId: " + cardId + "\n" +
                "CVV: " + CVV + "\n" +
                "Numar: " + numar + "\n" +
                "Nume titular: " + this.cont.getNumeTitular() + "\n" +
                "IBAN: " + IBAN + "\n" +
                "Data expirare: " + dataExpirare + "\n";
    }

    private String generareNumarCard() {
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
    private int generareCVV(){
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

    public Date getDataExpirare() {
        return dataExpirare;
    }

    public int getIdClient() {
        return this.cont.getIdClient();
    }
}