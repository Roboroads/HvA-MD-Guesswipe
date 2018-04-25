package nl.itit.geoguesswipe;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by rschepers on 15/04/2018.
 */


public class GuesswipeViewHolder extends RecyclerView.ViewHolder {

    public ImageView streetviewImage;
    public View view;

    public GuesswipeViewHolder(View itemView) {
        super(itemView);
        streetviewImage = itemView.findViewById(R.id.streetviewImage);
        view = itemView;
    }

}

