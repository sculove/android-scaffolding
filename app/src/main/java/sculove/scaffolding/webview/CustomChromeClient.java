package sculove.scaffolding.webview;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * Created by sculove on 14. 12. 23..
 */
public class CustomChromeClient extends WebChromeClient {
//    protected ProgressBar mProgress;
//
//    public CustomChromeClient(ProgressBar mProgress) {
//        this.mProgress = mProgress;
//    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, final android.webkit.JsResult result) {
        new AlertDialog.Builder(view.getContext())
            .setTitle("알림")
            .setMessage(message)
            .setPositiveButton(android.R.string.ok, new AlertDialog.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            }).setCancelable(false).create().show();
        result.confirm();
        return true;
    }

    @Override
    public boolean onJsConfirm(WebView view, String url, String message, final android.webkit.JsResult result) {
        new AlertDialog.Builder(view.getContext())
            .setTitle("확인")
            .setMessage(message)
            .setPositiveButton(android.R.string.ok,
                    new AlertDialog.OnClickListener() {
                        public void onClick(
                                DialogInterface dialog,
                                int which) {
                            result.confirm();
                        }
                    })
            .setNegativeButton(android.R.string.cancel,
                    new AlertDialog.OnClickListener() {
                        public void onClick(
                                DialogInterface dialog,
                                int which) {
                            result.cancel();
                        }
                    }).setCancelable(false).create().show();
        return true;
    }

//    @Override
//    public void onProgressChanged(WebView view, int newProgress) {
//        super.onProgressChanged(view, newProgress);
//        mProgress.setProgress(newProgress*100);
//    }
}
