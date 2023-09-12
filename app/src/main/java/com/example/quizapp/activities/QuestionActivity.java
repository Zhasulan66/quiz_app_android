package com.example.quizapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.example.quizapp.R;
import com.example.quizapp.databinding.ActivityQuestionBinding;
import com.example.quizapp.models.QuestionModel;

import java.util.ArrayList;

public class QuestionActivity extends AppCompatActivity {

    ArrayList<QuestionModel> list = new ArrayList<>();
    private int count = 0;
    private int position = 0;
    private int score = 0;
    CountDownTimer timer;

    ActivityQuestionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        resetTimer();
        timer.start();

        String setName = getIntent().getStringExtra("set");

        if (setName.equals("SET-1")) {
            setOne();
        } else if (setName.equals("SET-2")) {
            setTwo();
        }


        for (int i = 0; i < 4; i++) {

            binding.optionContainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    checkAnswer((Button) view);
                }
            });

        }

        playAnimation(binding.question, 0, list.get(position).getQuestion());

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(timer != null){
                    timer.cancel();
                }
                timer.start();

                binding.btnNext.setEnabled(false);
                binding.btnNext.setAlpha((float) 0.3);

                enableOption(true);
                position++;

                if (position == list.size()) {
                    Intent intent = new Intent(QuestionActivity.this, ScoreActivity.class);
                    intent.putExtra("score", score);
                    intent.putExtra("total", list.size());
                    startActivity(intent);
                    finish();
                    return;
                }

                count = 0;

                playAnimation(binding.question, 0, list.get(position).getQuestion());
            }
        });

    }

    private void resetTimer() {
        timer = new CountDownTimer(30_000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                binding.timer.setText(String.valueOf( millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
                Dialog dialog = new Dialog(QuestionActivity.this);
                dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.timerout_dialog);
                dialog.findViewById(R.id.btn_try_again).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(QuestionActivity.this, SetsActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

                if(!((Activity) QuestionActivity.this).isFinishing())
                {
                    //show dialog
                    dialog.show();
                }

            }
        };
    }

    private void playAnimation(View view, int value, String data) {
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500)
                .setStartDelay(100).setInterpolator(new DecelerateInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(@NonNull Animator animation) {
                        if (value == 0 && count < 4) {

                            String option = "";

                            if (count == 0) {
                                option = list.get(position).getOptionA();
                            } else if (count == 1) {
                                option = list.get(position).getOptionB();
                            } else if (count == 2) {
                                option = list.get(position).getOptionC();
                            } else if (count == 3) {
                                option = list.get(position).getOptionD();
                            }

                            playAnimation(binding.optionContainer.getChildAt(count), 0, option);
                            count++;
                        }
                    }

                    @Override
                    public void onAnimationEnd(@NonNull Animator animation) {

                        if (value == 0) {

                            try {
                                ((TextView) view).setText(data);
                                binding.totalQuestion.setText(position + 1 + "/" + list.size());
                            } catch (Exception e) {
                                ((Button) view).setText(data);
                            }

                            view.setTag(data);
                            playAnimation(view, 1, data);

                        }
                    }

                    @Override
                    public void onAnimationCancel(@NonNull Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(@NonNull Animator animation) {

                    }
                });
    }

    private void enableOption(boolean enable) {

        for (int i = 0; i < 4; i++) {
            binding.optionContainer.getChildAt(i).setEnabled(enable);

            if (enable) {
                binding.optionContainer.getChildAt(i).setBackgroundResource(R.drawable.btn_opt);
            }
        }

    }

    private void checkAnswer(Button selectedOption) {

        if(timer != null){
            timer.cancel();
        }

        binding.btnNext.setEnabled(true);
        binding.btnNext.setAlpha(1);

        if (selectedOption.getText().toString().equals(list.get(position).getCorrectAnswer())) {

            score++;
            selectedOption.setBackgroundResource(R.drawable.btn_correct);
        } else {
            selectedOption.setBackgroundResource(R.drawable.btn_wrong);

            Button correctOption = (Button) binding.optionContainer.findViewWithTag(list.get(position).getCorrectAnswer());
            correctOption.setBackgroundResource(R.drawable.btn_correct);
        }
    }

    private void setTwo() {
        list.add(new QuestionModel("23 * 4 = ?",
                "86", "92", "96", "112", "92"));
        list.add(new QuestionModel("25 * 25 = ?",
                "125", "225", "625", "525", "625"));
        list.add(new QuestionModel(" 5! = ?",
                "90", "125", "5", "25", "125"));
        list.add(new QuestionModel("log(1000) = ?",
                "10", "3", "1000", "2", "3"));
    }

    private void setOne() {
        list.add(new QuestionModel("23 * 4 = ?",
                "86", "92", "96", "112", "92"));
        list.add(new QuestionModel("25 * 25 = ?",
                "125", "225", "625", "525", "625"));
        list.add(new QuestionModel(" 5! = ?",
                "90", "125", "5", "25", "125"));
        list.add(new QuestionModel("log(1000) = ?",
                "10", "3", "1000", "2", "3"));
    }
}