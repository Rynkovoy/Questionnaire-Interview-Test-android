package com.qit.android.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.qit.android.models.answer.Comment;
import com.qit.android.models.quiz.Interview;
import com.qit.android.rest.api.FirebaseEventinfoGodObj;
import com.qit.android.rest.dto.InterviewDTO;

import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class InterviewAdapter extends RecyclerView.Adapter<InterviewAdapter.InterviewViewHolder> {

    public List<Interview> interviewDTOs;

    public static class InterviewViewHolder extends RecyclerView.ViewHolder {

        public ImageView civQuestionnaire;
        public TextView tvTitle;
        public TextView tvTopic;
        public Button addInterviewText;
        public LinearLayout ll;
        public View view;

        public int rImagesCivQuestionnaire[] = {R.drawable.qiz_img_1, R.drawable.qiz_img_2, R.drawable.qiz_img_3, R.drawable.qiz_img_4, R.drawable.qiz_img_5, R.drawable.qiz_img_6, R.drawable.qiz_img_7};

        public InterviewViewHolder(final View view) {
            super(view);
            this.view = view;

            ll = view.findViewById(R.id.add_interview_answe);

            civQuestionnaire = view.findViewById(R.id.civInterview);
            civQuestionnaire.setImageResource(rImagesCivQuestionnaire[(int) (Math.random() * 7)]);

            tvTitle = view.findViewById(R.id.tvInterviewTitle);
            tvTopic = view.findViewById(R.id.tvInterviewTopic);

            addInterviewText = view.findViewById(R.id.answer_btn);

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

    @Override
    public void onBindViewHolder(final InterviewViewHolder holder, final int position) {
        Interview interviewDTO = interviewDTOs.get(position);
        holder.tvTitle.setText(interviewDTO.getSummary());
        holder.tvTopic.setText(interviewDTO.getDescription());

        holder.addInterviewText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View views) {
                final EditText editText = new EditText(holder.view.getContext());
                editText.setTextColor(holder.view.getResources().getColor(R.color.colorDarkBlue));
                final Button saveTextBtn = new Button(holder.view.getContext());
                saveTextBtn.setText("SAVE");
                saveTextBtn.setBackgroundColor(holder.view.getResources().getColor(R.color.colorGreen));

                holder.ll.addView(editText);
                holder.ll.addView(saveTextBtn);

                saveTextBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
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
                        final DatabaseReference myRef = database.getReference("event/" + FirebaseEventinfoGodObj.getFirebaseCurrentEventName() + "/interviewsList/" + position);
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
            }
        });

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
    }

    @Override
    public int getItemCount() {
        return interviewDTOs.size();
    }

}
