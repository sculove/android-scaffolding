package sculove.scaffolding;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by sculove on 14. 12. 24..
 * 뒤로가기(Back 버튼) 두번 눌러 앱 종료하기
 */
public class BackPressedHandler {

    private long backKeyPressedTime = 0;
    private Toast toast;

    private Activity activity;

    public BackPressedHandler(Activity context) {
        this.activity = context;
    }

    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            showGuide();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            activity.finish();
            toast.cancel();
        }
    }

    public void showGuide() {
        toast = Toast.makeText(activity, this.activity.getString(R.string.action_backpressed_msg), Toast.LENGTH_SHORT);
        toast.show();
    }
}
