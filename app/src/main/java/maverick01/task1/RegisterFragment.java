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

public class RegisterFragment extends Fragment {
	private TextInputEditText nameText;
	private TextInputEditText emailText;
	private TextInputEditText phoneText;
	private TextInputEditText passwordText;
	private TextInputEditText cPasswordText;
	
	private SharedPreferences sharedPreferences;
	
	private SendKey sendKey;
	private Linker linker;
	
	static RegisterFragment newInstance() {
		return new RegisterFragment();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		sharedPreferences = linker.getSharedPreferences();
		setRetainInstance(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_register, container, false);
	}
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		nameText = view.findViewById(R.id.reg_name);
		emailText = view.findViewById(R.id.reg_email);
		phoneText = view.findViewById(R.id.reg_mobile);
		passwordText = view.findViewById(R.id.reg_password);
		cPasswordText = view.findViewById(R.id.reg_cpassword);
		
		Button registerButton = view.findViewById(R.id.reg_register);
		registerButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (validateAndRegister()) {
					assert getFragmentManager() != null;
					sendKey.sendData(emailText.getText().toString());
					getFragmentManager().beginTransaction()
							.replace(R.id.fragment_container, DashboardFragment.newInstance())
							.commit();
				}
			}
		});
	}
	
	private boolean validateAndRegister() {
		String name = nameText.getText().toString();
		String email = emailText.getText().toString();
		String phone = phoneText.getText().toString();
		String password = passwordText.getText().toString();
		String cpassword = cPasswordText.getText().toString();
		if (name.length() != 0 && email.length() != 0 && phone.length() != 0 && password.length() != 0 &&
				cpassword.length() != 0) {
			if (password.equals(cpassword)) {
				if (!email.equals(sharedPreferences.getString(email + "EML", ""))) {
					SharedPreferences.Editor editor = sharedPreferences.edit();
					editor.putString(email + "NAM", name)
							.putString(email + "EML", email)
							.putString(email + "PWD", password)
							.putString(email + "PHN", phone)
							.apply();
					
					return true;
				} else {
					AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
					builder.setIcon(R.drawable.error_24dp);
					builder.setMessage("Email is already used...!\nLogin Instead");
					builder.setTitle("Error");
					builder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							
							assert getFragmentManager() != null;
							getFragmentManager().beginTransaction()
									.replace(R.id.fragment_container, LoginFragment.newInstance())
									.commit();
						}
					});
					AlertDialog dialog = builder.create();
					dialog.show();
				}
			} else {
				AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
				builder.setMessage("Passwords do not match...!");
				builder.setTitle("Error");
				builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
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
			builder.setTitle("Please Fill");
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
	
	@Override
	public void onAttach(@NonNull Context context) {
		super.onAttach(context);
		linker = (MainActivity) getActivity();
		sendKey = (MainActivity) getActivity();
	}
}
