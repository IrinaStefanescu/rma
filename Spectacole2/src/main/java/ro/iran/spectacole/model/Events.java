package ro.iran.spectacole.model;

public class Events {

	private String name;
	private int roomId;
	private String date;
	private int hour;
	private int min;
	private int duration;
	private int nrPeople;
	
	public Events(String name, int roomId, String date, int hour, int min, int duration, int nrPeople) {
		super();
		this.name = name;
		this.roomId = roomId;
		this.date = date;
		this.hour = hour;
		this.min = min;
		this.duration = duration;
		this.nrPeople = nrPeople;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getNrPeople() {
		return nrPeople;
	}

	public void setNrPeople(int nrPeople) {
		this.nrPeople = nrPeople;
	}

	@Override
	public String toString() {
		return "Event [name=" + name + ", roomId=" + roomId + ", date=" + date + ", hour=" + hour + ", min=" + min
				+ ", duration=" + duration + ", nrPeople=" + nrPeople + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + duration;
		result = prime * result + hour;
		result = prime * result + min;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + nrPeople;
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
		Events other = (Events) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (duration != other.duration)
			return false;
		if (hour != other.hour)
			return false;
		if (min != other.min)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (nrPeople != other.nrPeople)
			return false;
		if (roomId != other.roomId)
			return false;
		return true;
	}
	
}
