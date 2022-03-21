package aplicatieBancara.Card;

public class GeneratorCard {

    private static int uniqueId = 0;

    public CardDebit creareCardDebit(String IBAN, String numeTitular){
        return new CardDebit(uniqueId++, IBAN, numeTitular);
    }

    public CardCredit creareCardCredit(String IBAN, String numeTitular){
        return new CardCredit(uniqueId++, IBAN, numeTitular);
    }
}
