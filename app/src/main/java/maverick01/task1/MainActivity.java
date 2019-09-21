package maverick01.task1;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.List;

public class MainActivity extends AppCompatActivity implements Linker {
	private static final String PREF_NAME = "Task1";
	
	private String key;
	private LoginFragment loginFragment;
	private RegisterFragment registerFragment;
	private DashboardFragment dashboardFragment;
	private SharedPreferences sharedPreferences;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		key = "";
		sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
		loginFragment = LoginFragment.newInstance();
		registerFragment = RegisterFragment.newInstance();
		dashboardFragment = DashboardFragment.newInstance();
		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.fragment_container, SplashFragment.newInstance())
					.commitNow();
		}
	}
	
	@Override
	public void onBackPressed() {
		List<Fragment> fragments = getSupportFragmentManager().getFragments();
		for (Fragment fragment : fragments) {
			if (fragment instanceof LoginFragment) finish();
			else super.onBackPressed();
		}
	}
	
	@Override
	public String getKey() {
		return key;
	}
	
	@Override
	public void sendData(String key) {
		this.key = key;
	}
	
	@Override
	public SharedPreferences getSharedPreferences() {
		return sharedPreferences;
	}
	
	@Override
	public void switchFragment(String tag) {
		switch (tag) {
			case LoginFragment.TAG:
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.fragment_container, loginFragment)
						.commit();
				break;
				
			case RegisterFragment.TAG:
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.fragment_container, registerFragment)
						.addToBackStack(null)
						.commit();
				break;
				
			case DashboardFragment.TAG:
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.fragment_container, dashboardFragment)
						.addToBackStack(null)
						.commit();
				break;
		}
	}
}
