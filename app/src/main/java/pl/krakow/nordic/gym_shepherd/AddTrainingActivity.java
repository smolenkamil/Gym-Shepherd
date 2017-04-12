package pl.krakow.nordic.gym_shepherd;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddTrainingActivity extends AppCompatActivity {
    private LinearLayout linearLayout;
    private Button addExerciseButton;
    private Button saveTrainingButton;
    private Button discardTrainingButton;
    private EditText trainingName;
    private List<EditText> exercisesArray;

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
        linearLayout = (LinearLayout) findViewById(R.id.linearLayoutInnerId);
        addExerciseButton = (Button) findViewById(R.id.addExerciseButton);
        saveTrainingButton = (Button) findViewById(R.id.saveTrainingButton);
        discardTrainingButton = (Button) findViewById(R.id.discardTrainingButton);
        exercisesArray = new ArrayList<>();
        trainingName = (EditText) findViewById(R.id.trainingName);
        data = getSharedPreferences("training" + trainingId, Context.MODE_PRIVATE);
        dataExecutor = data.edit();
    }

    public void addExerciseButtonClick(View view) {
        EditText exercise = new EditText(this);
        exercise.setHint("Ćwiczenie, np. Wyciskanie 3x12");
        exercise.setMaxLines(1);
        exercise.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES); // 61 - code for textPersonName

        linearLayout.removeView(addExerciseButton);
        linearLayout.addView(exercise);
        linearLayout.addView(addExerciseButton);

        exercise.requestFocus();
        exercisesArray.add(exercise);
        // TODO: Ustawić scrollView aby przycisk addExerciseButton był zawsze widoczny nad klawiaturą
    }


    public void saveTrainingButtonClick(View view) {
        dataExecutor.putString("trainingname", trainingName.getText().toString());
        int i = 1;
        for (EditText exercise : exercisesArray) {
            dataExecutor.putString("exercise" + i, exercise.getText().toString());
            i++;
        }
        dataExecutor.apply();
        Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
        saveTrainingId();
        Intent intent = new Intent(AddTrainingActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void discardTrainingButtonClick(View view) {
        Intent intent = new Intent(AddTrainingActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void saveTrainingId() {
        data = getSharedPreferences("different_values" , Context.MODE_PRIVATE);
        trainingId++;
        dataExecutor = data.edit();
        dataExecutor.putInt("trainingId", trainingId);
        dataExecutor.apply();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Zapisz")
                .setMessage("Zapisać wprowadzone zmiany?")
                .setPositiveButton("Zapisz", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        saveTrainingButton.performClick();
                    }
                })
                .setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        discardTrainingButton.performClick();
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
