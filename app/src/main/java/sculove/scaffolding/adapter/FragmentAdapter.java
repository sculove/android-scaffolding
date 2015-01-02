package sculove.scaffolding.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.viewpagerindicator.IconPagerAdapter;

import java.util.ArrayList;

import sculove.scaffolding.HistoryFragment;
import sculove.scaffolding.R;
import sculove.scaffolding.SettingsFragment;
import sculove.scaffolding.TodayFragment;

/**
 * Created by sculove on 14. 12. 19..
 */
public class FragmentAdapter extends FragmentPagerAdapter implements IconPagerAdapter {
    protected ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    protected ArrayList<Integer> icons = new ArrayList<Integer>();
    protected ArrayList<String> titles = new ArrayList<String>();

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public void add(int layout, String title) {
        Fragment fragment = null;
        int icon = -1;
        switch(layout) {
            case R.layout.fragment_today :
                fragment = new TodayFragment();
                icon = R.drawable.ic_action_go_to_today;
                break;
            case R.layout.fragment_history :
                fragment = new HistoryFragment();
                icon = R.drawable.ic_action_go_to_today;
                break;
            case R.xml.preferences :
                fragment = new SettingsFragment();
                icon = R.drawable.ic_action_settings;
                break;
        }
        this.fragments.add(fragment);
        this.titles.add(title);
        this.icons.add(icon);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int index) {
        return fragments.get(index % fragments.size());
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int index) {
        return titles.get(index % titles.size());
    }

    @Override
    public int getIconResId(int index) {
        return icons.get(index % icons.size());
    }
}