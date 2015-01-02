package sculove.scaffolding.webview;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by sculove on 14. 12. 23..
 */
public class CustomWebView extends WebView {
//    protected ProgressBar mProgressbar;
    protected Context mContext;

    public CustomWebView(Context context) {
        super(context);
        mContext = context;
        initWebView(this);
    }

    public CustomWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initWebView(this);
    }

    private void initWebView(WebView webView) {
//        mProgressbar = new ProgressBar(mContext, null, android.R.attr.progressBarStyleSmall);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDefaultTextEncodingName("UTF-8");
//        webView.addJavascriptInterface(coreJs, "informer");
        webView.setHorizontalScrollbarOverlay(true);
        webView.setVerticalScrollbarOverlay(true);
        webView.setWebChromeClient(new CustomChromeClient());
        webView.setWebViewClient(new CustomWebViewClient(mContext));
    }
}
