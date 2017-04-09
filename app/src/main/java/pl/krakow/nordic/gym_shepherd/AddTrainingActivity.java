package pl.krakow.nordic.gym_shepherd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class AddTrainingActivity extends AppCompatActivity {
    public ScrollView scrollView;
    public LinearLayout linearLayout;
    public Button addExerciseButton;
    public EditText trainingName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_training);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayoutInnerId);
        addExerciseButton = (Button) findViewById(R.id.addExerciseButton);
        scrollView = (ScrollView) findViewById(R.id.scrollViewId);
        trainingName = (EditText) findViewById(R.id.trainingName);
        addExerciseButton.performClick();
        trainingName.requestFocus();
    }

    public void addExerciseButtonClick(View view) {
        EditText exercise = new EditText(this);
        exercise.setHint("Ćwiczenie, np. Wyciskanie 3x12");
        exercise.setLines(1);
        exercise.setMaxLines(1);
        exercise.setInputType(4001); // 61 - code for textPersonName
        linearLayout.removeView(addExerciseButton);
        linearLayout.addView(exercise);
        linearLayout.addView(addExerciseButton);
        exercise.requestFocus();
        // TODO: Ustawić scrollView aby przycisk addExerciseButton był zawsze widoczny nad klawiaturą
    }
}
