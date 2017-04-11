package pl.krakow.nordic.gym_shepherd;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import static android.R.attr.id;

public class AddTrainingActivity extends AppCompatActivity {
    private ScrollView scrollView;
    private LinearLayout linearLayout;
    private Button addExerciseButton;
    private EditText trainingName;
    private EditText exercise;
    //private Button saveTrainingButton;
    private int exerciseId;
    private int trainingId;
    private SharedPreferences data;
    private SharedPreferences.Editor dataExecutor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_training);
        setTrainingId();
        init();

        addExerciseButton.performClick();
        trainingName.requestFocus();
    }

    private void setTrainingId() {
        data = getSharedPreferences("different_values", Context.MODE_PRIVATE);
        trainingId = data.getInt("trainingId", 0);

    }

    private void init() {
        exerciseId = 1;
        linearLayout = (LinearLayout) findViewById(R.id.linearLayoutInnerId);
        addExerciseButton = (Button) findViewById(R.id.addExerciseButton);
        scrollView = (ScrollView) findViewById(R.id.scrollViewId);
        trainingName = (EditText) findViewById(R.id.trainingName);
        data = getSharedPreferences("training" + trainingId, Context.MODE_PRIVATE);
        dataExecutor = data.edit();
    }

    public void addExerciseButtonClick(View view) {
        exercise = new EditText(this);
        exercise.setId(exerciseId); //set id of exercise
        exercise.setHint("Ćwiczenie, np. Wyciskanie 3x12");
        exercise.setMaxLines(1);
        exercise.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES); // 61 - code for textPersonName
        linearLayout.removeView(addExerciseButton);
        linearLayout.addView(exercise);
        linearLayout.addView(addExerciseButton);
        exercise.requestFocus();
        exerciseId++;
        // TODO: Ustawić scrollView aby przycisk addExerciseButton był zawsze widoczny nad klawiaturą
    }


    public void saveExerciseButtonClick(View view) {
        try {
            dataExecutor.putString("trainingname", trainingName.getText().toString());
            for (int i = 1; i < exerciseId; i++) {
                EditText currentEx = (EditText) findViewById(i);
                dataExecutor.putString("exercise" + i, currentEx.getText().toString());
            }
            dataExecutor.apply();
            Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.i("throw", e.toString());
        }
        saveTrainingId();
        Intent intent = new Intent(AddTrainingActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void discardExerciseButtonClick(View view) {
        //TODO: to be continued
    }

    private void saveTrainingId() {
        data = getSharedPreferences("different_values" , Context.MODE_PRIVATE);
        trainingId++;
        dataExecutor = data.edit();
        dataExecutor.putInt("trainingId", trainingId);
        dataExecutor.apply();
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
                .setMessage("Zapisać wprowadzone zmiany?")
                .setPositiveButton("Zapisz i wyjdź", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        saveExerciseButtonClick(scrollView);
                    }
                })
                .setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(AddTrainingActivity.this, MainActivity.class);
                        startActivity(intent);
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
