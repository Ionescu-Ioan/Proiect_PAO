package exercitii;

public class Subject {

    private Room room;
    private int noOfStudents;
    private Person teacher;

    public Subject(Room room, int noOfStudents, Person teacher){
        this.room = room;
        this.noOfStudents = noOfStudents;
        this.teacher = teacher;
    }

    public Room getSubjectRoom() {
        return room;
    }

    public int getSubjectNoOfStudents() {
        return noOfStudents;
    }

    public Person getSubjectTeacher() {
        return teacher;
    }

    public void setSubjectRoom(Room newRoom) {
        this.room = newRoom;
    }

    public void setSubjectNoOfStudents(int newNoOfStudents) {
        this.noOfStudents = newNoOfStudents;
    }

    public void setSubjectTeacher(Person newTeacher) {
        this.teacher = newTeacher;
    }

    @Override
    public String toString() {
        return "Subject{" + "\n" +
                "room = " + room.toString() + ",\n" +
                "noOfStudents = " + noOfStudents + ",\n" +
                "teacher = " + teacher.toString() + "\n" +
                '}';
    }

}
