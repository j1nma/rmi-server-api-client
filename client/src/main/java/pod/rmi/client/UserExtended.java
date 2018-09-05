package pod.rmi.client;

import pod.rmi.User;

public class UserExtended extends User {
	
	private final int edad;
	private final int documento;
	
	public UserExtended(String id, String name, int edad, int documento) {
		super(id, name);
		this.edad = edad;
		this.documento = documento;
	}
	
	@Override
	public String toString() {
		return "UserExtended{" +
				"edad=" + edad +
				", documento=" + documento +
				'}';
	}
}
