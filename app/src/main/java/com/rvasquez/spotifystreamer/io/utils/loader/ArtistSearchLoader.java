package com.rvasquez.spotifystreamer.io.utils.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.rvasquez.spotifystreamer.StreamerApp;

import kaaes.spotify.webapi.android.models.ArtistsPager;

/**
 * Created by vasquez on 6/6/15.
 */
public class ArtistSearchLoader extends AsyncTaskLoader<ArtistsPager> {
    private final String searchString;
    private ArtistsPager mData;

    public ArtistSearchLoader(Context context, String searchString) {
        super(context);
        this.searchString = searchString;
    }

    @Override
    public ArtistsPager loadInBackground() {
        ArtistsPager pager = null;
        try {
            pager = StreamerApp.getInstance().getSpotifyService().searchArtists(searchString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pager;
    }

    @Override
    public void deliverResult(ArtistsPager data) {
        mData = data;

        if (isStarted()) {
            super.deliverResult(data);
        }

    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (mData != null) {
            deliverResult(mData);
        }

        if (takeContentChanged() || mData == null) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
    }
}
