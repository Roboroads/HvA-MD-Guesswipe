package nl.itit.geoguesswipe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rschepers on 15/04/2018.
 */

public class GuesswipeActivity extends AppCompatActivity {

    private int score = 0;
    private int totalScorable = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guesswipe);

        final List<GuesswipeStreetviewObject> listStreetviewObject = new ArrayList<>();

        for (int id = 0; id < GuesswipeStreetviewObject.PRE_DEF_ID_TO_IMAGE.length; id++) {
            listStreetviewObject.add(new GuesswipeStreetviewObject(id));
        }

        final RecyclerView mRecyclerView = (RecyclerView) this.findViewById(R.id.guessswipeRecyclerView);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);

        mRecyclerView.setLayoutManager(mLayoutManager);
        final GuesswipeAdapter mAdapter = new GuesswipeAdapter(this, listStreetviewObject);
        mRecyclerView.setAdapter(mAdapter);

        totalScorable = mAdapter.getItemCount();



        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            //Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                //Get the index corresponding to the selected position
                int position = (viewHolder.getAdapterPosition());
                //Get the corresponding object
                final GuesswipeStreetviewObject streetviewObject = listStreetviewObject.get(position);


                Boolean rightAnswer = false;
                if(swipeDir == ItemTouchHelper.LEFT) {
                    //Swipe Left = in europe
                    if(streetviewObject.getIsInEurope()) {
                        rightAnswer = true;
                    }
                } else if (swipeDir == ItemTouchHelper.RIGHT) {
                    //Swipe Right != in europe
                    if(!streetviewObject.getIsInEurope()) {
                        rightAnswer = true;
                    }
                }

                if(rightAnswer) {
                    Toast.makeText(GuesswipeActivity.this, "That is correct, good job!", Toast.LENGTH_SHORT).show();
                    score++;
                } else {
                    Toast.makeText(GuesswipeActivity.this, "Oops, that wasn't right.", Toast.LENGTH_SHORT).show();
                }


                //Remove from list
                listStreetviewObject.remove(position);

                //Notify item in list got removed
                mAdapter.notifyItemRemoved(position);

                //Changing score in title
                setTitle("Geo Guesswipe (score: "+score+"/"+totalScorable+")");
            }
        };


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);

        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }
}
