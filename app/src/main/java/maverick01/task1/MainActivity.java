package maverick01.task1;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.List;

public class MainActivity extends AppCompatActivity implements Linker, SendKey{
	private static final String PREF_NAME = "Task1";
	
	private SharedPreferences sharedPreferences;
	private String key;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
		
		if (savedInstanceState ==  null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.fragment_container, SplashFragment.newInstance())
					.commitNow();
		}
	}
	
	@Override
	public void onBackPressed() {
		List<Fragment> fragments = getSupportFragmentManager().getFragments();
		for (Fragment fragment : fragments) {
			if (fragment instanceof LoginFragment) finish();
		}
		super.onBackPressed();
	}
	
	@Override
	public SharedPreferences getSharedPreferences() {
		return sharedPreferences;
	}
	
	@Override
	public void sendData(String key) {
		this.key = key;
	}
	
	@Override
	public String getKey() {
		return key;
	}
}
