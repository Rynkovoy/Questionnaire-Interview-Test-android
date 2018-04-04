package com.qit.android.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.qit.R;
import com.qit.android.activity.QuestionnaireAnswersActivity;
import com.qit.android.models.answer.Answer;
import com.qit.android.models.question.Question;
import com.qit.android.models.question.QuestionType;
import com.qit.android.models.quiz.Result;

import java.util.ArrayList;
import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    private List<Question> questionList;

    public static class QuestionViewHolder extends RecyclerView.ViewHolder {
        public TextView tvQuestion;

        public LinearLayout llCheckBox;
        public LinearLayout llRadioBox;
        public LinearLayout llDetailed;

        public CheckBox checkBoxPlace;
        public RadioGroup radioGroupPlace;
        public RadioButton radioButton;
        public EditText editText;

        Context context;

        public QuestionViewHolder(View view) {
            super(view);

            context = view.getContext();
            tvQuestion = view.findViewById(R.id.question_haeder);

            llCheckBox = view.findViewById(R.id.ll_check_box);
            llRadioBox = view.findViewById(R.id.ll_radio);
            llDetailed = view.findViewById(R.id.ll_detailed);

            checkBoxPlace = view.findViewById(R.id.checkBox_place);
            radioGroupPlace = view.findViewById(R.id.radio_group_place);
            radioButton = view.findViewById(R.id.radioButton_place);
            editText = view.findViewById(R.id.detailed_edit_text);

        }
    }

    public QuestionAdapter(List<Question> questionList) {
        this.questionList = questionList;
    }

    @Override
    public QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_question_questionnarie_answer, parent,false);

        return new QuestionViewHolder(itemView);
    }


    //TODO need tO BE ADDED LISTENERRS AND RESPONSE TO FIREBASE WITH ALL ANSWERS
    @Override
    public void onBindViewHolder(final QuestionViewHolder holder, final int position) {
        Question question = questionList.get(position);
        holder.tvQuestion.setText(question.getText());

        if (question.getQuestionType().equalsIgnoreCase(QuestionType.CHECKBOX.toString())){
            if(holder.llRadioBox.getVisibility()!=View.GONE){
                holder.llRadioBox.setVisibility(View.GONE);
                holder.llDetailed.setVisibility(View.GONE);

                QuestionnaireAnswersActivity.answer.getResults().add(position, new Result());

                for (int x= 0; x < question.getVariants().size(); x++){
                    if (x == 0) {
                        QuestionnaireAnswersActivity.answer.getResults().add(x, new Result());
                        holder.checkBoxPlace.setText(question.getVariants().get(x).getText());
                        final int finalX = x;
                        holder.checkBoxPlace.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                QuestionnaireAnswersActivity.answer.getResults().get(finalX).setAnswerBool(!QuestionnaireAnswersActivity.answer.getResults().get(finalX).isAnswerBool());
                            }
                        });
                    } else {
                        QuestionnaireAnswersActivity.answer.getResults().add(x, new Result());
                        CheckBox checkBox = new CheckBox(holder.context);
                        checkBox.setText(question.getVariants().get(x).getText());
                        holder.llCheckBox.addView(checkBox);

                        final int finalX = x;
                        checkBox.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                QuestionnaireAnswersActivity.answer.getResults().get(finalX).setAnswerBool(!QuestionnaireAnswersActivity.answer.getResults().get(finalX).isAnswerBool());
                            }
                        });
                    }
                }
            }
        } else if (question.getQuestionType().equalsIgnoreCase(QuestionType.RADIO.toString())){
            if(holder.llCheckBox.getVisibility() != View.GONE) {
                holder.llCheckBox.setVisibility(View.GONE);
                holder.llDetailed.setVisibility(View.GONE);

                QuestionnaireAnswersActivity.answer.getResults().add(position, new Result());

                for (int x = 0; x < question.getVariants().size(); x++) {
                    if (x == 0) {
                        QuestionnaireAnswersActivity.answer.getResults().add(x, new Result());
                        holder.radioButton.setText(question.getVariants().get(x).getText());

                        final int finalX = x;
                        holder.radioButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                QuestionnaireAnswersActivity.answer.getResults().get(finalX).setAnswerBool(!QuestionnaireAnswersActivity.answer.getResults().get(finalX).isAnswerBool());
                            }
                        });
                    } else {
                        QuestionnaireAnswersActivity.answer.getResults().add(x, new Result());
                        RadioButton radioButton = new RadioButton(holder.context);
                        radioButton.setText(question.getVariants().get(x).getText());

                        holder.radioGroupPlace.addView(radioButton);

                        final int finalX = x;
                        radioButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                QuestionnaireAnswersActivity.answer.getResults().get(finalX).setAnswerBool(!QuestionnaireAnswersActivity.answer.getResults().get(finalX).isAnswerBool());
                            }
                        });
                    }
                }
            }
        } else if (question.getQuestionType().equalsIgnoreCase(QuestionType.DETAILED.toString())){
            holder.llRadioBox.setVisibility(View.GONE);
            holder.llCheckBox.setVisibility(View.GONE);

            QuestionnaireAnswersActivity.answer.getResults().add(position, new Result());

            QuestionnaireAnswersActivity.answer.getResults().add(0, new Result());
            holder.editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    QuestionnaireAnswersActivity.answer.getResults().get(0).setAnswerStr(holder.editText.getText().toString());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }
}
