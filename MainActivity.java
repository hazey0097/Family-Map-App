package com.example.client.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import android.widget.Toast;

import com.example.client.DataCache.DataCache;
import com.example.client.Listener.Listener;
import com.example.client.R;
import com.example.client.fragments.LoginFragment;
import com.example.client.fragments.MapFragment;


public class MainActivity extends AppCompatActivity implements Listener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoginFragment loginFragment = new LoginFragment();
        Bundle bundle = new Bundle();

        Listener listener = this;

        bundle.putSerializable(LoginFragment.KEY_FOR_LOGIN_LISTENER , listener);
        loginFragment.setArguments(bundle);
        DataCache dataCache = DataCache.getInstance();

        if(dataCache.getAuthToken() == null){
            FragmentManager fm = this.getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.fragment_container, loginFragment, "LOGIN").commit();
        }
        else {
            successfulLogin();
        }
    }
    public void successfulLogin(){
        FragmentManager fm = this.getSupportFragmentManager();
        fm.beginTransaction().remove(fm.findFragmentByTag("LOGIN")).commit();
        Fragment map = fm.findFragmentById(R.id.map);
        if(map == null){
            map = new MapFragment();
            fm.beginTransaction().add(R.id.fragment_container, map,"MAP").commit();
        }
    }
    @Override
    public void onLoginSuccess(){
        successfulLogin();
    }

    @Override
    public void onLoginFailure() {
        Toast.makeText(this,
                "unsuccessful attempt in signing in!",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRegisterFailure() {
        Toast.makeText(this,
                "unsuccessful attempt in registering user!",
                Toast.LENGTH_SHORT).show();
    }

}
