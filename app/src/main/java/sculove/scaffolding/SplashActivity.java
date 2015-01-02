package sculove.scaffolding;

/**
 * Created by sculove on 14. 12. 24..
 */
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * Created by sculove on 2014. 5. 2..
 */
public class SplashActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                finish();    // 액티비티 종료
            }
        };
        handler.sendEmptyMessageDelayed(0, 3000);    // ms, 3초후 종료시킴
    }
}