package com.kamjer.purinescalculator.datarepository;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;

import com.kamjer.purinescalculator.R;
import com.kamjer.purinescalculator.activity.MainApp;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashMap;

public class DataRepository {

    public final HashMap<String, Integer> purinesLookUpTable = new HashMap<>();

    public DataRepository(Context context) throws XmlPullParserException, IOException {
        loadPurinesLookUpTable(context);
    }

    private void loadPurinesLookUpTable(Context context) throws Resources.NotFoundException, XmlPullParserException, IOException {
//        loading lookup table from xml file
        XmlResourceParser xpp = context.getResources().getXml(R.xml.purin_look_up_table);
//        looping thru the file
        while (xpp.getEventType()!= XmlPullParser.END_DOCUMENT) {
//            looking on a tags on a file
            if (xpp.getEventType()==XmlResourceParser.START_TAG) {
                if (xpp.getName().equals(context.getResources().getString(R.string.product))) {
//                    loading data if tag matches
                    purinesLookUpTable.put(xpp.getAttributeValue(1), Integer.valueOf(xpp.getAttributeValue(2)));
                }
            }
            xpp.next();
        }
    }

    public HashMap<String, Integer> getPurinesLookUpTable() {
        return new HashMap<>(purinesLookUpTable);
    }
}
