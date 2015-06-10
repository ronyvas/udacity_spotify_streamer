package com.rvasquez.spotifystreamer.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.rvasquez.spotifystreamer.R;
import com.rvasquez.spotifystreamer.ui.fragment.TopTracksFragment;

/**
 * Created by vasquez on 6/3/15.
 */
public class TopTracksActivity extends AppCompatActivity {
    private final static String EXTRA_ARTIST_ID = "artist_id";
    private final static String EXTRA_ARTIST_NAME = "artist_name";

    public static Intent getInstance(Context context, String id, String name) {
        Intent i = new Intent(context, TopTracksActivity.class);
        i.putExtra(EXTRA_ARTIST_ID, id);
        i.putExtra(EXTRA_ARTIST_NAME, name);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        getSupportActionBar().setTitle(getResources().getString(R.string.title_top_tracks));
        getSupportActionBar().setSubtitle(getIntent().getStringExtra(EXTRA_ARTIST_NAME));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        if (savedInstanceState == null) {
            String artistId = getIntent().getExtras().getString(EXTRA_ARTIST_ID);
            getFragmentManager().beginTransaction().add(R.id.fragment_container, TopTracksFragment.getInstance(artistId)).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
