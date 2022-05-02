package aplicatieBancara.Entitati.Cont;

import aplicatieBancara.Entitati.Banca;
import aplicatieBancara.Entitati.Client.Adresa;
import aplicatieBancara.Entitati.Client.Client;
import aplicatieBancara.Entitati.Client.SingletonClient;

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

public class SingletonCont {

    private static SingletonCont instanta_singleton;

    private List<Cont> conturi = new ArrayList<>();

    public static SingletonCont getInstance(){
        if (instanta_singleton == null)
            instanta_singleton = new SingletonCont();
        return instanta_singleton;
    }

    public void setConturi(List<Cont> conturi) {
        this.conturi = conturi;
    }

    public List<Cont> getConturi() {
        return conturi;
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
            System.out.println("Nu exista conturi salvate!");
        }
        return matrice;
    }

    public void construireConturi(){
        try {
            var matrice = SingletonCont.getCSVStrings("Data/cont.csv");
            for (var element : matrice) {
                String numeTitular = element[1] + " " + element[2];
                Cont contNou = new Cont(
                        element[3],
                        numeTitular,
                        Integer.parseInt(element[0]),
                        Banca.getBanca()
                );
                contNou.actualizareSold(Double.parseDouble(element[4]));
                conturi.add(contNou);
            }
        }catch (NumberFormatException  e){
            System.out.println(e.toString());
        }
    }


    public void adaugareCont(Cont cont)
    {
        conturi.add(cont);
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
//            for(var cont : this.conturi){
//                writer.write(cont.toCSV());
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
        SingletonCont that = (SingletonCont) o;
        return Objects.equals(getConturi(), that.getConturi());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getConturi());
    }
}
