package br.edu.ifsul.primeiraapp.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.edu.ifsul.primeiraapp.R;
import br.edu.ifsul.primeiraapp.setup.AppSetup;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "loginActivity" ;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mAuth = FirebaseAuth.getInstance();

        final EditText etEmail = findViewById(R.id.etEmailLogin);
        final EditText etSenha = findViewById(R.id.etSenhaLogin);

        Button btLogin = findViewById(R.id.btLogin);
        Button btCadastro = findViewById(R.id.btCadastro);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signIn(etEmail.getText().toString(), etSenha.getText().toString());
            }
        });



        btCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signUp(etEmail.getText().toString(), etSenha.getText().toString());
            }
        });




    }

    private void signIn(String email, String senha){
        Log.d(TAG, email+senha);
        mAuth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            Toast.makeText(LoginActivity.this, "Usuario logado com sucesso.", Toast.LENGTH_SHORT).show();

                            AppSetup.user = mAuth.getCurrentUser();
                            startActivity(new Intent(LoginActivity.this, ClienteActivity.class));
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Login não foi possivel",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });

    }




    private void signUp(String email, String senha){

        mAuth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            Toast.makeText(LoginActivity.this, "Certíssimo, ta bombando.",
                                    Toast.LENGTH_SHORT).show();

                            AppSetup.user = mAuth.getCurrentUser();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }



                        // ...
                    }
                });


    }
}
