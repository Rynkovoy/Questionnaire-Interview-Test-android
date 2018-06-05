package com.qit.android.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.qit.R;
import com.qit.android.models.answer.Answer;
import com.qit.android.models.answer.Variant;
import com.qit.android.models.question.Question;
import com.qit.android.models.question.QuestionType;
import com.qit.android.models.quiz.Questionnaire;
import com.qit.android.rest.api.FirebaseEventinfoGodObj;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GraphicActivity extends AppCompatActivity {

    private TextView headerTv;
    private TextView headerTvallText;
    public Questionnaire questionnaire;

    private LinearLayout pieLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphic);

        questionnaire = (Questionnaire) getIntent().getSerializableExtra("Questionnaire");

        pieLayout = findViewById(R.id.pieLayout);
        headerTv = findViewById(R.id.header_text_graphic);
        headerTvallText = findViewById(R.id.header_text_text);
        createGraphics();
    }


    public void createGraphics() {

        int[] counterRadio = new int[2];
        int[] counterCheckbox = new int[0];
        List<String> counterStrings = new ArrayList<>();

        for (int x = 0; x < questionnaire.getQuestionList().size(); x++) {

            CardView cardView = new CardView(this);
            LinearLayout innerLL = new LinearLayout(this);
            innerLL.setOrientation(LinearLayout.VERTICAL);

            TextView tvHead = new TextView(this);
            tvHead.setPadding(32,32,0,0);
            tvHead.setText("Question: "+ questionnaire.getQuestionList().get(x).getText());
            innerLL.addView(tvHead);


            if (questionnaire.getQuestionList().get(x).getQuestionType().equals(QuestionType.CHECKBOX.toString())) {
                counterCheckbox = new int[questionnaire.getQuestionList().get(x).getVariants().size()];
            } else if (questionnaire.getQuestionList().get(x).getQuestionType().equals(QuestionType.RADIO.toString())){
                counterRadio = new int[questionnaire.getQuestionList().get(x).getVariants().size()];
            } else if (questionnaire.getQuestionList().get(x).getQuestionType().equals(QuestionType.DETAILED.toString())){
                counterStrings = new ArrayList<>();
            }


            for (int y = 0; y < questionnaire.getQuestionList().get(x).getVariants().size(); y++) {
                //counterRadio = new int[1];

                tvHead = new TextView(this);
                //if (!questionnaire.getQuestionList().get(x).getVariants().get(y).getText().equalsIgnoreCase("null")) {
                    //tvHead.setText("DETAILED: " + questionnaire.getQuestionList().get(x).getVariants().get(y).getText());
                //}
                innerLL.addView(tvHead);

                for (int i = 0; i < questionnaire.getQuestionList().get(x).getVariants().get(y).getAnswers().size(); i++) {

                    Answer answer = questionnaire.getQuestionList().get(x).getVariants().get(y).getAnswers().get(i);

                    if (answer.getQuestionType().equals(QuestionType.RADIO)) {
                        if (answer.isAnswerBool()) {
                            counterRadio[y] += 1;
                        }
//                        else {
//                            counterRadio[1] += 1;
//                        }
                    } else if (answer.getQuestionType().equals(QuestionType.CHECKBOX)) {
                        if (answer.isAnswerBool()) {
                            counterCheckbox[y] += 1;
                        }
                    } else if (answer.getQuestionType().equals(QuestionType.DETAILED)) {
                        //Log.i("LOG_STR", answer.getAnswerStr());
                        counterStrings.add(answer.getAnswerStr());
                    }
                }
            }

            if (questionnaire.getQuestionList().get(x).getQuestionType().equals(QuestionType.RADIO.toString())) {

                PieChart chart = new PieChart(GraphicActivity.this);
                chart.getDescription().setText("");
                List<PieEntry> entries = new ArrayList<PieEntry>();

                for (int q = 0; q < counterRadio.length; q++) {
                    if ((float) counterRadio[q]!=0) {
                        entries.add(new PieEntry((float) counterRadio[q], questionnaire.getQuestionList().get(x).getVariants().get(q).getText()));
                    }
                }

                PieDataSet dataSet = new PieDataSet(entries, ""); // add entries to dataset

                int[] colors = new int[entries.size()];
                for (int q = 0; q < entries.size(); q++) {
                    Random rnd = new Random();
                    int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                    colors[q] = color;
                }

                dataSet.setColors(colors);
                dataSet.setValueTextColor(getResources().getColor(R.color.white));
                dataSet.setValueTextSize(12);

                PieData lineData = new PieData(dataSet);
                chart.setData(lineData);
                chart.invalidate(); // refresh
                //chart.setPadding(32,32,32,32);

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 800);

                innerLL.addView(chart,lp);
            }


            if (questionnaire.getQuestionList().get(x).getQuestionType().equals(QuestionType.CHECKBOX.toString())) {
                BarChart barChart = new BarChart(GraphicActivity.this);
                barChart.getDescription().setText("");
                List<BarEntry> entries = new ArrayList<BarEntry>();

                String s = "";
                for (int q = 0; q < counterCheckbox.length; q++) {
                    entries.add(new BarEntry(q, (float) counterCheckbox[q]));
                    s = s+questionnaire.getQuestionList().get(x).getVariants().get(q).getText()+"; ";
                }

                barChart.getAxisLeft().setLabelCount(5, true);
                barChart.getAxisLeft().setYOffset(1f);
                barChart.getXAxis().setTextColor(getResources().getColor(R.color.black));

                BarDataSet dataSet = new BarDataSet(entries, s); // add entries to dataset
                barChart.getAxisRight().setDrawLabels(false);
                barChart.getXAxis().setDrawLabels(false);

                int[] colors = new int[entries.size()];
                for (int q = 0; q < entries.size(); q++) {
                    Random rnd = new Random();
                    int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                    colors[q] = color;
                }

                dataSet.setColors(colors);
                dataSet.setValueTextColor(getResources().getColor(R.color.black));
                dataSet.setValueTextSize(12);

                BarData lineData = new BarData(dataSet);
                barChart.setData(lineData);
                barChart.setFitBars(true);

                barChart.invalidate(); // refresh
                //barChart.setPadding(32,32,32,32);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 800);

                innerLL.addView(barChart, lp);
            }

            if (questionnaire.getQuestionList().get(x).getQuestionType().equals(QuestionType.DETAILED.toString())) {
                LinearLayout lli = new LinearLayout(GraphicActivity.this);
                lli.setOrientation(LinearLayout.VERTICAL);

                TextView textView = new TextView(GraphicActivity.this);
                //textView.setText("DETAILED ANSEWRS");
                textView.setTypeface(null, Typeface.BOLD);

                LinearLayout.LayoutParams lpt = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);
                textView.setLayoutParams(lpt);

                lli.addView(textView);

                for (int q = 0; q < counterStrings.size(); q++) {

                    Random rnd = new Random();
                    int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

                    textView = new TextView(GraphicActivity.this);

                    textView.setText(q+1+". "+counterStrings.get(q) + "\n");
                    textView.setTextColor(color);
                    lli.addView(textView);
                }
                innerLL.addView(lli);
            }

            LinearLayout.LayoutParams cardViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            //cardView.setPadding(128,128,128,128);
            cardView.setLayoutParams(cardViewParams);
            cardView.setRadius(64);
            ViewGroup.MarginLayoutParams layoutParams =
                    (ViewGroup.MarginLayoutParams) cardView.getLayoutParams();
            layoutParams.setMargins(16, 16, 16, 16);
            innerLL.setPadding(32,32,32,32);
            cardView.requestLayout();

            pieLayout.addView(cardView);
            cardView.addView(innerLL);

            //cv.addView(ll);
            //pieLayout.addView(cv);

        }


        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference("event/" + FirebaseEventinfoGodObj.getFirebaseCurrentEventName() + "/questionLists/" + FirebaseEventinfoGodObj.getFirebaseCurrentQuestion());
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Questionnaire questionnaire = dataSnapshot.getValue(Questionnaire.class);
                headerTv.setText(questionnaire.getSummary());
                headerTvallText.setText(questionnaire.getDescription());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        this.startActivity(new Intent(this, QitActivity.class));
    }
}
