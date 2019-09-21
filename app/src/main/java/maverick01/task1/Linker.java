package maverick01.task1;

import android.content.SharedPreferences;

public interface Linker {
	String getKey();
	
	void sendData(String key);
	
	SharedPreferences getSharedPreferences();
	
	void switchFragment(String tag);
}