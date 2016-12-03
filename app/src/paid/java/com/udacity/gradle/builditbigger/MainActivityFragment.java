package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.andorid.freemind.jokewizardandroid.JokeActivity;
import com.example.JokeTreasure;
import com.udacity.gradle.asyntask.EndPoints;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements Databridge  {


    private EventBus bus = EventBus.getDefault();


    public MainActivityFragment() {


    }



    Button retrieveJoke;
    TextView showJoke;
    TextView flavour;
    JokeTreasure js = new JokeTreasure();

    final String joke = js.jokeSource();


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        EventBus.getDefault().register(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View root = inflater.inflate(R.layout.fragment_main, container, false);

        retrieveJoke = (Button) root.findViewById(R.id.jokebutton);
        showJoke = (TextView) root.findViewById(R.id.instructions_text_view);
        flavour = (TextView)root.findViewById(R.id.flavortextview) ;
        flavour.setText("Welcome to premium versionyou will have no ads here");


        retrieveJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EndPoints().execute(new Pair<Context, String>(getContext(), "Manfred"));



            }


        });


        return root;
    }


    @Subscribe (threadMode= ThreadMode.MAIN)
    public void onEvent(String result){

        String joke = result;
        Intent intent = new Intent(getContext(), JokeActivity.class);
        intent.putExtra("jokefrommaf", joke);
        startActivity(intent);

    }

    @Override
    public String jokebridge(String data) {
        return data;
    }

    /**
     * Called when the fragment is no longer attached to its activity.  This
     * is called after {@link #onDestroy()}.
     */
    @Override
    public void onDetach() {

        super.onDetach();
        EventBus.getDefault().unregister(this);
    }
}
