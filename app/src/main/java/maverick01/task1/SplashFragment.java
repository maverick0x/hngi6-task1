package maverick01.task1;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class SplashFragment extends Fragment {
	private Linker linker;
	
	static SplashFragment newInstance() {
		return new SplashFragment();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				linker.switchFragment(LoginFragment.TAG);
			}
		}, 2000);
		
		return inflater.inflate(R.layout.fragment_splash, container, false);
	}
	
	@Override
	public void onAttach(@NonNull Context context) {
		super.onAttach(context);
		linker = (MainActivity) getActivity();
	}
}