package maverick01.task1;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class SplashFragment extends Fragment {
	
	static SplashFragment newInstance() {
		return new SplashFragment();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				assert getFragmentManager() != null;
				getFragmentManager().beginTransaction()
						.replace(R.id.fragment_container, LoginFragment.newInstance())
						.commit();
			}
		}, 2000);
		
		return inflater.inflate(R.layout.fragment_splash, container, false);
	}
}