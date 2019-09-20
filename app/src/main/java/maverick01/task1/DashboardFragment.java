package maverick01.task1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

public class DashboardFragment extends Fragment {
	private SharedPreferences sharedPreferences;
	
	private Linker linker;
	private SendKey sendKey;
	
	static DashboardFragment newInstance() {
		return new DashboardFragment();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		sharedPreferences = linker.getSharedPreferences();
		setRetainInstance(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_dashboard, container, false);
	}
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		TextView nameText = view.findViewById(R.id.dbd_name);
		TextView emailText = view.findViewById(R.id.dbd_email);
		TextView phoneText = view.findViewById(R.id.dbd_mobile);
		TextView password = view.findViewById(R.id.dbd_password);
		
		String key = sendKey.getKey();
		nameText.setText(sharedPreferences.getString(key + "NAM", ""));
		emailText.setText(sharedPreferences.getString(key + "EML", ""));
		phoneText.setText(sharedPreferences.getString(key + "PHN", ""));
		int passwordLength = sharedPreferences.getString(key + "PWD", "").length();
		StringBuilder passwordText = new StringBuilder();
		for (int i = 0; i < passwordLength; i++) passwordText.append("*");
		password.setText(passwordText.toString());
		
		Button logOutButton = view.findViewById(R.id.dbd_logout);
		logOutButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				assert getFragmentManager() != null;
				List<Fragment> fragments = getFragmentManager().getFragments();
				for (Fragment fragment : fragments) {
					if (fragment instanceof LoginFragment) {
						getFragmentManager().beginTransaction()
								.remove(fragment)
								.commit();
					}
				}
				getFragmentManager().beginTransaction()
						.replace(R.id.fragment_container, LoginFragment.newInstance())
						.commit();
			}
		});
	}
	
	@Override
	public void onAttach(@NonNull Context context) {
		super.onAttach(context);
		linker = (MainActivity) getActivity();
		sendKey = (MainActivity) getActivity();
	}
}
