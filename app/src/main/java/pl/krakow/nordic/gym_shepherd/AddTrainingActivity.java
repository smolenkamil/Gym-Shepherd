package pl.krakow.nordic.gym_shepherd;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

public class AddTrainingActivity extends AppCompatActivity {
    public ScrollView scrollView;
    public LinearLayout linearLayout;
    public Button addExerciseButton;
    public EditText trainingName;
    private EditText exercise;
    private Button saveTrainingButton;
    private int exerciseId;
    private int trainingId;
    private SharedPreferences data;
    private SharedPreferences.Editor dataExecutor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_training);
        init();

        addExerciseButton.performClick();
        trainingName.requestFocus();
    }

    private void init() {
        exerciseId = 1;
        linearLayout = (LinearLayout) findViewById(R.id.linearLayoutInnerId);
        addExerciseButton = (Button) findViewById(R.id.addExerciseButton);
        saveTrainingButton = (Button) findViewById(R.id.saveTrainingButton);
        scrollView = (ScrollView) findViewById(R.id.scrollViewId);
        trainingName = (EditText) findViewById(R.id.trainingName);
        data = getSharedPreferences("training" + trainingId, Context.MODE_PRIVATE);
        dataExecutor = data.edit();
    }

    public void addExerciseButtonClick(View view) {
        exercise = new EditText(this);
        exercise.setId(exerciseId); //set id of exercise
        exercise.setHint("Ćwiczenie, np. Wyciskanie 3x12");
        exercise.setLines(1);
        exercise.setMaxLines(1);
        exercise.setInputType(4001); // 61 - code for textPersonName
        linearLayout.removeView(addExerciseButton);
        linearLayout.removeView(saveTrainingButton);
        linearLayout.addView(exercise);
        linearLayout.addView(addExerciseButton);
        linearLayout.addView(saveTrainingButton);
        exercise.requestFocus();
        exerciseId++;
        // TODO: Ustawić scrollView aby przycisk addExerciseButton był zawsze widoczny nad klawiaturą
    }


    public void saveExerciseButtonClick(View view) {
        try {
            dataExecutor.putString("trainingname", trainingName.getText().toString() );
            for (int i = 1; i< exerciseId ; i++){
                EditText currentEx = (EditText) findViewById(i);
                dataExecutor.putString("exercise"+i, currentEx.getText().toString());
            }
            dataExecutor.apply();;
            Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            Log.i("throw", e.toString());
        }
        Intent intent = new Intent(AddTrainingActivity.this, MainActivity.class);
        startActivity(intent);
    }


    //ZAPIS - do przeklejenia
    public void showMeEverything(View view) {
        trainingName.setText(data.getString("trainingname", "empty"));
        for (int i = 1; i< exerciseId ; i++){
            EditText currentEx = (EditText) findViewById(i);
            currentEx.setText(data.getString("exercise"+i, "empty"));
        }

        Toast.makeText(this, "Yeah!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Zapis")
                .setMessage("Napewno chcesz wyjść bez zapisu?")
                .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(AddTrainingActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Zapisz i Wyjdź", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        saveExerciseButtonClick(scrollView);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
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
