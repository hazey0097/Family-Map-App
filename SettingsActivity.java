package com.example.client.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.example.client.DataCache.DataCache;
import com.example.client.R;

public class SettingsActivity extends AppCompatActivity {
    private Switch showStoryLines;
    private Switch showFamilyLines;
    private Switch showFatherLines;
    private Switch showMotherLines;
    private Switch showMaleLines;
    private Switch showFemaleLines;
    private Switch showSpouseLines;
    private RelativeLayout logout;
    private DataCache myDataCache = DataCache.getInstance();
    // show the back button

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        showStoryLines = (Switch) findViewById(R.id.storyLineSwitch);
        showFamilyLines = (Switch) findViewById(R.id.familyLineSwitch);
        showFatherLines = (Switch) findViewById(R.id.FatherLineSwitch);
        showMotherLines = (Switch) findViewById(R.id.MotherLineSwitch);
        showMaleLines = (Switch) findViewById(R.id.MaleLineSwitch);
        showFemaleLines = (Switch) findViewById(R.id.FemaleLineSwitch);
        showSpouseLines = (Switch) findViewById(R.id.SpouseLineSwitch);
        logout = (RelativeLayout) findViewById(R.id.LogoutRelativeLayout);
        showStoryLines.setChecked(myDataCache.isStoryLine());
        showFamilyLines.setChecked(myDataCache.isFamilyTreeLine());
        showSpouseLines.setChecked(myDataCache.isSpouseLine());
        showFatherLines.setChecked(myDataCache.isFatherLine());
        showMotherLines.setChecked(myDataCache.isMotherLine());
        showMaleLines.setChecked(myDataCache.isMaleLine());
        showFemaleLines.setChecked(myDataCache.isFemaleLine());

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLogout();
            }
        });
        showStoryLines.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                showStoryLines.setChecked(isChecked);
                onStoryLine();
            }
        });
        showFamilyLines.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                showFamilyLines.setChecked(isChecked);
                onFamilyLine();
            }
        });
        showSpouseLines.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                showSpouseLines.setChecked(isChecked);
                onSpouseLine();
            }
        });
        showFatherLines.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                showFatherLines.setChecked(isChecked);
                onFatherLine();
            }
        });
        showMotherLines.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                showMotherLines.setChecked(isChecked);
                onMotherLine();
            }
        });
        showMaleLines.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                showMaleLines.setChecked(isChecked);
                onMaleLine();
            }
        });
        showFemaleLines.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                showFemaleLines.setChecked(isChecked);
                onFemaleLine();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menu) {
        if(menu.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP |
                    Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return true;
    }
    public void onStoryLine(){
        if(showStoryLines.isChecked()){
            myDataCache.setStoryLine(true);
        }
        else{
            myDataCache.setStoryLine(false);
        }
    }
    public void onFamilyLine(){
        if(showFamilyLines.isChecked()){
            myDataCache.setFamilyTreeLine(true);
        }
        else{
            myDataCache.setFamilyTreeLine(false);
        }
    }
    public void onSpouseLine(){
        if(showSpouseLines.isChecked()){
            myDataCache.setSpouseLine(true);
        }
        else{
            myDataCache.setSpouseLine(false);
        }
    }
    public void onFatherLine(){
        if(showFatherLines.isChecked()){
            myDataCache.setFatherLine(true);
        }
        else{
            myDataCache.setFatherLine(false);
        }
    }
    public void onMotherLine(){
        if(showMotherLines.isChecked()){
            myDataCache.setMotherLine(true);
        }
        else{
            myDataCache.setMotherLine(false);
        }
    }
    public void onMaleLine(){
        if(showMaleLines.isChecked()){
            showMaleLines.setChecked(true);
            myDataCache.setMaleLine(true);
        }
        else{
            showMaleLines.setChecked(false);
            myDataCache.setMaleLine(false);
        }
    }
    public void onFemaleLine(){
        if(showFemaleLines.isChecked()){
            myDataCache.setFemaleLine(true);
        }
        else{
            myDataCache.setFemaleLine(false);
        }
    }

    public void onLogout(){
        myDataCache.setAuthToken(null);
        Intent newLogin = new Intent(this, MainActivity.class);
        newLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        newLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(newLogin);
    }
}
