package com.kamjer.purinescalculator;

import com.kamjer.purinescalculator.activity.MainApp;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {

    @Mock
    private MainApp mockedActivity;
//
//    @Test
//    public void checkCalculations() {
//        // values               218                8
//        String[] ing = {"Cielęcina, wątróbka", "Mleko"};
//        int[] weight = {100, 50};
//        int expected = (int) (218 + 8 * 0.5);
//
////        when(mockedActivity.calculateUricAcid(ing, weight)).thenReturn(expected);
//
////        int calc = mockedActivity.calculateUricAcid(ing, weight);
//
//        Assert.assertEquals(expected, calc);
//    }
}