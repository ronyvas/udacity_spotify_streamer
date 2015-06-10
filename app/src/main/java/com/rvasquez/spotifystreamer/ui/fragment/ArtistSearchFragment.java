package com.rvasquez.spotifystreamer.ui.fragment;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.rvasquez.spotifystreamer.R;
import com.rvasquez.spotifystreamer.io.utils.loader.ArtistSearchLoader;
import com.rvasquez.spotifystreamer.ui.adapter.ArtistSearchAdapter;

import java.util.ArrayList;

import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;

/**
 * Created by vasquez on 6/3/15.
 */
public class ArtistSearchFragment extends Fragment implements TextView.OnEditorActionListener, LoaderManager.LoaderCallbacks<ArtistsPager> {

    private final static String ARGS_SEARCH = "search";
    private final static int LOADER_ID = 1124;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private EditText mArtistSearchBox;
    //    private ProgressDialog mProgress;
    private ProgressBar mProgress;

    private ArtistSearchAdapter mAdapter;

    public static ArtistSearchFragment getInstance() {
        return new ArtistSearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mAdapter = new ArtistSearchAdapter(getActivity(), new ArrayList<Artist>());
//        mProgress = new ProgressDialog(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artist_search, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_artist_results);
        mProgress = (ProgressBar) view.findViewById(R.id.progress);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mArtistSearchBox = (EditText) view.findViewById(R.id.edt_artist_search);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setAdapter(mAdapter);
        mArtistSearchBox.setOnEditorActionListener(this);
        mArtistSearchBox.clearFocus();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getLoaderManager().getLoader(LOADER_ID) != null)
            getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            if (!v.getText().toString().isEmpty()) {
                mAdapter.clear();
                Bundle b = new Bundle();
                b.putString(ARGS_SEARCH, mArtistSearchBox.getText().toString());
                getLoaderManager().restartLoader(LOADER_ID, b, this);
                mProgress.setVisibility(View.VISIBLE);

            }
            return true;
        }
        return false;
    }

    @Override
    public Loader<ArtistsPager> onCreateLoader(int id, Bundle args) {
        return new ArtistSearchLoader(getActivity().getApplicationContext(), args.getString(ARGS_SEARCH));
    }

    @Override
    public void onLoadFinished(Loader<ArtistsPager> loader, ArtistsPager data) {
        if (data == null || data.artists.items.size() <= 0) {
            showRetryToast();
        } else {
            mAdapter.add(data.artists.items);
        }
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(mArtistSearchBox.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        mProgress.setVisibility(View.GONE);
    }

    private void showRetryToast() {
        Toast.makeText(getActivity(), getString(R.string.artist_not_found), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoaderReset(Loader<ArtistsPager> loader) {

    }
}
