package com.alanesuhr.booleantoolbox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class CircuitActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.truth_table);

        TextView text = (TextView) findViewById(R.id.textView);
        Intent intent = getIntent();
        String prop = intent.getStringExtra(MyActivity.EXTRA_MESSAGE);
        BoolExpr bool = BoolExprParse.parse(prop);
        TruthTable truth = new TruthTable(bool);
        truth.getHTMLTable();
        String table = truth.toString();
        text.setText(Html.fromHtml(table));
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