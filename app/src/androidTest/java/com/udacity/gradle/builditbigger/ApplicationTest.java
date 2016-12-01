package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.support.v4.util.Pair;
import android.test.AndroidTestCase;

import com.example.JokeTreasure;
import com.udacity.gradle.asyntask.EndPoints;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends AndroidTestCase {




    public void testResponse(){

        EndPoints endpoints = new EndPoints();

        String joke = new JokeTreasure().jokeSource();

      String result =   endpoints.execute(new Pair<Context, String>(getContext(), "Manfred")).toString();

        assertEquals( joke,result);



    }

}