package com.qit.android.adapters;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qit.R;
import com.qit.android.activity.QitActivity;
import com.qit.android.activity.QuestionnaireAnswersActivity;
import com.qit.android.models.quiz.Questionnaire;
import com.qit.android.rest.api.FirebaseEventinfoGodObj;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class QuestionnaireAdapter extends RecyclerView.Adapter<QuestionnaireAdapter.QuestionnaireViewHolder> {

    private List<Questionnaire> questionnaires;
    private Context context;

    public class QuestionnaireViewHolder extends RecyclerView.ViewHolder {
        public ImageView civQuestionnaire;
        public TextView tvTitle;
        public TextView tvTopic;

        public int rImagesCivQuestionnaire[] = {R.drawable.qiz_img_1, R.drawable.qiz_img_2, R.drawable.qiz_img_3, R.drawable.qiz_img_4, R.drawable.qiz_img_5, R.drawable.qiz_img_6, R.drawable.qiz_img_7};

        public QuestionnaireViewHolder(View view) {
            super(view);
            civQuestionnaire = view.findViewById(R.id.civQuestionnaire);
            civQuestionnaire.setImageResource(rImagesCivQuestionnaire[(int) (Math.random() * 7)]);

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
                FirebaseEventinfoGodObj.setFirebaseCurrentQuestion(position);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return questionnaires.size();
    }
}
