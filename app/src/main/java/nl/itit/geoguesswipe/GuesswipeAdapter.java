package nl.itit.geoguesswipe;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by rschepers on 15/04/2018.
 */


public class GuesswipeAdapter extends RecyclerView.Adapter<GuesswipeViewHolder> {

    private final Context context;
    public List<GuesswipeStreetviewObject> listStreetviewObject;

    public GuesswipeAdapter(Context context, List<GuesswipeStreetviewObject> listStreetviewObject) {
        this.context = context;
        this.listStreetviewObject = listStreetviewObject;
    }

    @Override
    public GuesswipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.guesswipe_cell, parent, false);
        return new GuesswipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GuesswipeViewHolder holder, final int position) {
        final GuesswipeStreetviewObject streetviewObject = listStreetviewObject.get(position);
        holder.streetviewImage.setImageResource(streetviewObject.getStreetviewImage());
    }

    @Override
    public int getItemCount() {
        return listStreetviewObject.size();
    }
}
