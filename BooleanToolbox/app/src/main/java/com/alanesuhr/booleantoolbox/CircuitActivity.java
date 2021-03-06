package com.alanesuhr.booleantoolbox;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Gallery;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


public class CircuitActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.circuit_page);
        Intent intent = getIntent();
        String prop = intent.getStringExtra(MyActivity.EXTRA_MESSAGE);
        try {
           BoolExpr bool = BoolExprParse.parse(prop);
           View view = new CircuitView(this.getApplicationContext(),bool);
           HorizontalScrollView scroll= (HorizontalScrollView)findViewById(R.id.innerScroll);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
           scroll.addView(view);
           //ScrollView scroll = (ScrollView)findViewById(R.id.scroll);



            //scroll.addView(view);
           // setContentView(view);

        }
        catch (RuntimeException e) {
            Log.e("err","toss", e);
            Context context = getApplicationContext();
            CharSequence texts = "You shan't";
            int duration= Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context,texts,duration);
            toast.show();
        }




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() { }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.truth_table,
                    container, false);
            return rootView;
        }
    }

}