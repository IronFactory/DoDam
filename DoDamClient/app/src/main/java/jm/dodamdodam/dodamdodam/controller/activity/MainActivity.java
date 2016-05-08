package jm.dodamdodam.dodamdodam.controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.nispok.snackbar.Snackbar;

import java.util.ArrayList;

import jm.dodamdodam.dodamdodam.Global;
import jm.dodamdodam.dodamdodam.R;
import jm.dodamdodam.dodamdodam.controller.adapter.DiaryAdapter;
import jm.dodamdodam.dodamdodam.data.DBManager;
import jm.dodamdodam.dodamdodam.data.DiaryModel;

public class MainActivity extends Activity implements DiaryAdapter.OnDiaryClickListener {

    private static final String TAG = "MainActivity";

    private FloatingActionButton summaryView;
    private FloatingActionButton write;
    private FloatingActionButton add;

    private long backPressedTime = 0;
    private final long FINISH_INTERVAL_TIME = 2000;

    private RecyclerView recyclerView;
    private DiaryAdapter adapter;

    private ArrayList<DiaryModel> diarys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }


    @Override
    protected void onResume() {
        super.onResume();
        getDB();
        setAdapter();
    }

    private void init() {
        summaryView = (FloatingActionButton) findViewById(R.id.btn_summary);
        write = (FloatingActionButton) findViewById(R.id.btn_write);
        add = (FloatingActionButton) findViewById(R.id.btn_add);
        recyclerView = (RecyclerView) findViewById(R.id.activity_main_diary_list);

        setListener();
    }


    private void getDB() {
        Global.dbManager = new DBManager(getApplicationContext(), DBManager.TABLE_NAME, null, 1);
        diarys = Global.dbManager.getAllDiary();
    }


    private void setAdapter() {
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);

        adapter = new DiaryAdapter(this, diarys);
        recyclerView.setAdapter(adapter);
    }


    private void setListener() {
        summaryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "미리보기");
            }
        });

        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "일기 쓰기");
                Intent intent = new Intent(getApplicationContext(),WriteActivity.class);
                startActivity(intent);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "글귀 추가");
                Intent intent = new Intent(getApplicationContext(), WordActivity.class);
                startActivity(intent);
            }
        });

        summaryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "미리보기");
                Intent intent = new Intent(getApplicationContext(), SummaryActivity.class);
                intent.putExtra(Global.DIARY, diarys);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if(0 <= intervalTime && FINISH_INTERVAL_TIME >=intervalTime) {
            super.onBackPressed();
        } else {
            backPressedTime = tempTime;
            Snackbar.with(getApplicationContext())
                    .text("'뒤로' 버튼을 한번 더 누르세요.")
                    .show(this);
        }
    }


    @Override
    public void onClick(DiaryModel diary) {
        Intent intent = new Intent(getApplicationContext(), ViewActivity.class);
        intent.putExtra(Global.DIARY, diary);
        startActivity(intent);
    }
}
