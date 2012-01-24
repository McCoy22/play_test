package controllers;

import java.util.Calendar;
import java.util.Date;

import play.Logger;
import models.User;

public class Security extends Secure.Security {
	
	static boolean authenticate(String username, String password) {
	    return User.connect(username, password) != null;
	}
	
	static void onDisconnected() {
	    Application.index();
	}
	static void onAuthenticated() {
		Logger.info("Benutzer '%s' eingeloggt am '%2$td.%2$tm.%2$tY' um '%2$tT'", Security.connected(), Calendar.getInstance());
	    Admin.index();
	}
	
	static boolean check(String profile) {
	    if("admin".equals(profile)) {
	        return User.find("byEmail", connected()).<User>first().isAdmin;
	    }
	    return false;
	}
}
