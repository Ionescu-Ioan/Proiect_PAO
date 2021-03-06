package aplicatieBancara.Servicii;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Audit {

    private static Audit instanta_singleton;

    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private List<String> istoric = new ArrayList<>();

    public static Audit getInstance(){
        if (instanta_singleton == null)
            instanta_singleton = new Audit();
        return instanta_singleton;
    }



    private static List<String> getCSVStrings(String fileName){
        List<String> linii = new ArrayList<>();
        try(var in = new BufferedReader(new FileReader(fileName))){
            String linie;
            while((linie = in.readLine()) != null ) {
                linii.add(linie);
            }

        }catch (IOException e) {
            System.out.println("Nu exista istoric pentru actiuni!");
        }
        return linii;
    }

    public void construireIstoric() {
        var linii = Audit.getCSVStrings("Data/audit.csv");
        istoric.addAll(linii);
    }

    public void adaugareActiune(String actiune){
        istoric.add(actiune + "," + formatter.format(LocalDateTime.now()));

        try(var writer = new FileWriter("Data/audit.csv", true)) {
            writer.write(actiune + "," + formatter.format(LocalDateTime.now()));
            writer.write("\n");

        } catch (IOException e) {
            System.out.println(e.toString());
        }

    }

    public void scriereAudit()
    {
        Stream<String> streamIstoric = istoric.stream();
        try{
            var writer = new FileWriter("Data/audit.csv");
            Consumer<String> consumer = actiune -> {
                try {
                    writer.write(actiune);
                    writer.write("\n");

                } catch (IOException e) {
                    System.out.println(e.toString());
                }
            };
            streamIstoric.forEach(consumer);
            writer.close();
        }catch (IOException e){
            System.out.println(e.toString());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Audit audit = (Audit) o;
        return Objects.equals(formatter, audit.formatter) && Objects.equals(istoric, audit.istoric);
    }

    @Override
    public int hashCode() {
        return Objects.hash(formatter, istoric);
    }
}
