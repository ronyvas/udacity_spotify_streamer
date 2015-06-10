package com.rvasquez.spotifystreamer.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.rvasquez.spotifystreamer.R;
import com.rvasquez.spotifystreamer.io.utils.Utils;
import com.rvasquez.spotifystreamer.ui.fragment.ArtistSearchFragment;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.fragment_container, ArtistSearchFragment.getInstance()).commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!Utils.isNetworkConnected(this)) {
            showExitDialog();
        }
    }

    private void showExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.network_error));
        builder.setMessage(getString(R.string.no_internet));
        builder.setCancelable(false);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.show();
    }
}
