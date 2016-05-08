package jm.dodamdodam.dodamdodam.controller.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import jm.dodamdodam.dodamdodam.Global;
import jm.dodamdodam.dodamdodam.R;
import jm.dodamdodam.dodamdodam.controller.ImageSizeController;
import jm.dodamdodam.dodamdodam.data.DiaryModel;

/**
 * Created by IronFactory on 15. 11. 29..
 */
public class ViewDiaryFragment extends Fragment {

    private static final String TAG = "ViewDiaryFragment";

    private DiaryModel diary;

    private TextView feelView;
    private TextView contentView;
    private TextView dateView;
    private ImageView feelImageView;
    private ImageView container;

    public static ViewDiaryFragment newInstance(DiaryModel diary) {
        ViewDiaryFragment fragment = new ViewDiaryFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Global.DIARY, diary);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        diary = (DiaryModel) getArguments().getSerializable(Global.DIARY);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view_diary, container, false);
        init(rootView);
        return rootView;
    }


    private void init(View rootView) {
        feelView = (TextView) rootView.findViewById(R.id.fragment_view_feel);
        contentView = (TextView) rootView.findViewById(R.id.fragment_view_content);
        dateView = (TextView) rootView.findViewById(R.id.fragment_view_date);
        feelImageView = (ImageView) rootView.findViewById(R.id.fragment_view_feel_image);
        container = (ImageView) rootView.findViewById(R.id.fragment_view_diary_background);

        setDiary();
        setBackground();
    }


    private void setBackground() {
        Random random = new Random();
        int num = random.nextInt(5);
        Bitmap bitmap = null;
        switch (num) {
            case 0:
                bitmap = ImageSizeController.getSmallImage(getActivity(), R.drawable.background1);
                break;

            case 1:
                bitmap = ImageSizeController.getSmallImage(getActivity(), R.drawable.background2);
                break;

            case 2:
                bitmap = ImageSizeController.getSmallImage(getActivity(), R.drawable.background3);
                break;

            case 3:
                bitmap = ImageSizeController.getSmallImage(getActivity(), R.drawable.background4);
                break;

            case 4:
                bitmap = ImageSizeController.getSmallImage(getActivity(), R.drawable.background5);
                break;
        }

        BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
        container.setBackground(bitmapDrawable);
    }


    private void setDiary() {
        setFeel(diary.getFeel());
        setFeelImage(diary.getFeel());
        setDate();
        contentView.setText(diary.getContent());
    }


    private void setDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        Date currentTime = new Date(diary.getDate());
        String time = simpleDateFormat.format(currentTime);
        dateView.setText(time);
    }


    // TODO: 15. 11. 29. 기분 이미지 설정
    private void setFeelImage(int feel) {
        switch (feel) {
            case Global.HAPPY:
                feelImageView.setImageResource(R.drawable.happy);
                break;

            case Global.ANGRY:
                feelImageView.setImageResource(R.drawable.angry);
                break;

            case Global.SAD:
                feelImageView.setImageResource(R.drawable.sad);
                break;

            case Global.SHY:
                feelImageView.setImageResource(R.drawable.shy);
                break;

            case Global.SCARY:
                feelImageView.setImageResource(R.drawable.scary);
                break;
        }
    }



    // TODO: 15. 11. 29. 기분
    private void setFeel(int feel) {
        switch (feel) {
            case Global.HAPPY:
                feelView.setText("행복");
                break;

            case Global.SCARY:
                feelView.setText("무서움");
                break;

            case Global.SHY:
                feelView.setText("부끄러움");
                break;

            case Global.ANGRY:
                feelView.setText("화남");
                break;

            case Global.SAD:
                feelView.setText("슬픔");
                break;
        }

    }
}
