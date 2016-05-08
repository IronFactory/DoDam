package jm.dodamdodam.dodamdodam.socketio;

import android.content.Context;
import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import jm.dodamdodam.dodamdodam.Global;

/**
 * Created by ironFactory on 2015-08-03.
 */
public class SocketIO {

    private static final String URL = "http://dodam-dodam-server.herokuapp.com";
    private static final String TAG = "SocketIO";

    public static Socket socket;
    private Context context;
    private ResponseManager responseManager;

    public SocketIO(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        try {
            socket = IO.socket(URL);
        } catch (Exception e) {
            Log.e(TAG, "init 에러 = " + e.getMessage());
        }

        socketConnect();

        setListener();
        responseManager = new ResponseManager();
    }


    private void setListener() {
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                // 연결
                Log.d(TAG, "소켓 연결");
            }
        }).on(Global.INSERT_WORD, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject object = (JSONObject) args[0];
                responseManager.processInsertWord(object);
            }
        }).on(Global.GET_WORD, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject object = (JSONObject) args[0];
                responseManager.processGetWord(object);
            }
        });
    }


    public static Socket getSocket() {
        return socket;
    }


    public void socketConnect() {
        if (socket != null) {
            socket.open();
            socket.connect();
        }
    }


    // TODO: 15. 11. 24. 글귀 추가 요청
    public void insertWord(int feel, String word) {
        Log.d(TAG, "글귀 추가 요청");
        Log.i(TAG, "feel = " + feel);
        Log.i(TAG, "word = " + word);

        try {
            JSONObject object = new JSONObject();
            object.put(Global.FEEL, feel);
            object.put(Global.WORD, word);
            socket.emit(Global.INSERT_WORD, object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    // TODO: 15. 11. 29. 글귀 요청
    public void getWord(int feel) {
        Log.d(TAG, "글귀 요청");
        Log.d(TAG, "feel = " + feel);

        try {
            JSONObject object = new JSONObject();
            object.put(Global.FEEL, feel);
            socket.emit(Global.GET_WORD, object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}