package com.hamba.hambameet;
 
import com.parse.Parse;
import com.parse.ParseACL;
 
import com.parse.ParseUser;
 
import android.app.Application;
 
public class ParseApplication extends Application {
 
	@Override
	public void onCreate() {
		super.onCreate();
 
		// Add your initialization code here
        Parse.initialize(this, "8Os7PVIih2cRDfomi10U64mY07t0sypgNu8RUxWw", "8MWpDTnDLnaVhIjnzhtWSmjv5vIgr2HEc9AIBqN0");
 
		ParseUser.enableAutomaticUser();
		ParseACL defaultACL = new ParseACL();
 
		// If you would like all objects to be private by default, remove this line.
		defaultACL.setPublicReadAccess(true);
 
		ParseACL.setDefaultACL(defaultACL, true);
	}
 
}
