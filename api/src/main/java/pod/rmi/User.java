package pod.rmi;

import java.io.Serializable;

public class User implements Serializable {
	
	private final String id;
	private final String name;
	
	public User(final String id, final String name) {
		this.id = id;
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "User{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				'}';
	}
}
