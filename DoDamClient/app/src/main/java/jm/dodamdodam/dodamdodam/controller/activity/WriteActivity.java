package jm.dodamdodam.dodamdodam.controller.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

import jm.dodamdodam.dodamdodam.Global;
import jm.dodamdodam.dodamdodam.R;
import jm.dodamdodam.dodamdodam.data.DiaryModel;
import jm.dodamdodam.dodamdodam.socketio.RequestManager;
import jm.dodamdodam.dodamdodam.socketio.SocketHandler;

public class WriteActivity extends AppCompatActivity {

    private static final String TAG = "WriteActivity";

    private Button back_btn;
    private ImageButton submit;
    private EditText diaryInput;
    private Toolbar toolbar;
    private Spinner spinner;
    private LinearLayout container;
    private DiaryModel diary;

    private int feel = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        init();
        setListener();
//        setDate();
    }


    private void init() {
        diaryInput = (EditText) findViewById(R.id.write_edit_text);
        back_btn = (Button) findViewById(R.id.activity_write_back_btn);
        submit = (ImageButton) findViewById(R.id.activity_write_submit);
        toolbar = (Toolbar) findViewById(R.id.activity_write_toolbar);
        spinner = (Spinner) findViewById(R.id.activity_write_spinner);
        container = (LinearLayout) findViewById(R.id.activity_write_container);

        back_btn.setText("< 일기 입력");
        setSupportActionBar(toolbar);
        setDiary();
    }


    private void setDiary() {
        if (getIntent().getSerializableExtra(Global.DIARY) != null) {
            diary = (DiaryModel) getIntent().getSerializableExtra(Global.DIARY);
            diaryInput.setText(diary.getContent());
            spinner.setSelection(diary.getFeel());
        }
    }


    private void setListener() {
        // 확인 버튼 클릭
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiaryModel curDiary = diary;
                String content = diaryInput.getText().toString();
                long date = System.currentTimeMillis();
                Log.d(TAG, "date = " + date);

                if (TextUtils.isEmpty(content)) {
                    diaryInput.setError("일기를 입력하세요");
                    return;
                }
                if (feel == 0) {
                    Snackbar.make(container, "느낌을 선택하세요", Snackbar.LENGTH_SHORT).show();
                    return;
                }


                // Progress Visible



                if (curDiary == null) {
                    final DiaryModel newDiary = new DiaryModel();
                    newDiary.setContent(content);
                    newDiary.setFeel(feel);
                    newDiary.setDate(date);

                    // 서버에서 글귀가 오면
                    RequestManager.getWord(getApplicationContext(), feel, new SocketHandler.OnGetWord() {
                        @Override
                        public void onSuccess(String word) {
                            Log.d(TAG, "word = " + word);
                            newDiary.setWord(word);
                            Global.dbManager.insertDiary(newDiary);
                            finish();
                        }

                        @Override
                        public void onException() {
                            Snackbar.make(container, "글귀 요청에 실패했습니다", Snackbar.LENGTH_SHORT).show();
                            submit.setClickable(true);
                        }
                    });



                } else {
                    curDiary.setContent(content);
                    curDiary.setFeel(feel);
                    curDiary.setDate(date);
                    Global.dbManager.updateDiary(diary, curDiary);
                    finish();
                }

                submit.setClickable(false);
            }
        });
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                feel = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


//    private void setDate() {
//        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
//        Date currentTime = new Date();
//        String mTime = mSimpleDateFormat.format(currentTime);
//        write_date_text.setText("날짜 : " + mTime);
//    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
