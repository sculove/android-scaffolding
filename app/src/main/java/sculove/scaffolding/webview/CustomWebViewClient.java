package sculove.scaffolding.webview;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import sculove.scaffolding.R;

/**
 * Created by sculove on 14. 12. 24..
 */
public class CustomWebViewClient extends WebViewClient {
    protected ProgressDialog mProgress;
    protected Context mContext;

    public CustomWebViewClient(Context context) {
        mContext = context;
    }

    // 페이지 시작
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        mProgress = new ProgressDialog(mContext);
        mProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgress.setTitle(mContext.getText(R.string.webview_loading));
        mProgress.setMessage(mContext.getText(R.string.webview_loading_msg));
        mProgress.setProgress(0);
        mProgress.setMax(100);
        mProgress.show();
        super.onPageStarted(view, url, favicon);
    }
    // 페이지 로딩시
    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        Toast.makeText(mContext, mContext.getText(R.string.webview_loading_error) + description, Toast.LENGTH_SHORT).show();
        if (mProgress.isShowing()) {
            mProgress.dismiss();
        }
    }
    // 페이지 종료
    @Override
    public void onPageFinished(WebView view, String url) {
        if (mProgress.isShowing()) {
            mProgress.dismiss();
        }
    }
}
