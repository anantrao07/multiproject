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
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
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

    /**
     * Called when a fragment is first attached to its context.
     * {@link #onCreate(Bundle)} will be called after this.
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        EventBus.getDefault().register(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View root = inflater.inflate(R.layout.fragment_main, container, false);
        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
        retrieveJoke = (Button) root.findViewById(R.id.jokebutton);
        showJoke = (TextView) root.findViewById(R.id.instructions_text_view);
        flavour.setText("Thank you for using free version");


        retrieveJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EndPoints().execute(new Pair<Context, String>(getContext(), "Manfred"));



            }


        });


        return root;
    }

    public void launchJokeActivity(){




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
