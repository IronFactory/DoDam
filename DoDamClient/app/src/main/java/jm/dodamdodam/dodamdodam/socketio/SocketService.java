package jm.dodamdodam.dodamdodam.socketio;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import jm.dodamdodam.dodamdodam.Global;

public class SocketService extends Service {

    private static final String TAG = "socketService";
    private SocketIO socketIO;

    public SocketService() {
    }


    private void init() {
        if (socketIO == null) {
            socketIO = new SocketIO(getApplicationContext());
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        init();

        if (intent != null) {
            String command = intent.getStringExtra(Global.COMMAND);
            Log.d(TAG, "command = " + command);

            if (command != null) {
                if (command.equals(Global.INSERT_WORD)) {
                    // 글귀 추가
                    processInsertWord(intent);
                } else if (command.equals(Global.GET_WORD)) {
                    // 글귀 요청
                    processGetWord(intent);
                }
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }


    private void processGetWord(Intent intent) {
        int feel = intent.getIntExtra(Global.FEEL, -1);
        socketIO.getWord(feel);
    }


    // TODO: 15. 11. 24. 글귀 추가 명령
    private void processInsertWord(Intent intent) {
        int feel = intent.getIntExtra(Global.FEEL, -1);
        String word = intent.getStringExtra(Global.WORD);
        socketIO.insertWord(feel, word);
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
