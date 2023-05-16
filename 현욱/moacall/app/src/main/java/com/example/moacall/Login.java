package com.example.moacall;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Login extends AppCompatActivity implements View.OnClickListener{

    Button join_btn;
    Button login_btn;

    EditText id_edit;
    EditText pw_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.id_activity);

        join_btn = (Button)findViewById(R.id.join_btn);
        login_btn = (Button)findViewById(R.id.login_btn);

        join_btn.setOnClickListener(this);
        login_btn.setOnClickListener(this);

        id_edit = (EditText)findViewById(R.id.id_edit);
        pw_edit = (EditText)findViewById(R.id.pw_edit);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                login();
                break;
            case R.id.join_btn:
                Intent join_intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://192.168.0.4:8080/members/new"));
                startActivity(join_intent);
                finish();
                break;
        }

    }



    void login() {
        Log.w("login", "Trying to Login");
        try {
            String id = id_edit.getText().toString();
            String pw = pw_edit.getText().toString();
            Log.w("Send : ", id+ " , "+ pw);

            CustomTask task = new CustomTask();
            String result = task.execute(id, pw).get();
            Log.w("Receive", result);
            int resultInt = Integer.parseInt(result);
            if (resultInt == 200) {
                Intent login_intent = new Intent(Login.this, MainActivity.class);
                startActivity(login_intent);
                finish();
            } else {
                Log.w("not Connect",id);
                finish();
            }

        } catch (Exception e) {

        }
    }

    class CustomTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;


        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://192.168.0.4:8080/login");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);

                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "user_name="+strings[0]+"&pw="+strings[1];
                Log.d("sendMsg", sendMsg);
                osw.write(sendMsg);
                osw.flush();

                if(conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader inputStreamReader = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(inputStreamReader);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();
                } else{
                    Log.i("Connection Result ", conn.getResponseCode()+" ERROR");
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e ) {
                e.printStackTrace();
            }
            return receiveMsg;
        }
    }
}
