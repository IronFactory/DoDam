package jm.dodamdodam.dodamdodam.socketio;

import android.os.Handler;
import android.os.Looper;

import org.json.JSONException;
import org.json.JSONObject;

import jm.dodamdodam.dodamdodam.Global;

/**
 * Created by IronFactory on 15. 11. 24..
 */
public class ResponseManager extends SocketIntent {

    private static final String TAG = "ResponseManager";

    public static final int SUCCESS = 200;
    private Handler handler = new Handler();

    // TODO: 15. 11. 24. 글귀 추가 응답 처리
    public void processInsertWord(JSONObject object) {
        Looper.prepare();
        try {
            final int code = getCode(object);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (code == SUCCESS) {
                        SocketHandler.onInsertWord.onSuccess();
                    } else {
                        SocketHandler.onInsertWord.onException();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    // TODO: 15. 11. 29. 글귀 요청 응답
    public void processGetWord(JSONObject object) {
        try {
            final int code = getCode(object);
            if (code == SUCCESS) {
                final String word = object.getString(Global.WORD);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        SocketHandler.onGetWord.onSuccess(word);
                    }
                });
            } else {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        SocketHandler.onGetWord.onException();
                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
