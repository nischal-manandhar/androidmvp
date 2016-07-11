/*
 *
 *  * Copyright (C) 2014 Antonio Leiva Gordillo.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.antonioleiva.mvpexample.app.Login;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.antonioleiva.mvpexample.app.R;
import com.antonioleiva.mvpexample.app.main.MainActivity;

public class LoginActivity extends Activity implements LoginView, View.OnClickListener {
    private static final String TAG = LoginActivity.class.getSimpleName();
    private ProgressBar progressBar;
    private EditText username;
    private EditText password;
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = (ProgressBar) findViewById(R.id.progress);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        findViewById(R.id.button).setOnClickListener(this);

        presenter = new LoginPresenterImpl(this);
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setUsernameError() {
        username.setError(getString(R.string.username_error));
    }

    @Override
    public void setPasswordError() {
        password.setError(getString(R.string.password_error));
    }

    @Override
    public void navigateToHome() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onClick(View v) {
        String given = "pamapp://new_password/?token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6InJ1cGVzaEBlYnBlYXJscy5jb20iLCJjbGllbnRJZCI6ImRkdGVzdDEiLCJyb2xlIjoiZm9yZ290UGFzc3dvcmQiLCJpYXQiOjE0Njc5NTM0NjAsImV4cCI6MTQ2ODAzOTg2MH0.Nx7Ufhw-PGk5znOj0Sbbg1q0xs4YmmfaKeQTiWdR-Zg";
        String substr = "token";
        String before = given.substring(0, given.indexOf(substr) - 2);
        String after = given.substring(given.indexOf(substr) + 6);
        System.out.print(given);

        Uri uri =  Uri.parse("pamapp://new_password/?token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6InJ1cGVzaEBlYnBlYXJscy5jb20iLCJjbGllbnRJZCI6ImRkdGVzdDEiLCJyb2xlIjoiZm9yZ290UGFzc3dvcmQiLCJpYXQiOjE0Njc5NTM0NjAsImV4cCI6MTQ2ODAzOTg2MH0.Nx7Ufhw-PGk5znOj0Sbbg1q0xs4YmmfaKeQTiWdR-Zg");
        String token = uri.getQueryParameter("token");
        String url = uri.getHost();
        System.out.println("Prajit token==" + token);
        System.out.println("Prajit URL==" + url);
        Log.d(TAG, "before: " + before);
        Log.d(TAG, "after: " + after);

        presenter.validateCredentials(username.getText().toString(), password.getText().toString());
    }
}
