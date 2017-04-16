package pl.krakow.nordic.gym_shepherd;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class TrainingActivity extends AppCompatActivity {

    private SharedPreferences data;
    private TextView showTrainingName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
        String id;
        if(savedInstanceState == null) {
            Bundle extraData = getIntent().getExtras();
            if(extraData == null){
                id = null;
            } else {
                id = extraData.getString("id");
            }
        } else {
            id = (String) savedInstanceState.getSerializable("id");
        }
        data = getSharedPreferences(id, MODE_PRIVATE);
        showTrainingName = (TextView) findViewById(R.id.showMe);
        showTrainingName.setText(data.getString("trainingname", "empty"));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TrainingActivity.this, MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }
}
