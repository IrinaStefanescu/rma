package ro.iran.spectacole.model;

public class Room {

	private int roomId;
	private int floor;
	private int capacity;
	private String projector;

	public Room(int roomId, int floor, int capacity, String projector) {
		super();
		this.roomId = roomId;
		this.floor = floor;
		this.capacity = capacity;
		this.projector = projector;
	}

	public Room(int floor, int capacity, String projector) {
		super();
		this.floor = floor;
		this.capacity = capacity;
		this.projector = projector;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String getProjector() {
		return projector;
	}

	public void setProjector(String projector) {
		this.projector = projector;
	}

	@Override
	public String toString() {
		return "Room [roomId=" + roomId + ", floor=" + floor + ", capacity=" + capacity + ", projector=" + projector
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + capacity;
		result = prime * result + floor;
		result = prime * result + ((projector == null) ? 0 : projector.hashCode());
		result = prime * result + roomId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Room other = (Room) obj;
		if (capacity != other.capacity)
			return false;
		if (floor != other.floor)
			return false;
		if (projector == null) {
			if (other.projector != null)
				return false;
		} else if (!projector.equals(other.projector))
			return false;
		if (roomId != other.roomId)
			return false;
		return true;
	}

}