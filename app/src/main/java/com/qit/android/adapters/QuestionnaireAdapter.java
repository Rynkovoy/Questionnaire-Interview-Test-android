package com.qit.android.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.nfc.Tag;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.loopeer.shadow.ShadowView;
import com.qit.R;
import com.qit.android.activity.GraphicActivity;
import com.qit.android.activity.QitActivity;
import com.qit.android.activity.QuestionnaireAnswersActivity;
import com.qit.android.fragments.QuestionnaireTabFragment;
import com.qit.android.models.question.Question;
import com.qit.android.models.quiz.Questionnaire;
import com.qit.android.rest.api.FirebaseEventinfoGodObj;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class QuestionnaireAdapter extends RecyclerView.Adapter<QuestionnaireAdapter.QuestionnaireViewHolder> {

    public List<Questionnaire> questionnaires;
    private Context context;

    public class QuestionnaireViewHolder extends RecyclerView.ViewHolder {
        private ImageView civQuestionnaire;
        private TextView tvTitle;
        private TextView tvTopic;

        private ImageButton menuBtn;
        private ImageButton statBtn;
        private ImageButton editbtn;
        private ImageButton delBtn;

        private ShadowView cardView;
        private int [] res = {R.drawable.question_img_1, R.drawable.question_img_2, R.drawable.question_img_3, R.drawable.question_img_4, R.drawable.question_img_5, R.drawable.question_img_6, R.drawable.question_img_7,
                R.drawable.question_img_8, R.drawable.question_img_9, R.drawable.question_img_10, R.drawable.question_img_11, R.drawable.question_img_12, R.drawable.question_img_13, R.drawable.question_img_14};

        //public int rImagesCivQuestionnaire[] = {R.drawable.qiz_img_1, R.drawable.qiz_img_2, R.drawable.qiz_img_3, R.drawable.qiz_img_4, R.drawable.qiz_img_5, R.drawable.qiz_img_6, R.drawable.qiz_img_7};

        public QuestionnaireViewHolder(View view) {
            super(view);

            civQuestionnaire = view.findViewById(R.id.civQuestionnaire);
            civQuestionnaire.setImageResource(res[(int)(Math.random()*14)]);
            //civQuestionnaire.setImageResource(R.drawable.question_img);

            cardView = view.findViewById(R.id.cardView);

            menuBtn = view.findViewById(R.id.menuImgBtn);
            statBtn = view.findViewById(R.id.statImgBtn);
            editbtn = view.findViewById(R.id.editImgBtn);
            delBtn = view.findViewById(R.id.delImgBtn);

            tvTitle = view.findViewById(R.id.tvQuestionnaireTitle);
            tvTopic = view.findViewById(R.id.tvQuestionnaireTopic);
            context = view.getContext();

        }
    }

    public QuestionnaireAdapter(List<Questionnaire> questionnaires) {
        this.questionnaires = questionnaires;
    }

    @Override
    public QuestionnaireViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_questionnaire_list, parent, false);
        return new QuestionnaireViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final QuestionnaireViewHolder holder, final int position) {
        Questionnaire questionnaire = questionnaires.get(position);
        holder.tvTitle.setText(questionnaire.getSummary());

            if (!questionnaire.getAuthor().getLogin().equals(FirebaseEventinfoGodObj.getFirebaseUSerEmail())){
                holder.menuBtn.setVisibility(View.GONE);
            }

        if (!questionnaire.getDescription().equalsIgnoreCase("")) {
            holder.tvTopic.setText(questionnaire.getDescription());
        } else {
            holder.tvTopic.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, QuestionnaireAnswersActivity.class);
                intent.putExtra("Questionnaire", questionnaires.get(position));
                FirebaseEventinfoGodObj.setFirebaseCurrentQuestion(questionnaires.size()-1-position);
                context.startActivity(intent);
                Activity activity = (Activity) context;
                activity.finish();
            }
        });

        holder.menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.statBtn.getVisibility() == View.GONE) {
                    holder.statBtn.setVisibility(View.VISIBLE);
                    holder.delBtn.setVisibility(View.VISIBLE);
                } else {
                    holder.statBtn.setVisibility(View.GONE);
                    holder.delBtn.setVisibility(View.GONE);
                }
            }
        });

        holder.statBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, GraphicActivity.class);
                intent.putExtra("Questionnaire", questionnaires.get(position));
                FirebaseEventinfoGodObj.setFirebaseCurrentQuestion(questionnaires.size()-1-position);
                context.startActivity(intent);
                Activity activity = (Activity) context;
                activity.finish();
            }
        });

        holder.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseEventinfoGodObj.setFirebaseCurrentQuestion(position);

                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference myRef = database.getReference("event");
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                            if (childDataSnapshot.getKey().equalsIgnoreCase(FirebaseEventinfoGodObj.getFirebaseCurrentEventName())) {
                                for (DataSnapshot child : childDataSnapshot.getChildren()) {
                                    if (child.getKey().equalsIgnoreCase("questionLists")) {
                                        questionnaires.remove(position);
                                        DatabaseReference myRef = database.getReference("event/"+FirebaseEventinfoGodObj.getFirebaseCurrentEventName()+"/questionLists");
                                        List<Questionnaire> qList = new ArrayList<>();
                                        qList.addAll(questionnaires);
                                        Collections.reverse(qList);
                                        myRef.setValue(qList);
                                        QuestionnaireTabFragment.questionnaireAdapter.notifyDataSetChanged();
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
    }

    @Override
    public int getItemCount() {
        return questionnaires.size();
    }
}
