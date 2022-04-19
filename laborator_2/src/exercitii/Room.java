package exercitii;

public class Room {

    private int roomNumber;
    private String roomType;
    private int roomFloor;

    public Room(int roomNumber, String roomType, int roomFloor){
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.roomFloor = roomFloor;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getRoomFloor() {
        return roomFloor;
    }

    public void setRoomNumber(int newRoomNumber) {
        this.roomNumber = newRoomNumber;
    }

    public void setRoomType(String newRoomType) {
        this.roomType = newRoomType;
    }

    public void setRoomFloor(int newRoomFloor) {
        this.roomFloor = newRoomFloor;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomNumber=" + roomNumber +
                ", roomType=" + roomType +
                ", roomFloor=" + roomFloor +
                '}';
    }
}
