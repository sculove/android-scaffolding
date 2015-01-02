package sculove.scaffolding;

import android.os.Bundle;
import android.support.v4.preference.PreferenceFragment;

/**
 * Created by naver on 14. 12. 22..
 */
public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
