package jm.dodamdodam.dodamdodam.controller.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import jm.dodamdodam.dodamdodam.Global;
import jm.dodamdodam.dodamdodam.R;
import jm.dodamdodam.dodamdodam.controller.ImageSizeController;
import jm.dodamdodam.dodamdodam.data.DiaryModel;

public class SummaryActivity extends AppCompatActivity {

    private static final String TAG = "SummaryActivity";
    private ArrayList<DiaryModel> diarys;
    private FrameLayout container;
    private TextView contentView;
    private TextView wordView;
    private ImageView background;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        init();
    }


    private void init() {
        diarys = (ArrayList) getIntent().getSerializableExtra(Global.DIARY);
        container = (FrameLayout) findViewById(R.id.activity_summary_container);
        contentView = (TextView) findViewById(R.id.activity_summary_content);
        wordView = (TextView) findViewById(R.id.activity_summary_word);
        background = (ImageView) findViewById(R.id.activity_summary_background);

        contentView.setVisibility(View.INVISIBLE);
        wordView.setVisibility(View.INVISIBLE);

        setBackground();
        randomDiary();
        showView();
    }


    private void setBackground() {
        Random random = new Random();
        int num = random.nextInt(5);
        Bitmap bitmap = null;
        switch (num) {
            case 0:
                bitmap = ImageSizeController.getSmallImage(getApplicationContext(), R.drawable.background1);
                break;

            case 1:
                bitmap = ImageSizeController.getSmallImage(getApplicationContext(), R.drawable.background2);
                break;

            case 2:
                bitmap = ImageSizeController.getSmallImage(getApplicationContext(), R.drawable.background3);
                break;

            case 3:
                bitmap = ImageSizeController.getSmallImage(getApplicationContext(), R.drawable.background4);
                break;

            case 4:
                bitmap = ImageSizeController.getSmallImage(getApplicationContext(), R.drawable.background5);
                break;
        }
        background.setImageBitmap(bitmap);
    }


    // TODO: 15. 12. 5. 랜덤으로 섞기
    private void randomDiary() {
        Random random = new Random();
        for (int i = 0; i < diarys.size(); i++) {
            int num = random.nextInt(diarys.size());
            DiaryModel diary = diarys.get(i);
            DiaryModel curDiary = diarys.get(num);
            diarys.set(num, diary);
            diarys.set(i, curDiary);
        }
    }


    private void showView() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < diarys.size() * 2; i++) {
                    final int I = i;
                    if (i % 2 == 0) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                setText(I / 2);
                                setAnimation(contentView);
                            }
                        });
                    } else {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                setAnimation(wordView);
                            }
                        });
                    }

                    try {
                        Thread.sleep(6000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                });
            }
        }).start();
    }


    private void setText(int position) {
        contentView.setText(diarys.get(position).getContent());
        wordView.setText(diarys.get(position).getWord());
    }


    private void setText(DiaryModel diary) {
        contentView.setText(diary.getContent());
        wordView.setText(diary.getWord());
    }


    private void setAnimation(final TextView contentView) {
        contentView.setVisibility(View.VISIBLE);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        Random random = new Random();
        boolean xSign = random.nextBoolean();
        boolean ySign = random.nextBoolean();
        int x = random.nextInt(width / 2);
        int y = random.nextInt(height / 2);
        if (!xSign)
            x *= -1;
        if (!ySign)
            y *= -1;
        final AnimationSet animationSet = new AnimationSet(false);
        TranslateAnimation translateAnimation = new TranslateAnimation(0,x,0,y);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1f);
        translateAnimation.setDuration(6000);
        alphaAnimation.setDuration(5000);
        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(alphaAnimation);

        AlphaAnimation alphaAnimation1 = new AlphaAnimation(1,0);
        alphaAnimation1.setDuration(1000);
        alphaAnimation1.setStartOffset(5000);
        animationSet.addAnimation(alphaAnimation1);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                contentView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        contentView.startAnimation(animationSet);
    }
}
