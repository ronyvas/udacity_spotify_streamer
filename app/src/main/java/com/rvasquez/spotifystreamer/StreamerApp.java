package com.rvasquez.spotifystreamer;

import android.app.Application;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;

/**
 * Created by vasquez on 6/4/15.
 */
public class StreamerApp extends Application {

    private static StreamerApp mInstance;
    private SpotifyService mSpotifyService;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        SpotifyApi api = new SpotifyApi();
        mSpotifyService = api.getService();
    }

    public static StreamerApp getInstance() {
        return mInstance;
    }

    public SpotifyService getSpotifyService() {
        return mSpotifyService;
    }
}
