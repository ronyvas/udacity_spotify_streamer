package com.rvasquez.spotifystreamer.io.utils.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.rvasquez.spotifystreamer.StreamerApp;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import kaaes.spotify.webapi.android.models.Tracks;

/**
 * Created by vasquez on 6/6/15.
 */
public class TopTracksLoader extends AsyncTaskLoader<Tracks> {
    private Tracks mData;
    private final String mArtistId;

    public TopTracksLoader(Context context, String artistId) {
        super(context);
        this.mArtistId = artistId;
    }

    @Override
    public Tracks loadInBackground() {
        Tracks tracks = null;
        try {
            Map<String, Object> queryMap = new HashMap<>();
            queryMap.put("country", Locale.getDefault().getCountry());
            tracks = StreamerApp.getInstance().getSpotifyService().getArtistTopTrack(mArtistId, queryMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tracks;
    }

    @Override
    public void deliverResult(Tracks data) {
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
