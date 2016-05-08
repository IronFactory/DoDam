package jm.dodamdodam.dodamdodam.socketio;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import jm.dodamdodam.dodamdodam.Global;

/**
 * Created by IronFactory on 15. 11. 24..
 */
public class SocketIntent {

    private static final String TAG = "SocketIntent";


    public static Intent getIntent(Context context, Class activity, int code, String command) {
        Intent intent = new Intent(context, activity);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Global.COMMAND, command);
        intent.putExtra(Global.CODE, code);
        return intent;
    }


    public static Intent getIntent(Context context, Class intentClass, String command) {
        Intent intent = new Intent(context, intentClass);
        intent.putExtra(Global.COMMAND, command);
        return intent;
    }


    public static int getCode(JSONObject object) throws JSONException {
        int code = object.getInt(Global.CODE);
        Log.d(TAG, "code = " + code);
        return code;
    }
}
