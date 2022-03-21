package aplicatieBancara.Cont;

import aplicatieBancara.Banca;
import aplicatieBancara.Card.*;

import java.util.ArrayList;


public class Cont {

    private String IBAN;
    private final String swift = Banca.getCodSwift();
    private double sold;
    private String numeTitular;
    private int idClient;

    private ArrayList<Card> carduri = new ArrayList<Card>();

    private final GeneratorCard generatorCard = new GeneratorCard();

    public void adaugaCard(String numeTitular, String tip){
        if(tip == "debit")
        {
            Card cardNou = generatorCard.creareCardDebit(IBAN, numeTitular);
            carduri.add(cardNou);
        }
        else if(tip == "credit")
        {
            Card cardNou = generatorCard.creareCardCredit(IBAN, numeTitular);
            carduri.add(cardNou);
        }
    }

    public void eliminaCard(String numeTitular, String tip, String numarCard){
        for(var card : carduri)
        {
            if(numeTitular == card.getNumeTitular() && numarCard == card.getNumar() && tip == card.getTip())
                carduri.remove(card);
        }

    }

    public Cont(String IBAN, String numeTitular, int idClient){
        this.IBAN = IBAN;
        this.sold = 0;
        this.numeTitular = numeTitular;
        this.idClient = idClient;
    }

    public Cont(String numeTitular, int idClient, int idUnic){
        this.IBAN = this.generareIBAN(idUnic);
        this.sold = 0;
        this.numeTitular = numeTitular;
        this.idClient = idClient;
    }

    private String generareIBAN(int idUnic){
        String bank = "BTRLRONCRT";

        return "RO05" + bank + idUnic;
    }

    protected void actualizareSold(double suma){
        sold += suma;
    }

    public String getIBAN() {
        return IBAN;
    }

    public String getSwift() {
        return swift;
    }

    public double getSold() {
        return sold;
    }

    public String getNumeTitular() {
        return numeTitular;
    }

    public void setNumeTitular(String numeTitular) {
        this.numeTitular = numeTitular;
    }

    public int getIdClient() {
        return idClient;
    }

    public ArrayList<Card> getCarduri() {
        return carduri;
    }

}
