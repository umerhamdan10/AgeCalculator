package com.hamdani.agecalculator;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;


public class GetStarted extends AppCompatActivity {
    private AdView mAdView, mAdView2;
      private InterstitialAd mInterstitialAd;
      int count=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_get_started);
        showInterAdd();


        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



    }


    public void showInterAdd() {
        count++;
        mInterstitialAd = new InterstitialAd(this);
//        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
//        mInterstitialAd.setAdUnitId(String.valueOf(R.string.ADD_INTERSTALIAR));
       // mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.setAdUnitId("ca-app-pub-3614349063418432/1333091141");
        mInterstitialAd.loadAd(new AdRequest.Builder().addTestDevice("deviceid").build());
        mInterstitialAd.setAdListener(new com.google.android.gms.ads.AdListener() {
            @Override
            public void onAdLoaded() {
                mInterstitialAd.show();
                super.onAdLoaded();

            }
        });

    }

    public void getStarted(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        showInterAdd();
    }
}
