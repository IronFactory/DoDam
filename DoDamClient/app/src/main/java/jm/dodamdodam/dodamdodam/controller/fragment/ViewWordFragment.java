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

import java.util.Random;

import jm.dodamdodam.dodamdodam.Global;
import jm.dodamdodam.dodamdodam.R;
import jm.dodamdodam.dodamdodam.controller.ImageSizeController;
import jm.dodamdodam.dodamdodam.data.DiaryModel;

/**
 * Created by IronFactory on 15. 11. 29..
 */
public class ViewWordFragment extends Fragment {

    private static final String TAG = "ViewWordFragment";

    private DiaryModel diary;

    private TextView wordView;
    private ImageView container;

    public static ViewWordFragment newInstance(DiaryModel diary) {
        ViewWordFragment fragment = new ViewWordFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_view_word, container, false);
        init(rootView);
        return rootView;
    }


    private void init(View rootView) {
        wordView = (TextView) rootView.findViewById(R.id.fragment_view_word_text);
        container = (ImageView) rootView.findViewById(R.id.fragment_view_background);
        wordView.setText(diary.getWord());

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
}
