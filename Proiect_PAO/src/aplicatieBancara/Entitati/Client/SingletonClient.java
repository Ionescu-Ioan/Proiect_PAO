package aplicatieBancara.Entitati.Client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class SingletonClient {


    private static SingletonClient instanta_singleton;

    private List<Client> clienti = new ArrayList<>();


    public static SingletonClient getInstance(){
        if (instanta_singleton == null)
            instanta_singleton = new SingletonClient();
        return instanta_singleton;
    }

    public void setClienti(List<Client> clienti) {
        this.clienti = clienti;
    }

    public List<Client> getClienti() {
        return clienti;
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
            System.out.println("Nu exista clienti salvate!");
        }
        return matrice;
    }

    public void construireClienti(List<Adresa> listaAdrese){
        try {
            var matrice = SingletonClient.getCSVStrings("Data/client.csv");
            for (var element : matrice) {
                Adresa adresa = null;
                int idClient = Integer.parseInt(element[0]);
                for(var adr : listaAdrese)
                {
                   if(adr.getIdClient() == idClient)
                       adresa = adr;
                }
                Client clientNou = new Client(
                        idClient,
                        element[1],
                        element[2],
                        element[3],
                        LocalDate.parse(element[4]),
                        element[5],
                        element[6],
                        adresa
                );
                clienti.add(clientNou);
            }
        }catch (NumberFormatException  e){
            System.out.println(e.toString());
        }
    }

    public void adaugareClient(Client client)
    {
        clienti.add(client);
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
//            for(var client : this.clienti){
//                writer.write(client.toCSV());
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
        SingletonClient that = (SingletonClient) o;
        return Objects.equals(getClienti(), that.getClienti());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClienti());
    }
}
