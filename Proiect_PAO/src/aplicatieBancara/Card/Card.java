package aplicatieBancara.Card;

import java.nio.charset.StandardCharsets;
import java.util.*;

public abstract class Card {

    private final int cardId;
    private final int CVV;
    private String numar;
    private String numeTitular;
    private String IBAN;
    private final Date dataExpirare;


    static private final HashSet<String> usedNumbers = new HashSet<String>();

    abstract public String getTip();

    public Card(int cardId, String IBAN, String numeTitular) {
        this.cardId = cardId;
        this.IBAN = IBAN;
        this.numeTitular = numeTitular;
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


    @Override
    public String toString() {
        return  "cardId: " + cardId + "\n" +
                "CVV: " + CVV + "\n" +
                "Numar: " + numar + "\n" +
                "Nume titular: " + numeTitular + "\n" +
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
        return numeTitular;
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
}