package exercitii;

public class Area {
    private CandyBag candyBag;
    private int number;
    private String street;

    public Area() {
    }

    public Area(CandyBag candyBag, int number, String street) {
        this.candyBag = candyBag;
        this.number = number;
        this.street = street;
    }

    public void printAddress(){
        System.out.println("Adress: " + "Street " + street + " number " + number + "\n");
        System.out.println("The bag contains:\n");
        for(CandyBox candyBox : candyBag.getBoxes())
        {
            System.out.println(candyBox.toString() + "\n");
        }
    }
}
