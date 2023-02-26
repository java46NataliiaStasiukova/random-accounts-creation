package telran.util;

import java.util.Arrays;

public class DTO {
	
	public String username;
	public String password;
	public String experation;//string in the ISO format of LocalDateTime – YYYY-MM-DDTHH-mm-SS
	public String[] roles;
	
	public DTO(String username, String password, String experation, String[] roles) {

		this.username = username;
		this.password = password;
		this.experation = experation;
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "DTO [userName=" + username + ", password=" + password + ", experation=" + experation + ", roles="
				+ Arrays.toString(roles) + "]";
	}
	

}
