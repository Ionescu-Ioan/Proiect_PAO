package aplicatieBancara.Card;

public class CardDebit extends Card{

    private final static String tip = "debit";
    private double sold = 0;

    public CardDebit(int cardId, String IBAN, String numeTitular){
        super(cardId, IBAN, numeTitular);
    }

    public void actualizareSold(double suma)
    {
        sold += suma;
    }

    public double getSold(){
        return sold;
    }

    public String getTip()
    {
        return tip;
    }
}
