package exercitii;

public class Person {

    private String name;
    private String surname;
    private int age;
    private long identityNumber;
    private String type;

    public Person(String name, String surname, int age, long identityNumber, String type){
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.identityNumber = identityNumber;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getAge() {
        return age;
    }

    public long getIdentityNumber() {
        return identityNumber;
    }

    public String getType(){
        return type;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public void setSurname(String newSurname) {
        this.surname = newSurname;
    }

    public void setAge(int newAge) {
        this.age = newAge;
    }

    public void setIdentityNumber(long newIdentityNumber) {
        this.identityNumber = newIdentityNumber;
    }

    public void setType(String newType){
        this.type = newType;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name=" + name +
                ", surname=" + surname +
                ", age=" + age +
                ", identityNumber=" + identityNumber +
                ", type=" + type +
                '}';
    }

}





