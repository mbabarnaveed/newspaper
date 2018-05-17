/**
 * 
 */
package com.org.news.sharestorage;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

/**
 *
 */
public class Preference {

	private static final String TAG = Preference.class.getCanonicalName();
	private String PrefName = "NewsApp";
	
	private Context context=null;
	private SharedPreferences pref=null;
	
	public Preference(final Context pContext) {
		this.context = pContext;
		pref = context.getSharedPreferences(PrefName, Context.MODE_PRIVATE);
	}
	
	
	public void clearPreference(){
		try {
			pref.edit().clear().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 
	 * @param pRef
	 * @param value
	 */
	
	
	
	public void saveStringPreference(final String pRef, final String value){
		
		try {
			  Editor editor = pref.edit();
			  editor.putString(pRef, value); 
			  editor.commit();
		} catch (Exception e) {
			Log.e(TAG, "" + e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @param pRef
	 * @param defaultValue
	 * @return String
	 */
	public String getStringPreference(final String pRef, final String defaultValue){
		return pref.getString(pRef, defaultValue);  
	}
	
	
	
	
	/**
	 * 
	 * @param pRef
	 * @param value
	 */
	public void saveIntegerPreference(final String pRef, final int value){
		
		try {
			  Editor editor = pref.edit();
			  editor.putInt(pRef, value); 
			  editor.commit();
		} catch (Exception e) {
			Log.e(TAG, "" + e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @param pRef
	 * @param defaultValue
	 * @return String
	 */
	public int getIntegerPreference(final String pRef, final int defaultValue){
		return  pref.getInt(pRef, defaultValue);  
	}


	/**
	 *
	 * @param pRef
	 * @param value
	 */
	public void saveIntegerLongPreference(final String pRef, final long value){

		try {
			Editor editor = pref.edit();
			editor.putLong(pRef, value);
			editor.commit();
		} catch (Exception e) {
			Log.e(TAG, "" + e.getMessage());
		}
	}


	/**
	 *
	 * @param pRef
	 * @param defaultValue
	 * @return String
	 */
	public long getLongPreference(final String pRef, final long defaultValue){
		return  pref.getLong(pRef, defaultValue);
	}

	/**
	 *
	 * @param pRef
	 * @param value
	 */
	public void saveBoolPreference(final String pRef, final boolean value){

		try {
			Editor editor = pref.edit();
			editor.putBoolean(pRef, value);
			editor.commit();
		} catch (Exception e) {
			Log.e(TAG, "" + e.getMessage());
		}
	}

	/**
	 *
	 * @param pRef
	 * @param defaultValue
	 * @return String
	 */
	public boolean getBoolPreference(final String pRef, final boolean defaultValue){
		return  pref.getBoolean(pRef, defaultValue);
	}
}
