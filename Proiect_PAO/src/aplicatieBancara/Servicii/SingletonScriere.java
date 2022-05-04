package aplicatieBancara.Servicii;

import aplicatieBancara.Entitati.Card.Card;
import aplicatieBancara.Entitati.Client.Adresa;
import aplicatieBancara.Entitati.Client.Client;
import aplicatieBancara.Entitati.Cont.Cont;
import aplicatieBancara.Entitati.Tranzactie;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class SingletonScriere {

    private static SingletonScriere instanta_singleton;

    private List<Card> carduri = SingletonCitire.getInstance().getCarduri();
    private List<Adresa> adrese = SingletonCitire.getInstance().getAdrese();
    private List<Client> clienti = SingletonCitire.getInstance().getClienti();
    private List<Cont> conturi = SingletonCitire.getInstance().getConturi();
    private List<Tranzactie> tranzactii = SingletonCitire.getInstance().getTranzactii();


    public static SingletonScriere getInstance(){
        if (instanta_singleton == null)
            instanta_singleton = new SingletonScriere();
        return instanta_singleton;
    }

//    public void setCarduri(List<Card> carduri) {
//        this.carduri = carduri;
//    }
//
//    public void setAdrese(List<Adresa> adrese) {
//        this.adrese = adrese;
//    }
//
//
//    public void setClienti(List<Client> clienti) {
//        this.clienti = clienti;
//    }
//
//
//    public void setConturi(List<Cont> conturi) {
//        this.conturi = conturi;
//    }
//
//
//    public void setTranzactii(List<Tranzactie> tranzactii) {
//        this.tranzactii = tranzactii;
//    }

    public <T> void set(List<T> lista)
    {
        if(!lista.isEmpty() && lista.get(0) instanceof Card)
        {
            this.carduri = (List<Card>) lista;
        }
        else if(!lista.isEmpty() && lista.get(0) instanceof Adresa)
        {
            this.adrese = (List<Adresa>) lista;
        }
        else if(!lista.isEmpty() && lista.get(0) instanceof Client)
        {
            this.clienti = (List<Client>) lista;
        }
        else if(!lista.isEmpty() && lista.get(0) instanceof Cont)
        {
            this.conturi = (List<Cont>) lista;
        }
        else if(!lista.isEmpty() && lista.get(0) instanceof Tranzactie)
        {
            this.tranzactii = (List<Tranzactie>) lista;
        }
        else{
            System.out.println("Lista este vida!");
        }
    }

    public void scriereCarduri(){
        try{
            Stream<Card> streamCarduri = carduri.stream();
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

            writer.close();
        }catch (IOException e){
            System.out.println(e.toString());
        }
    }


    public void scriereAdrese(){
        try{
            Stream<Adresa> streamAdrese = adrese.stream();
            var writer = new FileWriter("Data/adresa.csv");
            Consumer<Adresa> consumer = adresa -> {
                try {
                    writer.write(adresa.toCSV());
                    writer.write("," + adresa.getIdClient());
                    writer.write("\n");

                } catch (IOException e) {
                    System.out.println(e.toString());
                }
            };
            streamAdrese.forEach(consumer);
            writer.close();
        }catch (IOException e){
            System.out.println(e.toString());
        }
    }


    public void scriereClienti(){
        try{
            Stream<Client> streamClienti = clienti.stream();
            var writer = new FileWriter("Data/client.csv");
            Consumer<Client> consumer = client -> {
                try {
                    writer.write(client.toCSV());
                    writer.write("\n");

                } catch (IOException e) {
                    System.out.println(e.toString());
                }
            };
            streamClienti.forEach(consumer);
            writer.close();
        }catch (IOException e){
            System.out.println(e.toString());
        }
    }

    public <T> void adaugare(T entitate){
        String fileName = null;
        if(entitate instanceof Card)
        {
            fileName = "Data/card.csv";
            carduri.add((Card) entitate);
        }
        else if(entitate instanceof Adresa)
        {
            fileName = "Data/adresa.csv";
            adrese.add((Adresa) entitate);
        }
        else if(entitate instanceof  Client)
        {
            fileName = "Data/client.csv";
            clienti.add((Client) entitate);
        }
        else if(entitate instanceof  Cont)
        {
            fileName = "Data/cont.csv";
            conturi.add((Cont) entitate);
        }
        else if(entitate instanceof Tranzactie)
        {
            fileName = "Data/tranzactie.csv";
            tranzactii.add((Tranzactie) entitate);
        }

        try(var writer = new FileWriter(fileName, true)) {
            if(entitate instanceof Card)
                writer.write(((Card) entitate).toCSV());
            else if(entitate instanceof Adresa)
            {
                writer.write(((Adresa) entitate).toCSV());
                writer.write("," + ((Adresa) entitate).getIdClient());
            }
            else if(entitate instanceof Client)
                writer.write(((Client) entitate).toCSV());
            else if(entitate instanceof Cont)
                writer.write(((Cont) entitate).toCSV());
            else if(entitate instanceof Tranzactie)
                writer.write(((Tranzactie) entitate).toCSV());
            writer.write("\n");

        } catch (IOException e) {
            System.out.println(e.toString());
        }

    }

    public void scriereConturi(){
        try{
            Stream<Cont> streamConturi = conturi.stream();
            var writer = new FileWriter("Data/cont.csv");
            Consumer<Cont> consumer = cont -> {
                try {
                    writer.write(cont.toCSV());
                    writer.write("\n");

                } catch (IOException e) {
                    System.out.println(e.toString());
                }
            };
            streamConturi.forEach(consumer);
            writer.close();
        }catch (IOException e){
            System.out.println(e.toString());
        }
    }

    public void scriereTranzactii(){
        try{
            Stream<Tranzactie> streamTranzactii = tranzactii.stream();
            var writer = new FileWriter("Data/tranzactie.csv");
            Consumer<Tranzactie> consumer = tranzactie -> {
                try {
                    writer.write(tranzactie.toCSV());
                    writer.write("\n");

                } catch (IOException e) {
                    System.out.println(e.toString());
                }
            };
            streamTranzactii.forEach(consumer);
            writer.close();
        }catch (IOException e){
            System.out.println(e.toString());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SingletonScriere that = (SingletonScriere) o;
        return Objects.equals(carduri, that.carduri) && Objects.equals(adrese, that.adrese) && Objects.equals(clienti, that.clienti) && Objects.equals(conturi, that.conturi) && Objects.equals(tranzactii, that.tranzactii);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carduri, adrese, clienti, conturi, tranzactii);
    }
}
