package com.projects.demo.activity;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.projects.demo.R;
import com.projects.demo.util.Typefaces;
import com.projects.demo.view.Titanic;
import com.projects.demo.view.TitanicTextView;


public class TitanicActivity extends SherlockFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.titanic_main);

        TitanicTextView tv = (TitanicTextView) findViewById(R.id.my_text_view);

        // set fancy typeface
        tv.setTypeface(Typefaces.get(this, "fonts/Satisfy-Regular.ttf"));

        // start animation
        new Titanic().start(tv);
    }

}
