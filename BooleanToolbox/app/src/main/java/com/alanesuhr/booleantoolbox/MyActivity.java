package com.alanesuhr.booleantoolbox;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MyActivity extends Activity {
public static String EXTRA_MESSAGE = "pie";


    public String prop ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        Button buttonA = (Button)findViewById(R.id.button_A);
        Button buttonB = (Button)findViewById(R.id.button_B);
        Button buttonC = (Button)findViewById(R.id.button_C);
        Button buttonD = (Button)findViewById(R.id.button_D);
        Button buttonW = (Button)findViewById(R.id.button_W);
        Button buttonX = (Button)findViewById(R.id.button_X);
        Button buttonY = (Button)findViewById(R.id.button_Y);
        Button buttonZ = (Button)findViewById(R.id.button_Z);
        Button buttonAnd = (Button)findViewById(R.id.button_And);
        Button buttonOr = (Button)findViewById(R.id.button_Or);
        Button buttonXor = (Button)findViewById(R.id.button1_Xor);
        Button buttonOP = (Button)findViewById(R.id.button_Op);
        Button buttonCP = (Button)findViewById(R.id.button_Cp);
        Button buttonTable = (Button)findViewById(R.id.button_Table);
        Button buttonCircuit = (Button)findViewById(R.id.button_Circuit);
        final TextView text = (TextView)findViewById(R.id.textView);
        Button buttonF = (Button)findViewById(R.id.button_F);
        Button buttonT = (Button)findViewById(R.id.button_T);
        Button buttonBack = (Button)findViewById(R.id.button_Back);
        Button buttonClear = (Button)findViewById(R.id.button_Clear);
        Button buttonNot = (Button)findViewById(R.id.button_Not);
        Button buttonNor = (Button)findViewById(R.id.button_Nor);
        Button buttonNand = (Button)findViewById(R.id.button_Nand);
        final Intent intent = new Intent(this, TableActivity.class);
        final Intent intent2 = new Intent(this, CircuitActivity.class);

        buttonA.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               text.setText(prop+"A");
                prop = text.getText().toString();
            }
        });
        buttonB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                text.setText(prop+"B");
                prop = text.getText().toString();
            }
        });
        buttonC.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                text.setText(prop+"C");
                prop = text.getText().toString();
            }
        });
        buttonD.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                text.setText(prop+"D");
                prop = text.getText().toString();
            }
        });
        buttonW.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                text.setText(prop+"W");
                prop = text.getText().toString();
            }
        });
        buttonY.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                text.setText(prop+"Y");
                prop = text.getText().toString();
            }
        });
        buttonX.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                text.setText(prop+"X");
                prop = text.getText().toString();
            }
        });

        buttonZ.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                text.setText(prop+"Z");
                prop = text.getText().toString();
            }
        });
        buttonAnd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                text.setText(prop+"*");
                prop = text.getText().toString();
            }
        });
        buttonXor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                text.setText(prop+"@");
                prop = text.getText().toString();
            }
        });
        buttonOr.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                text.setText(prop+"+");
                prop = text.getText().toString();
            }
        });
        buttonOP.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                text.setText(prop+"(");
                prop = text.getText().toString();
            }
        });
        buttonCP.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                text.setText(prop+")");
                prop = text.getText().toString();
            }
        });
        buttonClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                text.setText(prop+")");
                prop = "";
                text.setText(prop);
            }
        });
        buttonNot.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                prop +="!";
                text.setText(prop);
            }
        });
        buttonCircuit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                prop= text.getText().toString();
                EXTRA_MESSAGE = prop;
                intent2.putExtra(EXTRA_MESSAGE,prop);
                startActivity(intent2);
            }
        });
        buttonNor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                prop= text.getText().toString();
                EXTRA_MESSAGE = prop;
                try {
                    BoolExpr bool = BoolExprParse.parse(prop);
                    bool = BoolExprManipulation.useNOROnly(bool);
                    prop = bool.toString();
                    EXTRA_MESSAGE =prop;
                }
                catch (RuntimeException e) {
                    Context context = getApplicationContext();
                    CharSequence texts = "You shan't";
                    int duration= Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context,texts,duration);
                    toast.show();
                }
                intent2.putExtra(EXTRA_MESSAGE,prop);
                startActivity(intent2);
            }
        });
        buttonNand.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                prop= text.getText().toString();
                EXTRA_MESSAGE = prop;
                try {
                    BoolExpr bool = BoolExprParse.parse(prop);
                    bool = BoolExprManipulation.useNANDOnly(bool);
                    prop = bool.toString();
                    EXTRA_MESSAGE =prop;
                }
                catch (RuntimeException e) {
                    Context context = getApplicationContext();
                    CharSequence texts = "You shan't";
                    int duration= Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context,texts,duration);
                    toast.show();
                }
                intent2.putExtra(EXTRA_MESSAGE,prop);
                startActivity(intent2);
            }
        });
        buttonTable.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                prop= text.getText().toString();
                EXTRA_MESSAGE = prop;
                intent.putExtra(EXTRA_MESSAGE,prop);
                startActivity(intent);
            }
        });
        buttonT.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                text.setText(prop+"1");
                prop = text.getText().toString();
            }
        });
        buttonBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (prop.length() >0) {
                    text.setText(prop.substring(0, prop.length() - 1));
                    prop = text.getText().toString();
                }
            }
        });
        buttonF.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                text.setText(prop+"0");
                prop = text.getText().toString();
            }
        });



    }
}