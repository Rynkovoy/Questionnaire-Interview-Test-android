package com.qit.android.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.qit.R;
import com.qit.android.models.answer.Answer;
import com.qit.android.models.question.Question;
import com.qit.android.models.question.QuestionType;
import com.qit.android.models.quiz.Questionnaire;
import com.qit.android.rest.api.FirebaseEventinfoGodObj;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GraphicActivity extends AppCompatActivity {

    private TextView headerTv;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphic);

        headerTv = findViewById(R.id.header_text_graphic);
        scrollView = findViewById(R.id.scroll_graphics);

        createGraphics();


    }

    private List<Questionnaire> qList = new ArrayList<>();

    public void createGraphics() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference("event/" + FirebaseEventinfoGodObj.getFirebaseCurrentEventName() + "/questionLists/" + FirebaseEventinfoGodObj.getFirebaseCurrentQuestion() + "/answerList/");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<Answer> answerList = new ArrayList<>();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Answer answer = dataSnapshot1.getValue(Answer.class);
                    answerList.add(answer);
                }

                //Log.i("DATA_", counter[0]+"");

                try {

                    int[] counterRadio = new int[answerList.get(0).getResults().size()];

                    for (int x = 0; x < answerList.size(); x++) {
                        for (int y = 0; y < answerList.get(x).getResults().size(); y++) {

                            if (answerList.get(x).getResults().get(y).getQuestionType().equals(QuestionType.RADIO)) {
                                if (answerList.get(x).getResults().get(y).isAnswerBool()) {
                                    counterRadio[y] += 1;
                                }
                            }
                            else if (answerList.get(x).getResults().get(y).getQuestionType().equals(QuestionType.CHECKBOX)){
//                                if (answerList.get(x).getResults().get(y).isAnswerBool()) {
//                                    counterRadio[y] += 1;
//                                }
                            }
                        }

                    }

                    PieChart chart = (PieChart) findViewById(R.id.chart);
                    List<PieEntry> entries = new ArrayList<PieEntry>();

                    for (int x = 0; x < counterRadio.length; x++) {
                        //Log.i("DATA_", counter[x]+"");
                        entries.add(new PieEntry((float) counterRadio[x], ""));
                    }

                    PieDataSet dataSet = new PieDataSet(entries, "All Results"); // add entries to dataset

                    int[] colors = new int[entries.size()];
                    for (int x = 0; x < entries.size(); x++) {
                        Random rnd = new Random();
                        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                        colors[x] = color;
                    }
                    dataSet.setColors(colors);
                    dataSet.setValueTextColor(getResources().getColor(R.color.white));
                    dataSet.setValueTextSize(20);

                    PieData lineData = new PieData(dataSet);
                    chart.setData(lineData);
                    chart.invalidate(); // refresh

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}