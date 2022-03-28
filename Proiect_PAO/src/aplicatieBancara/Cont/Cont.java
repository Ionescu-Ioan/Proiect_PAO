package aplicatieBancara.Cont;

import aplicatieBancara.Banca;
import aplicatieBancara.Card.*;
import aplicatieBancara.TipCard;
import aplicatieBancara.TipTranzactie;
import aplicatieBancara.Tranzactie;
import java.util.*;
import java.util.ArrayList;


public class Cont {

    private String IBAN;
    private final String swift = Banca.getCodSwift();
    private double sold;
    private String numeTitular;
    private int idClient;
    private ArrayList<Card> carduri = new ArrayList<Card>();
    private ArrayList<Tranzactie> tranzactii = new ArrayList<Tranzactie>();

    private final GeneratorCard generatorCard = new GeneratorCard();

    public void adaugaCard(TipCard tip){
        Card cardNou = null;
        switch (tip){
        case DEBIT:
            cardNou = generatorCard.creareCardDebit(this);
            carduri.add(cardNou);
            break;
        case CREDIT:
            cardNou = generatorCard.creareCardCredit(this);
            carduri.add(cardNou);
            break;
        }
    }

    public void adaugaCard(Card card){
        carduri.add(card);
    }

    public void eliminaCard(Card card){
        carduri.remove(card);
    }

    public void adaugaTranzactie(Tranzactie tranzactie) {
        tranzactii.add(tranzactie);
        Collections.sort(tranzactii, new ComparatorTranzactii());
    }


    public Cont(String numeTitular, int idClient){
        this.IBAN = this.generareIBAN(idClient);
        this.sold = 0;
        this.numeTitular = numeTitular;
        this.idClient = idClient;
    }

    private String generareIBAN(int idClient){
        String bank = Banca.getPrefixIBAN();
        Random rand = new Random();
        return bank + idClient + rand.nextInt(100000000, 1000000000);
    }

    public void actualizareSold(double suma){
        sold += suma;
    }

    public String getIBAN() {
        return IBAN;
    }

    public String getSwift() {
        return swift;
    }

    public double interogareSold() {
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

    public ArrayList<Tranzactie> getTranzactii() {
        return tranzactii;
    }

    @Override
    public String toString() {
        return "Cont{" +
                "IBAN='" + IBAN + '\'' +
                ", swift='" + swift + '\'' +
                ", sold=" + sold +
                ", nume titular='" + numeTitular + '\'' +
                ", idClient=" + idClient +
                '}';
    }
}
