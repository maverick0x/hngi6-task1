package maverick01.task1;

import android.content.SharedPreferences;

public interface Linker {
	
	SharedPreferences getSharedPreferences();
}

interface SendKey{
	void sendData(String key);
	
	String getKey();
}