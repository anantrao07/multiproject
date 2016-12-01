import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.util.Pair;
import android.test.InstrumentationTestCase;

import com.example.anant.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertTrue;

/**
 * Created by anant on 2016-11-30.
 */

public class AsyncronusTest {

    private static MyApi myAPIService= null;
    private Context context;
    String returnedResult;

    @Test
    public void test() throws Throwable{
        final CountDownLatch cl = new CountDownLatch(1);


        final AsyncTask<Pair<Context,String> ,Void, String> task = new AsyncTask<Pair<Context, String>, Void, String>() {
            @Override
            protected String doInBackground(Pair<Context, String>... params) {


                if(myAPIService == null){// Only do this once

                    MyApi.Builder builder  = new MyApi.Builder(AndroidHttp.newCompatibleTransport()
                            ,new AndroidJsonFactory(),null)
                            // options for running against local devappserver
                            // - 10.0.2.2 is localhost's IP address in Android emulator
                            // - turn off compression when running against local devappserver
                            .setRootUrl("http://192.168.0.6:8080/_ah/api/")
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

            /**
             * <p>Runs on the UI thread after {@link #doInBackground}. The
             * specified result is the value returned by {@link #doInBackground}.</p>
             * <p>
             * <p>This method won't be invoked if the task was cancelled.</p>
             *
             * @param s The result of the operation computed by {@link #doInBackground}.
             * @see #onPreExecute
             * @see #doInBackground
             * @see #onCancelled(Object)
             */
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                returnedResult = s;

                cl.countDown();

            }
        };

        InstrumentationTestCase it = new InstrumentationTestCase();

        //execute asynctask on ui thread
        it.runTestOnUiThread(new Runnable() {
            /**
             * When an object implementing interface <code>Runnable</code> is used
             * to create a thread, starting the thread causes the object's
             * <code>run</code> method to be called in that separately executing
             * thread.
             * <p>
             * The general contract of the method <code>run</code> is that it may
             * take any action whatsoever.
             *
             * @see Thread#run()
             */
            @Override
            public void run() {

                task.execute(new Pair<Context, String>(context, "Manfred"));
            }
        });


       // cl.wait(10);
        //if (returnedResult!=null){
        assertTrue(returnedResult,true);

     //   }
      //  else {
          //  assertTrue(returnedResult, false);
      //  }
    }

     /* The testing thread will wait here until the UI thread releases it
     * above with the countDown() or 30 seconds passes and it times out.
     */
    //
}

