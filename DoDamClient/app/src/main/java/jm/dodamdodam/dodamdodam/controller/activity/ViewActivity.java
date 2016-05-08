package jm.dodamdodam.dodamdodam.controller.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import jm.dodamdodam.dodamdodam.Global;
import jm.dodamdodam.dodamdodam.R;
import jm.dodamdodam.dodamdodam.controller.fragment.ViewDiaryFragment;
import jm.dodamdodam.dodamdodam.controller.fragment.ViewWordFragment;
import jm.dodamdodam.dodamdodam.data.DiaryModel;

public class ViewActivity extends AppCompatActivity {

    private static final String TAG = "ViewActivity";

    private ViewPager viewPager;

    private long backPressedTime = 0;
    private final long FINISH_INTERVAL_TIME = 2000;

    private DiaryModel diary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        init();
    }


    private void init() {
        viewPager = (ViewPager) findViewById(R.id.activity_view_pager);
        viewPager.setAdapter(new ViewAdapter(getSupportFragmentManager()));

        diary = (DiaryModel) getIntent().getSerializableExtra(Global.DIARY);
    }


    private class ViewAdapter extends FragmentPagerAdapter {

        public ViewAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return ViewDiaryFragment.newInstance(diary);

                default:
                    return ViewWordFragment.newInstance(diary);
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

//    @Override
//    public void onBackPressed() {
//        long tempTime = System.currentTimeMillis();
//        long intervalTime = tempTime - backPressedTime;
//
//        if(0 <= intervalTime && FINISH_INTERVAL_TIME >=intervalTime) {
//            super.onBackPressed();
//        } else {
//            backPressedTime = tempTime;
//        }
//    }
}
