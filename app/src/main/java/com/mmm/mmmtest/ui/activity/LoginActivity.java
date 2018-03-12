package com.mmm.mmmtest.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mmm.mmmtest.R;
import com.mmm.mmmtest.data_access.DbHelperFactory;
import com.mmm.mmmtest.data_access.dao.UserDao;
import com.mmm.mmmtest.data_access.entity.User;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername;
    EditText etPassword;
    TextInputLayout etUsernameLayout;
    TextInputLayout etPasswordLayout;

    TextView tvErrorMessage;

    Button btnLogin;

    View layoutLogin;

    UserDao userDao = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userDao = new UserDao(LoginActivity.this);
        userDao.getDbHelper().recreate(userDao.getDb());

        setContentView(R.layout.activity_login);
        etUsername = (EditText)findViewById(R.id.etUsername);
        etPassword = (EditText)findViewById(R.id.etPassword);
        etUsernameLayout = (TextInputLayout)findViewById(R.id.etUsernameLayout);
        etPasswordLayout = (TextInputLayout)findViewById(R.id.etPasswordLayout);

        tvErrorMessage = (TextView)findViewById(R.id.tvErrorMessage);
        btnLogin = (Button)findViewById(R.id.btnLogin);

        layoutLogin = findViewById(R.id.layoutLogin);

        layoutLogin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(getCurrentFocus() != null)
                    hideSoftInput(getCurrentFocus());
                return false;
            }
        });

        //ONLY IF DATABASE WAS CREATED BY ANDROID (IT THINK IS BECAUSE OF THE .JOURNAL FILE)
        //deleteDatabase(DbHelperFactory.DbHelper.DATABASE_NAME);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                if(TextUtils.isEmpty(username)) {
                    int inputType = etUsername.getInputType();
                    etUsernameLayout.setError("Username cannot be empty");
                    hideSoftInput(etUsername);
                    etUsername.requestFocus();
                    //showSoftInput(etUsername, inputType);
                    return;
                } else{
                    etUsernameLayout.setErrorEnabled(false);
                }
                if(TextUtils.isEmpty(password)) {
                    int inputType = etPassword.getInputType();
                    etPasswordLayout.setError("Password cannot be empty");
                    hideSoftInput(etPassword);
                    etPassword.requestFocus();
                    //showSoftInput(etPassword, inputType);
                    return;
                } else{
                    etPasswordLayout.setErrorEnabled(false);
                }

                User currentUser = userDao.login(username, password);
                if(currentUser != null){
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else{
                    tvErrorMessage.setText(getString(R.string.login_error));
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        if(userDao != null)
            userDao.close();
        super.onDestroy();
    }

    void hideSoftInput(View view){
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), 0);
        //((EditText)view).setInputType(InputType.TYPE_NULL);
    }

    void showSoftInput(View view, int inputType){
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        //((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
        ((EditText)view).setInputType(inputType);//THESE TWO MAKE THE PASSWORD INPUT TYPE BY USING THE BINARY OR InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT
    }
}
