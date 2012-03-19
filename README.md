SimpleSettings
=================
A simple settings framework (backed by SQLite) for use in Android applications. Works with Android 2.2 and later.

Install
-------
Just copy the simplesettings.jar file into your Android project and add it to your build path. 	
	
If you'd like to build it yourself, just load the project in Eclipse with the Android SDK installed and it will auto-generate the JAR file for you (in the /bin dir) since the project is marked as a library.

For compilation outside of Eclipse, you can use the included Ant script.

	ant build.xml

This will build ./simplesettings.jar. Be sure to specify the location of your Android SDK Jar in the build.xml file first.   

Saving settings
--------------------------
	SimpleSettings.getInstance(Context).save(key, value);

Retrieving settings
-----------------------
	SimpleSettings settings = SimpleSettings.getInstance(Context);

	// retrieve a String value
	String value = settings.getString(key);
	
	// retrieve an int value
	int value = settings.getInt(key);

You can 

FAQ:
-------
Why not just use the Preferences framework?

You absolutely should, for anything that you'd like the end use to be able to change. Settings are different than preferences as they store values necessary for the functioning of the application.

COPYRIGHT
---------
Copyright (c) 2012 Fogstack LLC 
Sean Byrnes <sean@fogstack.com>
Released under the MIT license so you can use it for whatever you like. 