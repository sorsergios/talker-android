package ar.fi.uba.androidtalker.service;

import ar.fi.uba.androidtalker.model.User;

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
