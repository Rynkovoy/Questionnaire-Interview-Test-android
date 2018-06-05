package com.qit.android.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.loopeer.shadow.ShadowView;
import com.qit.R;
import com.qit.android.activity.InterviewCreationActivity;
import com.qit.android.activity.InterviewCustomChatActivity;
import com.qit.android.activity.NewEventOrChoseEventActivity;
import com.qit.android.activity.QitActivity;
import com.qit.android.activity.QuestionnaireAnswersActivity;
import com.qit.android.fragments.InterviewTabFragment;
import com.qit.android.models.answer.Comment;
import com.qit.android.models.event.Event;
import com.qit.android.models.quiz.Interview;
import com.qit.android.models.quiz.Questionnaire;
import com.qit.android.models.user.User;
import com.qit.android.rest.api.FirebaseEventinfoGodObj;
import com.qit.android.rest.dto.InterviewDTO;
import com.qit.android.utils.BtnClickAnimUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class InterviewAdapter extends RecyclerView.Adapter<InterviewAdapter.InterviewViewHolder> {

    private List<Interview> interviewDTOs = new ArrayList<>();
//    public static boolean isAddedCard = true;

    public static class InterviewViewHolder extends RecyclerView.ViewHolder {

        private ShadowView interviewCardView;
        private ImageView civQuestionnaire;
        private TextView tvTitle;
        private TextView tvTopic;
        private LinearLayout interviewCounterLinearLayout;
        private View view;

        private ImageButton menuBtn;
        private ImageButton editbtn;
        private ImageButton delBtn;

//        private Interview interview;

        private int[] res = {R.drawable.question_img_1, R.drawable.question_img_2, R.drawable.question_img_3, R.drawable.question_img_4, R.drawable.question_img_5, R.drawable.question_img_6, R.drawable.question_img_7,
                R.drawable.question_img_8, R.drawable.question_img_9, R.drawable.question_img_10, R.drawable.question_img_11, R.drawable.question_img_12, R.drawable.question_img_13, R.drawable.question_img_14};

        private InterviewViewHolder(final View view) {
            super(view);
            this.view = view;
            interviewCardView = view.findViewById(R.id.interviewCardView);
            civQuestionnaire = view.findViewById(R.id.civInterview);

            tvTitle = view.findViewById(R.id.tvInterviewTitle);
            tvTopic = view.findViewById(R.id.tvInterviewTopic);

            menuBtn = view.findViewById(R.id.menuImgBtnInterview);
            editbtn = view.findViewById(R.id.editImgBtnInterview);
            delBtn = view.findViewById(R.id.delImgBtnInterview);

            interviewCounterLinearLayout = view.findViewById(R.id.interviewCounterLinearLayout);

            civQuestionnaire.setImageResource(res[(int) (Math.random() * 14)]);
            //Random rnd = new Random();
            //int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            //civQuestionnaire.setColorFilter(color);
        }
    }

    public InterviewAdapter(List<Interview> interviewDTOs) {
//        if (interviewDTOs.size() == 0) {
//            InterviewAdapter.isAddedCard = false;
//            Interview interview = new Interview();
//            interview.setSummary("Ouh....");
//            interview.setDescription("Somewhere one cat is sad because you don`t have any interviews");
//            interview.setAuthor(new User());
//            interviewDTOs.add(interview);
//        }
        this.interviewDTOs = interviewDTOs;
    }

    @Override
    public InterviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_interview_list, parent, false);
        return new InterviewViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final InterviewViewHolder holder, final int position) {
        final Interview interviewDTO = interviewDTOs.get(position);
        holder.tvTitle.setText(interviewDTO.getSummary());
        holder.tvTopic.setText(interviewDTO.getDescription());
//
//        if (!isAddedCard) {
//            holder.civQuestionnaire.setImageResource(R.drawable.sad_kitty);
//            holder.civQuestionnaire.setColorFilter(null);
//        } else {
//
        try {
            if (!interviewDTO.getAuthor().getLogin().equals(FirebaseEventinfoGodObj.getFirebaseUSerEmail())) {
                holder.menuBtn.setVisibility(View.GONE);
                //QitActivity.isShowFab = false;
                //QitActivity.mFab.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        holder.interviewCounterLinearLayout.removeAllViews();
        TextView tempTv = new TextView(holder.view.getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tempTv.setText("There are " + interviewDTO.getComments().size() + " comments");
        tempTv.setTextSize((float) 14);
        tempTv.setLayoutParams(params);

        holder.interviewCounterLinearLayout.addView(tempTv);

        holder.menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.editbtn.getVisibility() == View.GONE) {
                    holder.editbtn.setVisibility(View.VISIBLE);
                    holder.delBtn.setVisibility(View.VISIBLE);
                } else {
                    holder.editbtn.setVisibility(View.GONE);
                    holder.delBtn.setVisibility(View.GONE);
                }
            }
        });

        holder.editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.view.getContext(), InterviewCreationActivity.class);
                intent.putExtra("Position", position);
                holder.view.getContext().startActivity(intent);
                Activity a = (Activity) holder.view.getContext();
                a.finish();
            }
        });

        holder.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference myRef = database.getReference("event" + "/" + FirebaseEventinfoGodObj.getFirebaseCurrentEventName() + "/interviewsList/");
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        try {
                            interviewDTOs.remove(position);
                            List<Interview> qList = new ArrayList<>();
                            qList.addAll(interviewDTOs);
                            Collections.reverse(qList);
                            myRef.setValue(qList);
                            InterviewTabFragment.interviewAdapter.notifyDataSetChanged();
                            holder.menuBtn.performClick();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Snackbar.make(view, "Error while deleting, try later", Snackbar.LENGTH_LONG).show();
                        }
                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.i("ERROR", databaseError.getDetails());
                    }
                });
            }
        });

        holder.interviewCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(view.getContext(), InterviewCustomChatActivity.class);
                    intent.putExtra("Interview", (interviewDTOs.size() - 1 - position));
                    intent.putExtra("InterviewObj", interviewDTOs.get(position));
                    holder.view.getContext().startActivity(intent);
                    Activity a = (Activity) holder.view.getContext();
                    a.finish();
                } catch (Exception e) {
                    e.printStackTrace();
                    Snackbar.make(view, "Error while opening chat, try later", Snackbar.LENGTH_LONG).show();
                }

            }
        });
    }
//    }

    @Override
    public int getItemCount() {
        return interviewDTOs.size();
    }

}
