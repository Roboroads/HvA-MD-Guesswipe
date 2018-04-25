package nl.itit.geoguesswipe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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


        //Initialize a new gesture listener
        final GestureDetector.SimpleOnGestureListener gestureListener = new GestureDetector.SimpleOnGestureListener() {
            private static final int MINIMAL_DISTANCE_FOR_SWIPE = 120;
            private static final int MINIMAL_SPEED_FOR_SWIPE = 200;
            private static final int MAX_PATH_CORRECTION = 250; //When swiping down, no need to do gesture actions - we just want left/right.

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                try {
                    if (Math.abs(e1.getY() - e2.getY()) > MAX_PATH_CORRECTION) {
                        return false;
                    }

                    Boolean rightAnswer = false;
                    // finding item that got swiped
                    View child = mRecyclerView.findChildViewUnder(e1.getX(), e1.getY());
                    int position = mRecyclerView.getChildPosition(child);
                    final GuesswipeStreetviewObject streetviewObject = listStreetviewObject.get(position);

                    //math.abs makes number always positive.
                    if(e1.getX() - e2.getX() > MINIMAL_DISTANCE_FOR_SWIPE && Math.abs(velocityX) > MINIMAL_SPEED_FOR_SWIPE) {
                        //Swipe Left = in europe
                        if(streetviewObject.getIsInEurope()) {
                            rightAnswer = true;
                        }
                    } else if (e2.getX() - e1.getX() > MINIMAL_DISTANCE_FOR_SWIPE && Math.abs(velocityX) > MINIMAL_SPEED_FOR_SWIPE) {
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

                    //Removing the one that got swiped
                    listStreetviewObject.remove(position);

                    //Notifying that we changed the array.
                    mAdapter.notifyDataSetChanged();

                    //Changing score in title
                    setTitle("Geo Guesswipe (score: "+score+"/"+totalScorable+")");

                } catch (Exception e) {
                    //Do nothing if it fails
                }
                return false;
            }
        };

        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            //add gestureListener to a GestureDetector
            GestureDetector gestureDetector = new GestureDetector(GuesswipeActivity.this, gestureListener);

            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                gestureDetector.onTouchEvent(e);
                return false;
            }

            //Do nothing here
            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {}

            //Do nothing here
            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {}
        });

    }
}
