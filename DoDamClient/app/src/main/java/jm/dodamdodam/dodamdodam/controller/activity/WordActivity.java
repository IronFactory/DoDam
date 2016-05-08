package jm.dodamdodam.dodamdodam.controller.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;

import jm.dodamdodam.dodamdodam.R;
import jm.dodamdodam.dodamdodam.socketio.RequestManager;
import jm.dodamdodam.dodamdodam.socketio.SocketHandler;

public class WordActivity extends AppCompatActivity {

    private static final String TAG = "WordActivity";

    private Button back_btn;
    private ImageButton submit;
    private EditText word_edit_text;
    private Toolbar toolbar;
    private Spinner spinner;
    private LinearLayout container;
    private ProgressBar progressBar;

    private String word;
    private int feel = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        init();
        setListener();
    }


    private void init() {
        word_edit_text = (EditText) findViewById(R.id.word_edit_text);
        back_btn = (Button) findViewById(R.id.activity_word_back_btn);
        submit = (ImageButton) findViewById(R.id.activity_word_submit);
        toolbar = (Toolbar) findViewById(R.id.activity_word_toolbar);
        spinner = (Spinner) findViewById(R.id.activity_word_spinner);
        container = (LinearLayout) findViewById(R.id.activity_word_container);
        progressBar = (ProgressBar) findViewById(R.id.activity_word_progress);

        back_btn.setText("< 글귀 입력");
        setSupportActionBar(toolbar);
    }


    private void setListener() {
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 확인
                word =  word_edit_text.getText().toString();
                if (TextUtils.isEmpty(word)) {
                    word_edit_text.setError("글귀를 입력하세요");
                    return;
                }
                if (feel == 0) {
                    Snackbar.make(container, "느낌을 선택하세요", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                // 서버에 글귀 추가 요청
                RequestManager.insertWord(getApplicationContext(), feel, word, new SocketHandler.OnInsertWord() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.INVISIBLE);
                        finish();
                    }

                    @Override
                    public void onException() {
                        Snackbar.make(container, "글귀 저장 중 문제가 발생했습니다.", Snackbar.LENGTH_SHORT).show();
                        submit.setClickable(true);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });

                submit.setClickable(false);
                progressBar.setVisibility(View.VISIBLE);
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


//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//
//        if (intent != null) {
//            String command = intent.getStringExtra(Global.COMMAND);
//            if (command != null) {
//                int code = intent.getIntExtra(Global.CODE, -1);
//                if (code != -1) {
//                    if(code == 200) {
//                        Toast.makeText(getApplicationContext(),"성공~!", Toast.LENGTH_SHORT).show();
//                        finish();
//                    } else if (code == 400 || code == 401) {
//                        Toast.makeText(getApplicationContext(),"네트워크를 확인하세요.",Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        }
//    }
}
