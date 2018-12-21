package com.prabhat.brothers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //declaring views
    private FirebaseAuth firebaseAuth;

    private EditText editTextEmail, editTextPassword;
    private TextView forgotPassword, createAccount;
    private Button loginButton;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //assigning views
        firebaseAuth = FirebaseAuth.getInstance();

        editTextEmail = (EditText) findViewById(R.id.registered_email);
        editTextPassword = (EditText) findViewById(R.id.password);
        forgotPassword = (TextView) findViewById(R.id.forgot_password);
        loginButton = (Button) findViewById(R.id.login_button);
        createAccount = (TextView) findViewById(R.id.create_account);

        progressDialog = new ProgressDialog(this);

        //forgot editTextPassword text clicked
        forgotPassword.setOnClickListener(this);

        //loginButton clicked
        loginButton.setOnClickListener(this);

        //create account is clicked
        createAccount.setOnClickListener(this);
    }

    private void goToRegisterActivity() {

        startActivity(new Intent(this, MainActivity.class));
        finish();

    }

    private void loginUser() {

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty()){
            editTextEmail.setError("Email is Required");
            editTextEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Enter a valid email");
            editTextEmail.requestFocus();
            return;
        }
        if (password.isEmpty()){
            editTextPassword.setError("Enter your password");
            editTextPassword.requestFocus();
            return;
        }
        if (password.length() < 6){
            editTextPassword.setError("Password length should be minimum 6 letters");
            editTextPassword.requestFocus();
            return;
        }

        progressDialog.setMessage("Logging In...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){

                            goToHomepage();

                        }
                        else{

                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            editTextPassword.setText("");

                        }

                    }
                });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), homeActivity.class));
        }
    }

    private void goToHomepage() {

        progressDialog.dismiss();
        startActivity(new Intent(this, homeActivity.class));
        finish();

    }

    private void resetPassword() {

        Toast.makeText(LoginActivity.this, "Password Resetting is Disabled Currently", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.login_button:
                loginUser();
                break;

            case R.id.create_account:
                goToRegisterActivity();
                break;

            case R.id.forgot_password:
                resetPassword();
                break;

            default:
                break;

        }

    }
}
