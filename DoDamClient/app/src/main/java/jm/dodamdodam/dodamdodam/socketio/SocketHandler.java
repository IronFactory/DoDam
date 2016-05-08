package jm.dodamdodam.dodamdodam.socketio;

/**
 * Created by IronFactory on 15. 11. 29..
 */
public class SocketHandler {

    public static OnInsertWord onInsertWord;
    public static OnGetWord onGetWord;


    // TODO: 15. 11. 29. 글귀 입력
    public interface OnInsertWord {
        void onSuccess();
        void onException();
    }


    // TODO: 15. 11. 29. 글귀 받아오기
    public interface OnGetWord {
        void onSuccess(String word);
        void onException();
    }
}
