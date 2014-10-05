package com.alanesuhr.booleantoolbox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

/**
 * Created by lyndis on 10/4/14.
 */
public class TableActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.truth_table);
        WebView text = (WebView) findViewById(R.id.webView);
        Intent intent = getIntent();
        String prop = intent.getStringExtra(MyActivity.EXTRA_MESSAGE);
        BoolExpr bool = BoolExprParse.parse(prop);
        TruthTable truth = new TruthTable(bool);
        String table= truth.getHTMLTable();
        text.getSettings().setJavaScriptEnabled(true);
        text.loadDataWithBaseURL("notreal/",table,"text/html","UTF-8",null);
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