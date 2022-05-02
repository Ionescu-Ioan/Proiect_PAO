package aplicatieBancara.Entitati.Card;

import aplicatieBancara.Entitati.Banca;
import aplicatieBancara.Entitati.Cont.Cont;
import aplicatieBancara.Entitati.Cont.SingletonCont;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class SingletonCard {

    private static SingletonCard instanta_singleton;

    private List<Card> carduri = new ArrayList<>();
    private Stream<Card> streamCarduri = carduri.stream();

    public static SingletonCard getInstance(){
        if (instanta_singleton == null)
            instanta_singleton = new SingletonCard();
        return instanta_singleton;
    }

    public void setCarduri(List<Card> carduri) {
        this.carduri = carduri;
    }

    public List<Card> getCarduri() {
        return carduri;
    }

    private static List<String[]> getCSVStrings(String fileName){
        List<String[]> matrice = new ArrayList<>();

        try(var in = new BufferedReader(new FileReader(fileName))){
            String linie;
            while((linie = in.readLine()) != null ) {
                String[] campuri = linie.replaceAll(" ", "").split(",");
                matrice.add(campuri);
            }

        }catch (IOException e) {
            System.out.println("Nu exista carduri salvate!");
        }
        return matrice;
    }


    public void construireCarduri(List<Cont> listaConturi){
        try {
            var matrice = SingletonCard.getCSVStrings("Data/card.csv");
            for (var element : matrice) {
                Cont cont = null;
                for(var cc : listaConturi)
                {
                    if(element[1].equals(cc.getIBAN()))
                        cont = cc;

                }
                if(element[2].equals("DEBIT"))
                {
                    CardDebit cardNou = new CardDebit(
                            Integer.parseInt(element[0]),
                            cont
                        );
                    carduri.add(cardNou);
                }
                else if(element[2].equals("CREDIT"))
                {
                    CardCredit cardNou = new CardCredit(
                            Integer.parseInt(element[0]),
                            cont
                    );
                    cardNou.actualizareSumaCreditata(Double.parseDouble(element[3]));
                    carduri.add(cardNou);

                }
            }
        }catch (NumberFormatException  e){
            System.out.println(e.toString());
        }
    }

    public void adaugareCard(Card card)
    {
        carduri.add(card);
    }

    public void scriereCarduri(){
        try{
            var writer = new FileWriter("Data/card.csv");
            Consumer<Card> consumer = card -> {
                try {
                    writer.write(card.toCSV());
                    writer.write("\n");

                } catch (IOException e) {
                    System.out.println(e.toString());
                }
            };
            streamCarduri.forEach(consumer);

//            for(var card : this.carduri){
//                writer.write(card.toCSV());
//                writer.write("\n");
//            }
            writer.close();
            }catch (IOException e){
                System.out.println(e.toString());
            }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SingletonCard that = (SingletonCard) o;
        return Objects.equals(getCarduri(), that.getCarduri());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCarduri());
    }
}
