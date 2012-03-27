package com.fogstack.simplesettings;

import android.content.Context;
import android.util.Log;

public class SimpleSettings {
	Context ctx;
	
	private SimpleSettings(Context ctx)
	{
		this.ctx = ctx;
	}
	
	public static SimpleSettings getInstance(Context ctx) { return new SimpleSettings(ctx); }
	
	public void save(String name, String value) 
	{
		SimpleSettingsDAO.getInstance(ctx).save(name, value);
	}
	
	public void save(String name, int value) 
	{
		SimpleSettingsDAO.getInstance(ctx).save(name, value);
	}
	
	public void save(String name, boolean value) 
	{
		SimpleSettingsDAO.getInstance(ctx).save(name, Boolean.toString(value));
	}
	
	public String getString(String name) 
	{
		return SimpleSettingsDAO.getInstance(ctx).getString(name);
	}
	
	public int getInt(String name, int defaultValue) 
	{
		try {
			String value = getString(name);
			if(value != null)
			{
				return Integer.parseInt(value);
			}
		} catch (NumberFormatException e) {
			// Setting not found or mis-formatted
			Log.e("SimpleSettings", "Unable to retrieve int setting", e);
		}
		
		return defaultValue;
	}
	
	public boolean getBoolean(String name, boolean defaultValue) 
	{
		try {
			return Boolean.parseBoolean(getString(name));
		} catch (Exception e) {
			// Setting not found or mis-formatted
			Log.e("SimpleSettings", "Unable to retrieve boolean setting", e);
		}
		
		return defaultValue;
	}
}
