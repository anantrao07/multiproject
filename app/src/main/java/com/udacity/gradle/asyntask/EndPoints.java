package com.udacity.gradle.asyntask;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.util.Pair;
import android.widget.Toast;

import com.example.anant.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.Databridge;
import com.udacity.gradle.builditbigger.MainActivityFragment;

import java.io.IOException;
/**
 * Created by anant on 2016-11-27.
 */

public class EndPoints extends AsyncTask<Pair<Context, String>,Void,String> {
    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param params The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */

    private static MyApi myAPIService= null;
    private Context context;

    Databridge databridge;




    @Override
    protected String doInBackground(Pair<Context, String>... params) {

        if(myAPIService == null){// Only do this once

            MyApi.Builder builder  = new MyApi.Builder(AndroidHttp.newCompatibleTransport()
            ,new AndroidJsonFactory(),null)
            // options for running against local devappserver
            // - 10.0.2.2 is localhost's IP address in Android emulator
            // - turn off compression when running against local devappserver
            .setRootUrl("http://192.168.0.7:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {


                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });

            // end options for devappserver

            myAPIService = builder.build();

        }

        context = params[0].first;
        String name = params[0].second;

        try {
            return myAPIService.sayHi(name).execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }

    }
    @Override
    protected void onPostExecute(String result) {


        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        Databridge db = new MainActivityFragment();
        db.jokebridge(result);



        //MyBean mybean = new MyBean().setData(result);


        //i.putExtra("jokefromserver",result);


        //i.putExtra("jokefromserver", result);
    }
}
