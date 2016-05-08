package jm.dodamdodam.dodamdodam.controller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import jm.dodamdodam.dodamdodam.Global;
import jm.dodamdodam.dodamdodam.R;
import jm.dodamdodam.dodamdodam.data.DiaryModel;

/**
 * Created by kim on 2015-11-14.
 */
public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder> {

    private ArrayList<DiaryModel> diarys;
    private Context context;
    private OnDiaryClickListener sender;

    public DiaryAdapter(Context context, ArrayList<DiaryModel> diarys) {
        this.context = context;
        this.diarys = diarys;

        sender = (OnDiaryClickListener) context;
    }


    @Override
    public DiaryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.diary_item, parent, false);
        return new DiaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DiaryViewHolder holder, final int position) {
        setFeelImage(holder, diarys.get(position).getFeel());
        holder.contentView.setText(diarys.get(position).getContent());
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sender.onClick(diarys.get(position));
            }
        });
    }


    // TODO: 15. 11. 29. 기분 이미지 설정
    private void setFeelImage(DiaryViewHolder holder, int feel) {
        switch (feel) {
            case Global.HAPPY:
                holder.feelView.setImageResource(R.drawable.happy);
                break;

            case Global.ANGRY:
                holder.feelView.setImageResource(R.drawable.angry);
                break;

            case Global.SAD:
                holder.feelView.setImageResource(R.drawable.sad);
                break;

            case Global.SHY:
                holder.feelView.setImageResource(R.drawable.shy);
                break;

            case Global.SCARY:
                holder.feelView.setImageResource(R.drawable.scary);
                break;
        }
    }


    @Override
    public int getItemCount() {
        return diarys.size();
    }

    class DiaryViewHolder extends RecyclerView.ViewHolder {
        ImageView feelView;
        TextView contentView;
        LinearLayout container;

        public DiaryViewHolder(View itemView) {
            super(itemView);

            feelView = (ImageView) itemView.findViewById(R.id.diary_item_feel_image);
            contentView = (TextView) itemView.findViewById(R.id.diary_item_content);
            container = (LinearLayout) itemView.findViewById(R.id.diary_item_container);
        }
    }


    public interface OnDiaryClickListener {
        void onClick(DiaryModel diary);
    }
}
