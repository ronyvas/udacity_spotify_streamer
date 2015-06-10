package com.rvasquez.spotifystreamer.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rvasquez.spotifystreamer.R;
import com.rvasquez.spotifystreamer.ui.activity.TopTracksActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import kaaes.spotify.webapi.android.models.Artist;

/**
 * Created by vasquez on 6/3/15.
 */
public class ArtistSearchAdapter extends RecyclerView.Adapter<ArtistSearchAdapter.ViewHolder> {
    private List<Artist> mItems;
    private Context mContext;

    public ArtistSearchAdapter(Context context, List<Artist> artists) {
        mItems = artists;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_artist_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Artist artist = mItems.get(position);
        holder.artistName.setText(artist.name);
        if (artist.images.size() > 1) {
            Picasso.with(mContext).load(artist.images.get(1).url).into(holder.thumbnail);
        } else {
            holder.thumbnail.setImageResource(R.drawable.bg_spotify_black);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void add(List<Artist> artists) {
        mItems.addAll(artists);
        this.notifyDataSetChanged();
    }

    public void clear() {
        mItems.clear();
        this.notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView thumbnail;
        TextView artistName;

        public ViewHolder(View view) {
            super(view);
            this.thumbnail = (ImageView) view.findViewById(R.id.img_artist_result_thumbnail);
            this.artistName = (TextView) view.findViewById(R.id.txt_artist_result_name);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Artist artist = mItems.get(this.getLayoutPosition());
            mContext.startActivity(TopTracksActivity.getInstance(mContext, artist.id, artist.name));
        }
    }
}
