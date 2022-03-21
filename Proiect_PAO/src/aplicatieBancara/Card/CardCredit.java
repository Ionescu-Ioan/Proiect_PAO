package aplicatieBancara.Card;

public class CardCredit extends Card{

    private final static String tip = "credit";
    private double sold = 0;
    private double sumaCreditata = 0;

    public CardCredit(int cardId, String IBAN, String numeTitular){
        super(cardId, IBAN, numeTitular);
    }

    public void actualizareSold(double suma)
    {
        sold += suma;
    }

    public void actualizareSumaCreditata(double suma)
    {
        sumaCreditata += suma;
    }

    public double getSold(){
        return sold;
    }

    public double getSumaCreditata(){
        return sumaCreditata;
    }

    public String getTip()
    {
        return tip;
    }
}
