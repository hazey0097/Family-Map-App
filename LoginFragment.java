package com.example.client.fragments;

import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.*;
import android.widget.RadioButton;
import android.widget.EditText;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.client.DataCache.DataCache;
import com.example.client.Listener.Listener;
import com.example.client.Proxy.ProxyServer;
import com.example.client.R;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


import Domain.Event;
import Domain.Person;
import Domain.User;
import Request.LoginReq;
import Request.RegisterReq;
import Result.EventRes;
import Result.Login;
import Result.PersonRes;
import Result.Register;


public class LoginFragment extends Fragment {
    public static final String KEY_FOR_LOGIN_LISTENER = "listener";
    private Listener listener;
    private User user;
    private DataCache myDataCache = DataCache.getInstance();
    //All widgets in layout
    private EditText serverHostNumber;
    private EditText serverPortNumber;
    private EditText myUsername;
    private EditText myPassword;
    private EditText myFirstName;
    private EditText myLastName;
    private EditText myEmailAddress;
    private RadioButton myMale;
    private RadioButton myFemale;
    private Button myLoginButton;
    private Button myRegisterButton;
    private RadioGroup myRadioGroup;


    private TextWatcher tw = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // nothing is overridden here
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            boolean loginButton = serverHostNumber.getText().length() > 0 && serverPortNumber.getText().length() > 0 && myUsername.getText().length() > 0 &&
                    myPassword.getText().length() > 0;
            myLoginButton.setEnabled(loginButton);
            boolean registerBoolean = loginButton && myFirstName.getText().length() > 0 && myLastName.getText().length() > 0  &&
                    myEmailAddress.getText().length() > 0;
            myRegisterButton.setEnabled(registerBoolean);
            // check radio group access, getradiocheckedID
        }
        @Override
        public void afterTextChanged(Editable s) {
            // nothing is overridden here
        }
    };

    public LoginFragment(){
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = new User();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.activity_login, container, false);

        //This will wire up the widgets
        serverHostNumber = (EditText) v.findViewById(R.id.serverHost);
        serverPortNumber= (EditText) v.findViewById(R.id.serverPort);
        myUsername = (EditText) v.findViewById(R.id.username);
        myPassword = (EditText) v.findViewById(R.id.password);
        myFirstName = (EditText) v.findViewById(R.id.firstName);
        myLastName = (EditText) v.findViewById(R.id.lastName);
        myEmailAddress = (EditText) v.findViewById(R.id.emailAddress);
        myMale = (RadioButton) v.findViewById(R.id.male);
        myFemale = (RadioButton) v.findViewById(R.id.female);
        myLoginButton = (Button) v.findViewById(R.id.loginButton);
        myRegisterButton = (Button) v.findViewById(R.id.registerButton);
        myRadioGroup = (RadioGroup) v.findViewById(R.id.radioGroup1);
        myLoginButton.setEnabled(false);
        myRegisterButton.setEnabled(false);
        myMale.setChecked(true);
        if(savedInstanceState == null){
            System.out.println("NULL1");
        }
        listener = (Listener) getArguments().getSerializable(KEY_FOR_LOGIN_LISTENER);
        if(listener == null){
            System.out.println("NULL");
        }
        serverHostNumber.addTextChangedListener(tw);
        serverPortNumber.addTextChangedListener(tw);
        myUsername.addTextChangedListener(tw);
        myPassword.addTextChangedListener(tw);
        myFirstName.addTextChangedListener(tw);
        myLastName.addTextChangedListener(tw);
        myEmailAddress.addTextChangedListener(tw);
        myMale.addTextChangedListener(tw);
        myFemale.addTextChangedListener(tw);
        myLoginButton.addTextChangedListener(tw);
        myRegisterButton.addTextChangedListener(tw);


        myLoginButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                onLoginClicked();
            }
        });
        myRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRegisterClicked();
            }
        });
        // set click listener here

        return v;
    }

    public User registerUser(){
        user.setUserName(myUsername.getText().toString());
        user.setPassword(myPassword.getText().toString());
        user.setFirstName(myFirstName.getText().toString());
        user.setLastName(myLastName.getText().toString());
        user.setEmail(myLastName.getText().toString());
        if(myMale.isChecked()){
            user.setGender('m');
        }
        else if (myFemale.isChecked()){
            user.setGender('f');
        }
        return user;
    }

    public void onLoginClicked(){
        myDataCache.setServerHost(serverHostNumber.getText().toString());
        myDataCache.setServerPort(serverPortNumber.getText().toString());

        LoginReq loginReq = new LoginReq();
        loginReq.setUserName(myUsername.getText().toString());
        loginReq.setPassword(myPassword.getText().toString());
        LoginTask loginTask = new LoginTask();
        loginTask.execute(loginReq);
    }
    public void onRegisterClicked(){
        RegisterReq registerReq = new RegisterReq();
        myDataCache.setServerHost(serverHostNumber.getText().toString());
        myDataCache.setServerPort(serverPortNumber.getText().toString());
        registerReq.setUserName(myUsername.getText().toString());
        registerReq.setPassword(myPassword.getText().toString());
        registerReq.setFirstName(myFirstName.getText().toString());
        registerReq.setLastName(myFirstName.getText().toString());
        registerReq.setEmail(myEmailAddress.getText().toString());
        if(myMale.isChecked()){
            registerReq.setGender('m');
        }
        else if (myFemale.isChecked()) {
            registerReq.setGender('f');
        }
        RegisterTask registerTask =  new RegisterTask();
        registerTask.execute(registerReq);
    }
    public class LoginTask extends AsyncTask<LoginReq, Void, Login> {
       private Login loginResponse;
        @Override
        protected Login doInBackground(LoginReq... loginReqs) {
            ProxyServer loginProxy = new ProxyServer();
            Login response = loginProxy.loginProxy(loginReqs[0]);
            if(response != null){
                if(response.isSuccess()){
                    myDataCache.setAuthToken(response.getAuthToken());
                    EventRes events = loginProxy.getEventsProxy(myDataCache.getAuthToken());
                    PersonRes people = loginProxy.getPeopleProxy(myDataCache.getAuthToken());
                    myDataCache.setUser(people.getData()[0]);
                    filterData(events, people);
                }
            }
            return response;
        }
        protected void onPostExecute(Login response){
            // do the toast here
            if(response == null){
                listener.onLoginFailure();
            }
            else if(response.isSuccess()){
                Toast.makeText(getActivity(),
                        "Welcome " + response.getUserName() + "!",
                        Toast.LENGTH_SHORT).show();
                listener.onLoginSuccess();
            }
        }
    }
    public class RegisterTask extends AsyncTask<RegisterReq, Void, Register> {
        private Register registerResponse; 
        @Override
        protected Register doInBackground(RegisterReq... registerReqs) {
            ProxyServer registerProxy = new ProxyServer();
            Register response = registerProxy.registerProxy(registerReqs[0]);
            if(response != null){
                if(response.isSuccess()){
                    myDataCache.setAuthToken(response.getAuthToken());
                    EventRes events = registerProxy.getEventsProxy(myDataCache.getAuthToken());
                    PersonRes people = registerProxy.getPeopleProxy(myDataCache.getAuthToken());
                    myDataCache.setUser(people.getData()[0]);
                    filterData(events, people);
                }
            }
            return response;
        }
        protected void onPostExecute(Register response){
            if(response == null){
                listener.onRegisterFailure();
            }
            else if(response.isSuccess()){
                myDataCache.setAuthToken(response.getAuthToken());
                Toast.makeText(getActivity(),
                        "Welcome " +  myFirstName.getText().toString() + " " + myLastName.getText().toString()+ "!",
                        Toast.LENGTH_SHORT).show();
                listener.onLoginSuccess();
            }
        }
    }
    public void filterData(EventRes events, PersonRes people){
        // insert events to the map in the data cache
        HashMap<String, HashSet<Event>> filteredEvents = new HashMap<String, HashSet<Event>>();
        HashMap<String, Person> filteredPeople = new HashMap<String, Person>();
        for(int i = 0; i < events.getData().length; i++){
            if(filteredEvents.containsKey(events.getData()[i].getPersonID())){
                filteredEvents.get(events.getData()[i].getPersonID()).add(events.getData()[i]);
            }
            else{
                HashSet<Event> tempEvents = new HashSet<Event>();
                tempEvents.add(events.getData()[i]);
                filteredEvents.put(events.getData()[i].getPersonID(), tempEvents);
            }
        }
        for(int i = 0; i < people.getData().length; i++){
            filteredPeople.put(people.getData()[i].getPersonID(),people.getData()[i]);
        }
        myDataCache.setAllEvents(filteredEvents);
        myDataCache.setAllPeople(filteredPeople);
        myDataCache.addDirectFamily();
        myDataCache.filterFatherSide(myDataCache.getFather());
        myDataCache.filterMotherSide(myDataCache.getMother());
        myDataCache.filterGender();
    }
    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    @Override
    public void onStart() {
        super.onStart();
    }
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}