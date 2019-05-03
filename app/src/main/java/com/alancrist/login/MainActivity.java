package com.alancrist.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText login, password;
    private Button validar;
    private CheckBox lembrar;
    private int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = (EditText) findViewById(R.id.editLogin);
        password = (EditText) findViewById(R.id.editPass);
        lembrar = (CheckBox) findViewById(R.id.checkLembrar);


            lembrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences preferences = getSharedPreferences("preferencias",
                            Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = preferences.edit();

                    if(lembrar.isChecked()) {
                        edit.putBoolean("check", true);
                        edit.putString("login", login.getText().toString());
                        edit.putString("senha", password.getText().toString());


                        edit.apply();
                        Toast toast = Toast.makeText(getBaseContext(), "Dados salvos com sucesso",
                                Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        edit.remove("login");
                        edit.remove("senha");
                        edit.putBoolean("check", false);

                        edit.apply();
                        Toast toast = Toast.makeText(getBaseContext(), "Dados removidos com sucesso",
                                Toast.LENGTH_SHORT);
                        toast.show();
                            }
                        }
            });

        validar = (Button) findViewById(R.id.validar);
        validar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarLogin();

            }
        });


        Button cancelar = (Button) findViewById(R.id.cancelar);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.setText("");
                password.setText("");
            }
        });
    }

    public void validarLogin(){
        Intent intent = new Intent(MainActivity.this, AdminActivity.class);
        intent.putExtra("myString", login.getText());

        startActivity(intent);

    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences preferences = getSharedPreferences("preferencias",
                Context.MODE_PRIVATE);
        if (preferences.getBoolean("check", false)) {
            count+=1;
            lembrar.setChecked(true);
            login.setText(preferences.getString("login", "Não possui login salvo"));
            password.setText(preferences.getString("senha", "Não possui senha salva"));
            if (count == 2){
                validarLogin();
            }
        }
    }

}
