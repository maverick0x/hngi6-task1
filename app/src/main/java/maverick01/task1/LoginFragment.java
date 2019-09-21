package maverick01.task1;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

public class LoginFragment extends Fragment {
	static final String TAG = "LOGIN";
	
	private TextInputEditText emailText;
	private TextInputEditText passwordText;
	private SharedPreferences sharedPreferences;
	private Linker linker;
	
	static LoginFragment newInstance() {
		return new LoginFragment();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		sharedPreferences = linker.getSharedPreferences();
		setRetainInstance(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_login, container, false);
	}
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		emailText = view.findViewById(R.id.log_email);
		passwordText = view.findViewById(R.id.log_password);
		
		Button loginButton = view.findViewById(R.id.log_login);
		loginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (validateLogin()) {
					linker.sendData(emailText.getText().toString());
					linker.switchFragment(DashboardFragment.TAG);
					clearField();
				}
			}
		});
		
		Button registerButton = view.findViewById(R.id.log_register);
		registerButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				linker.switchFragment(RegisterFragment.TAG);
				clearField();
			}
		});
		
	}
	
	private boolean validateLogin() {
		if (emailText.getText().toString().length() != 0 && passwordText.getText().toString().length() != 0) {
			String email = emailText.getText().toString();
			String password = passwordText.getText().toString();
			
			String storedPassword = sharedPreferences.getString(email + "PWD", "");
			
			if (password.equals(storedPassword))
				return true;
			else if (!email.equals(sharedPreferences.getString(email + "EML", ""))) {
				AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
				builder.setIcon(R.drawable.error_24dp);
				builder.setMessage("User does not exist...");
				builder.setTitle("Error");
				builder.setPositiveButton("Sign up", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						linker.switchFragment(RegisterFragment.TAG);
						clearField();
					}
				});
				AlertDialog dialog = builder.create();
				dialog.show();
			} else {
				AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
				builder.setIcon(R.drawable.error_24dp);
				builder.setMessage("Invalid Credentials...\n\nRecheck!");
				builder.setTitle("Error");
				builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				AlertDialog dialog = builder.create();
				dialog.show();
			}
		} else {
			AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
			builder.setMessage("Please enter the required fields...!");
			builder.setIcon(R.drawable.error_24dp);
			builder.setTitle("All fields required");
			builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			AlertDialog dialog = builder.create();
			dialog.show();
		}
		return false;
	}
	
	private void clearField() {
		emailText.setText("");
		passwordText.setText("");
	}
	
	@Override
	public void onAttach(@NonNull Context context) {
		super.onAttach(context);
		linker = (MainActivity) getActivity();
	}
}