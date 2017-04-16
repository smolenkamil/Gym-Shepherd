package pl.krakow.nordic.gym_shepherd;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ScrollView scrollView;
    private LinearLayout linearLayout;
    private int trainingId;
    private SharedPreferences data;
    private Button training;
    private static boolean dontSplash = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (dontSplash == false) {
            startActivity(new Intent(MainActivity.this, SplashScreen.class));
            dontSplash = true;
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        linearLayout = (LinearLayout) findViewById(R.id.linearLayoutMain);
        scrollView = (ScrollView) findViewById(R.id.scrollViewMain);
        try {
            getTrainingId();
        } catch (Exception e) {
            Log.i("error", e.getMessage().toString());
        }
        createTrainingButtons(scrollView);

    }

    private void createTrainingButtons(View view) {
        for (int i = 0; i < trainingId; i++) {
            //TODO: jesli pusty rekord 'trainingId' na wypadek usunięcia rekordu
            data = getSharedPreferences("training" + i, Context.MODE_PRIVATE);
            training = new Button(this);
            final String id = "training" + i;
            training.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getBaseContext(), "ok", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, TrainingActivity.class);
                    intent.putExtra("id",id);
                    startActivity(intent);
                }
            });
            LinearLayout.LayoutParams parameter = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            parameter.gravity = Gravity.CENTER;
            parameter.width = LinearLayout.LayoutParams.WRAP_CONTENT;
            training.setLayoutParams(parameter);
            training.setText(data.getString("trainingname", "empty"));
            linearLayout.addView(training);
        }
        try {
            training.requestFocus();
        } catch (Exception e) {
            Log.i("error", e.getMessage().toString());
        }
    }

    private void getTrainingId() {
        data = getSharedPreferences("different_values", Context.MODE_PRIVATE);
        trainingId = data.getInt("trainingId", 0);
    }

    @Override
    protected void onStop() {
        dontSplash = true;
        super.onStop();
    }

    public void addTrainingFABClick(View view) {
        Intent intent = new Intent(this, AddTrainingActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        this.finishAffinity();
        System.exit(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

