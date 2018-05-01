package com.qit.android.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.constraint.ConstraintLayout;
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
import com.qit.R;
import com.qit.android.activity.InterviewCreationActivity;
import com.qit.android.activity.NewEventOrChoseEventActivity;
import com.qit.android.activity.QitActivity;
import com.qit.android.models.answer.Comment;
import com.qit.android.models.event.Event;
import com.qit.android.models.quiz.Interview;
import com.qit.android.rest.api.FirebaseEventinfoGodObj;
import com.qit.android.rest.dto.InterviewDTO;

import java.util.Date;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class InterviewAdapter extends RecyclerView.Adapter<InterviewAdapter.InterviewViewHolder> {

    private List<Interview> interviewDTOs;

    public static class InterviewViewHolder extends RecyclerView.ViewHolder {

        private CardView interviewCardView;

        private ImageView civQuestionnaire;
        private TextView tvTitle;
        private TextView tvTopic;
        private Button addInterviewText;
        private LinearLayout ll;
        private LinearLayout relativeLayout;
        private LinearLayout interviewCounterLinearLayout;
        private View view;

        private ImageButton menuBtn;
        private ImageButton editbtn;
        private ImageButton delBtn;

        private Interview interview;

        private InterviewViewHolder(final View view) {
            super(view);
            this.view = view;

            interviewCardView = view.findViewById(R.id.interviewCardView);

            ll = view.findViewById(R.id.add_interview_answe);
            relativeLayout = view.findViewById(R.id.relativeLayout);

            civQuestionnaire = view.findViewById(R.id.civInterview);
            civQuestionnaire.setImageResource(R.drawable.question_img);

            tvTitle = view.findViewById(R.id.tvInterviewTitle);
            tvTopic = view.findViewById(R.id.tvInterviewTopic);

            menuBtn = view.findViewById(R.id.menuImgBtnInterview);
            editbtn = view.findViewById(R.id.editImgBtnInterview);
            delBtn = view.findViewById(R.id.delImgBtnInterview);

            interviewCounterLinearLayout = view.findViewById(R.id.interviewCounterLinearLayout);

            addInterviewText = view.findViewById(R.id.answer_btn);
            Random rnd = new Random();
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            civQuestionnaire.setColorFilter(color);
        }
    }

    public InterviewAdapter(List<Interview> interviewDTOs) {
        this.interviewDTOs = interviewDTOs;
    }

    @Override
    public InterviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_interview_list, parent, false);

        return new InterviewViewHolder(itemView);
    }


    private boolean isAlreadyEditTextOpened = false;

    private int clickCounter;
    @Override
    public void onBindViewHolder(final InterviewViewHolder holder, final int position) {
        final Interview interviewDTO = interviewDTOs.get(position);
        holder.tvTitle.setText(interviewDTO.getSummary());
        holder.tvTopic.setText(interviewDTO.getDescription());
        final EditText editText = new EditText(holder.view.getContext());
        final Button saveTextBtn = new Button(holder.view.getContext());

        holder.interviewCounterLinearLayout.removeAllViews();
        TextView tempTv = new TextView(holder.view.getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                ((int) LinearLayout.LayoutParams.WRAP_CONTENT, (int) LinearLayout.LayoutParams.WRAP_CONTENT);
        tempTv.setText("There are "+interviewDTO.getComments().size()+" comments");
        tempTv.setTextSize((float) 14);
        tempTv.setLayoutParams(params);

        final ImageButton imb = new ImageButton(holder.view.getContext());
        imb.setBackground(holder.view.getContext().getResources().getDrawable(android.R.drawable.arrow_down_float));
        imb.setPadding(32, 0 , 0 ,0);
        imb.setLayoutParams(params);

        holder.interviewCounterLinearLayout.addView(tempTv);
        holder.interviewCounterLinearLayout.addView(imb);

        holder.interviewCounterLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //holder.interviewCounterLinearLayout.setVisibility(View.GONE);
                if(clickCounter == 0) {
                    clickCounter++;
                imb.setBackground(holder.view.getContext().getResources().getDrawable(android.R.drawable.arrow_up_float));
                    if (interviewDTO.getComments().size() != 0) {
                        holder.ll.removeAllViews();
                        for (int x = 0; x < interviewDTO.getComments().size(); x++) {

                            TextView tv = new TextView(holder.view.getContext());
                            tv.setText(interviewDTO.getComments().get(x).getComment());

                            TextView authorText = new TextView(holder.view.getContext());
                            authorText.setText(interviewDTO.getComments().get(x).getCommentCreatorName());

                            TextView borderText = new TextView(holder.view.getContext());
                            borderText.setText("______________________\n");

                            tv.setTextColor(holder.view.getResources().getColor(R.color.colorGreen));
                            holder.ll.addView(authorText);
                            holder.ll.addView(tv);
                            holder.ll.addView(borderText);

                        }
                    }
                } else {
                    clickCounter = 0;
                    holder.ll.removeAllViews();
                    imb.setBackground(holder.view.getContext().getResources().getDrawable(android.R.drawable.arrow_down_float));
                }
            }
        });

        holder.addInterviewText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View views) {
                Log.i("LOG", isAlreadyEditTextOpened + "");
                if (!isAlreadyEditTextOpened) {
                    isAlreadyEditTextOpened = true;
                    editText.setVisibility(View.VISIBLE);
                    saveTextBtn.setVisibility(View.VISIBLE);

                    editText.setTextColor(holder.view.getResources().getColor(R.color.colorDarkBlue));

                    saveTextBtn.setText("SAVE");
                    saveTextBtn.setBackgroundColor(holder.view.getResources().getColor(R.color.colorGreen));

                    editText.setHint("Place answer here");
                    editText.clearFocus();

                    try {
                        holder.relativeLayout.addView(editText);
                        holder.relativeLayout.addView(saveTextBtn);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    saveTextBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            isAlreadyEditTextOpened = false;
                            editText.setVisibility(View.GONE);
                            saveTextBtn.setVisibility(View.GONE);

                            TextView tv = new TextView(view.getContext());
                            tv.setText(editText.getText().toString());

                            TextView authorText = new TextView(view.getContext());
                            authorText.setText(FirebaseEventinfoGodObj.getFirebaseUserFullName());

                            TextView borderText = new TextView(view.getContext());
                            borderText.setText("______________________\n");

                            tv.setTextColor(view.getResources().getColor(R.color.colorGreen));
                            holder.ll.addView(authorText);
                            holder.ll.addView(tv);
                            holder.ll.addView(borderText);

                            final FirebaseDatabase database = FirebaseDatabase.getInstance();
                            final DatabaseReference myRef = database.getReference("event/" + FirebaseEventinfoGodObj.getFirebaseCurrentEventName() + "/interviewsList/" + (interviewDTOs.size()-1-position));
                            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    Interview interview = dataSnapshot.getValue(Interview.class);
                                    Comment comment = new Comment();
                                    comment.setComment(editText.getText().toString());
                                    comment.setCommentCreatorName(FirebaseEventinfoGodObj.getFirebaseUserFullName());
                                    comment.setDateOfComment(new Date(System.currentTimeMillis()));
                                    interview.getComments().add(comment);
                                    myRef.setValue(interview);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Log.i("ERROR", databaseError.getDetails());
                                }
                            });
                        }
                    });
                } else {
                    editText.setVisibility(View.GONE);
                    saveTextBtn.setVisibility(View.GONE);
                    isAlreadyEditTextOpened = false;
                }
            }
        });


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
            }
        });

        holder.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference myRef = database.getReference("event"+"/"+ FirebaseEventinfoGodObj.getFirebaseCurrentEventName());
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                            Event event = dataSnapshot.getValue(Event.class);
                            event.getInterviewsList().remove(event.getInterviewsList().size()-1-position);
                            DatabaseReference myRefTemp = database.getReference("event"+"/"+ FirebaseEventinfoGodObj.getFirebaseCurrentEventName()+"/interviewsList/");
                            myRefTemp.removeValue();
                            myRefTemp.setValue(event.getInterviewsList());
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
                holder.view.getContext().startActivity(new Intent(holder.view.getContext(), NewEventOrChoseEventActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return interviewDTOs.size();
    }

}
