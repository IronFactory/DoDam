package jm.dodamdodam.dodamdodam.socketio;

import android.content.Context;
import android.content.Intent;

import jm.dodamdodam.dodamdodam.Global;
import jm.dodamdodam.dodamdodam.data.DiaryModel;

/**
 * Created by IronFactory on 15. 11. 24..
 */
public class RequestManager extends SocketIntent {

    private static final String TAG = "RequestManager";

    public static void insertWord(Context context, int feel, String word, SocketHandler.OnInsertWord onInsertWord) {
        Intent intent = getIntent(context, SocketService.class, Global.INSERT_WORD);
        intent.putExtra(Global.FEEL, feel);
        intent.putExtra(Global.WORD, word);
        context.startService(intent);

        SocketHandler.onInsertWord = onInsertWord;
    }


    public static void getWord(Context context, int feel, SocketHandler.OnGetWord onGetWord) {
        Intent intent = getIntent(context, SocketService.class, Global.GET_WORD);
        intent.putExtra(Global.FEEL, feel);
        context.startService(intent);

        SocketHandler.onGetWord = onGetWord;
    }


    public static void insertDiary(DiaryModel diary) {

    }
}
