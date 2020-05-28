package ro.iran.spectacole.model;

public class Invites {
	private Events event;
	private Users sender;
	private Users receiver;
	public Invites(Events event, Users sender, Users receiver) {
		super();
		this.event = event;
		this.sender = sender;
		this.receiver = receiver;
	}
	public Events getEvent() {
		return event;
	}
	public void setEvent(Events event) {
		this.event = event;
	}
	public Users getSender() {
		return sender;
	}
	public void setSender(Users sender) {
		this.sender = sender;
	}
	public Users getReceiver() {
		return receiver;
	}
	public void setReceiver(Users receiver) {
		this.receiver = receiver;
	}
	@Override
	public String toString() {
		return "Invites [event=" + event + ", sender=" + sender + ", receiver=" + receiver + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((event == null) ? 0 : event.hashCode());
		result = prime * result + ((receiver == null) ? 0 : receiver.hashCode());
		result = prime * result + ((sender == null) ? 0 : sender.hashCode());
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
		Invites other = (Invites) obj;
		if (event == null) {
			if (other.event != null)
				return false;
		} else if (!event.equals(other.event))
			return false;
		if (receiver == null) {
			if (other.receiver != null)
				return false;
		} else if (!receiver.equals(other.receiver))
			return false;
		if (sender == null) {
			if (other.sender != null)
				return false;
		} else if (!sender.equals(other.sender))
			return false;
		return true;
	}
	
	
	
}
