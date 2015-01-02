package sculove.scaffolding;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sculove.scaffolding.webview.CustomWebView;

/**
 * Created by sculove on 14. 12. 19..
 */
public class TodayFragment extends Fragment {
    protected View root = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 중복 생성하기 않기
        if(root == null ) {
            root = inflater.inflate(R.layout.fragment_today, container, false);
            CustomWebView webView = (CustomWebView)root.findViewById(R.id.todayWebview);
            webView.loadUrl("file:///android_asset/today.html");
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 중복 추가 하지 않기 위해
        if(root!=null){
            ViewGroup parent = (ViewGroup)root.getParent();
            if(parent!=null){
                parent.removeView(root);
            }
        }
    }
}


