package ar.uba.fi.talker.service;

import ar.uba.fi.talker.model.User;

public class LoginService {

	
	private User u;
	private boolean loggedIn;
	public User getU() {
		return u;
	}
	public void setU(User u) {
		this.u = u;
	}
	public boolean isLoggedIn() {
		return loggedIn;
	}
	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

}
