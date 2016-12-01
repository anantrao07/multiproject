package com.example;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
/**
 * Created by anant on 2016-11-30.
 */


public class JokeTreasureTest {

    @Test
    public void jokeSource() throws Exception {

        String result = "When I lost my rifle, the Army charged me $85. That’s why in the Navy, the captain goes down with the ship.";
        JokeTreasure js = new JokeTreasure();
        //assertEquals(js.jokeSource(), result);

        assertThat(js.jokeSource().equals(result),is(true));

    }

}