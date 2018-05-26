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

import com.loopeer.shadow.ShadowView;
import com.qit.R;
import com.qit.android.activity.GraphicActivity;
import com.qit.android.activity.QitActivity;
import com.qit.android.activity.QuestionnaireAnswersActivity;
import com.qit.android.models.quiz.Questionnaire;
import com.qit.android.rest.api.FirebaseEventinfoGodObj;

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

        //public int rImagesCivQuestionnaire[] = {R.drawable.qiz_img_1, R.drawable.qiz_img_2, R.drawable.qiz_img_3, R.drawable.qiz_img_4, R.drawable.qiz_img_5, R.drawable.qiz_img_6, R.drawable.qiz_img_7};

        public QuestionnaireViewHolder(View view) {
            super(view);

            civQuestionnaire = view.findViewById(R.id.civQuestionnaire);
            civQuestionnaire.setImageResource(R.drawable.question_img);

            cardView = view.findViewById(R.id.cardView);

            menuBtn = view.findViewById(R.id.menuImgBtn);
            statBtn = view.findViewById(R.id.statImgBtn);
            editbtn = view.findViewById(R.id.editImgBtn);
            delBtn = view.findViewById(R.id.delImgBtn);

            tvTitle = view.findViewById(R.id.tvQuestionnaireTitle);
            tvTopic = view.findViewById(R.id.tvQuestionnaireTopic);
            context = view.getContext();

            Random rnd = new Random();
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            civQuestionnaire.setColorFilter(color);
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
                QitActivity.isShowFab = false;
                QitActivity.mFab.setVisibility(View.GONE);
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
                FirebaseEventinfoGodObj.setFirebaseCurrentQuestion(position);
                context.startActivity(intent);
                Activity activity = (Activity) context;
                activity.finish();
            }
        });

        holder.menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.statBtn.getVisibility() == View.GONE) {
                    //holder.editbtn.setVisibility(View.VISIBLE);
                    holder.statBtn.setVisibility(View.VISIBLE);
                    holder.delBtn.setVisibility(View.VISIBLE);
                } else {
                    //holder.editbtn.setVisibility(View.GONE);
                    holder.statBtn.setVisibility(View.GONE);
                    holder.delBtn.setVisibility(View.GONE);
                }
            }
        });

        holder.statBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseEventinfoGodObj.setFirebaseCurrentQuestion(position);
                context.startActivity(new Intent(context, GraphicActivity.class));
                Activity activity = (Activity) context;
                activity.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return questionnaires.size();
    }
}
