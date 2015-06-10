package com.rvasquez.spotifystreamer.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rvasquez.spotifystreamer.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import kaaes.spotify.webapi.android.models.Track;

/**
 * Created by vasquez on 6/3/15.
 */
public class TopTracksAdapter extends RecyclerView.Adapter<TopTracksAdapter.ViewHolder> {
    private List<Track> mItems;
    private Context mContext;

    public TopTracksAdapter(Context context, List<Track> tracks) {
        this.mContext = context;
        this.mItems = tracks;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_top_track, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Track track = mItems.get(position);
        String trackName = String.format("%d. %s", position + 1, track.name);
        holder.trackName.setText(trackName);
        holder.albumName.setText(track.album.name);
        if (track.album.images.size() > 1) {
            Picasso.with(mContext).load(track.album.images.get(1).url).into(holder.thumbnail);
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

    public void add(List<Track> artists) {
        mItems.addAll(artists);
        this.notifyDataSetChanged();
    }

    public void clear() {
        mItems.clear();
        this.notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        TextView trackName;
        TextView albumName;


        public ViewHolder(View itemView) {
            super(itemView);
            this.thumbnail = (ImageView) itemView.findViewById(R.id.img_top_track_thumbnail);
            this.trackName = (TextView) itemView.findViewById(R.id.txt_top_track_name);
            this.albumName = (TextView) itemView.findViewById(R.id.txt_top_track_album);

        }
    }
}
