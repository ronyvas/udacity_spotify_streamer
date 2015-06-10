package com.rvasquez.spotifystreamer.ui.fragment;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rvasquez.spotifystreamer.R;
import com.rvasquez.spotifystreamer.io.utils.loader.TopTracksLoader;
import com.rvasquez.spotifystreamer.ui.adapter.TopTracksAdapter;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;

/**
 * Created by vasquez on 6/3/15.
 */
public class TopTracksFragment extends Fragment implements LoaderManager.LoaderCallbacks<Tracks> {
    private final static String ARGS_ARTIST_ID = "artist_id";
    private final static int LOADER_ID = 1123;

    @InjectView(R.id.rv_top_tracks)
    RecyclerView mRecyclerView;
    @InjectView(R.id.empty_view)
    TextView mEmptyText;
    @InjectView(R.id.progress)
    ProgressBar mProgress;

    private RecyclerView.LayoutManager mLayoutManager;

    private TopTracksAdapter mAdapter;

    private String mArtistId;

    public static TopTracksFragment getInstance(String artistId) {
        TopTracksFragment fragment = new TopTracksFragment();
        Bundle b = new Bundle();
        b.putString(ARGS_ARTIST_ID, artistId);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARGS_ARTIST_ID)) {
            mArtistId = getArguments().getString(ARGS_ARTIST_ID);
        }

        mAdapter = new TopTracksAdapter(getActivity(), new ArrayList<Track>());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_tracks, container, false);
        ButterKnife.inject(this, view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mProgress.setVisibility(View.VISIBLE);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public Loader<Tracks> onCreateLoader(int id, Bundle args) {
        return new TopTracksLoader(getActivity().getApplicationContext(), mArtistId);
    }

    @Override
    public void onLoadFinished(Loader<Tracks> loader, Tracks data) {
        if (data != null && data.tracks.size() > 0) {
            mAdapter.add(data.tracks);
        } else {
            mRecyclerView.setVisibility(View.GONE);
            mEmptyText.setVisibility(View.VISIBLE);
        }
        mProgress.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<Tracks> loader) {

    }
}
