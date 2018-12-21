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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //declaring views as variables

    private EditText newEmail, newPassword;
    private Button registerButton;
    private TextView signIn;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //assigning views

        firebaseAuth = FirebaseAuth.getInstance();

        newEmail = (EditText) findViewById(R.id.new_email);
        newPassword = (EditText) findViewById(R.id.new_password);
        registerButton = (Button) findViewById(R.id.register_button);
        signIn = (TextView) findViewById(R.id.already_registered);
        progressDialog = new ProgressDialog(this);

        //already registered text onclick event
        signIn.setOnClickListener(this);

        //register button onclick event
        registerButton.setOnClickListener(this);

    }

    private void registerUser() {

        String email = newEmail.getText().toString().trim();
        String password = newPassword.getText().toString().trim();

        //checking if email field is empty
        if (email.isEmpty()) {
            //will do this
            newEmail.setError("Email is Required");
            newEmail.requestFocus();
            return;
        }

        //checking if email is valid
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

            newEmail.setError("Please enter a valid email");
            newEmail.requestFocus();
            return;
        }

        //checking if password field is empty
        if (password.isEmpty()) {
            //will do this
            newPassword.setError("Password is Required");
            newPassword.requestFocus();
            return;
        }

        //checking if the password is valid
        if(password.length() < 6){

            newPassword.setError("Password length should be minimum 6 letters");
            newPassword.requestFocus();
            return;

        }

        //if entries are valid
        //showing a progress dialog

        progressDialog.setMessage("Wait! We are registering you...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            //user is successfully registered
                            Toast.makeText(MainActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
                            DatabaseReference mRef = mDatabase.getReference("users");
                            DatabaseReference reference = mDatabase.getReference();

                            Users user  = new Users();
                            String userEmail = firebaseUser.getEmail();
                            String userId = firebaseUser.getUid();

                            user.setUserEmail(userEmail);

                            mRef.child(userId).setValue(user);
                            reference.child(userId);

                            goToEditProfilePage();

                        } else {

                            if (task.getException() instanceof FirebaseAuthUserCollisionException){

                                Toast.makeText(MainActivity.this, "Email is already regitered.", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(MainActivity.this, "Unable to register, Please try again", Toast.LENGTH_SHORT).show();
                            }

                            progressDialog.dismiss();
                        }

                    }
                });


    }

    private void goToEditProfilePage() {

        startActivity(new Intent(this, ProfileEditActivity.class));
        finish();

    }

    private void goToLoginPage() {

        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void onClick(View view) {

        //checking which button is clicked

        //performing the task according to the clicked button
        switch (view.getId()) {

            case R.id.register_button:
                registerUser();
                break;

            case R.id.already_registered:
                goToLoginPage();
                break;

            default:
                break;

        }

    }

}
