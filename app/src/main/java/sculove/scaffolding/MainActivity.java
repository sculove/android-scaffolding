package sculove.scaffolding;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.viewpagerindicator.PageIndicator;

import net.simonvt.menudrawer.MenuDrawer;

import sculove.scaffolding.adapter.FragmentAdapter;
import sculove.scaffolding.adapter.SeparatedListAdapter;

public class MainActivity extends ActionBarActivity {
    private Menu mMenu;
    private BackPressedHandler mBackPressedHandler;

    private MenuDrawer mDrawer;

    // view pager indicator in drawer
    protected ViewPager mPager;
    protected PageIndicator mIndicator;
    private FragmentAdapter mFragmentAdapter;

    // menu view in drawer
    private ListView lvNavList;
    private SeparatedListAdapter mSeparatedListAdapter;

    void initDrawer() {
        mDrawer = MenuDrawer.attach(this, MenuDrawer.Type.OVERLAY);
        mDrawer.setMenuView(R.layout.activity_slidemenu);
        mDrawer.setContentView(R.layout.activity_container);
        mDrawer.setSlideDrawable(R.drawable.ic_drawer);
        mDrawer.setDrawerIndicatorEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#795548")));

        initMenuView(mDrawer.getMenuView());
        initContainerView(mDrawer.getRootView());
    }

    void initMenuView(View menuView) {
        lvNavList = (ListView)menuView.findViewById(R.id.slidemenu_list);
        mSeparatedListAdapter = new SeparatedListAdapter(this);
        for (int i = 1; i < 30; i++) {
            mSeparatedListAdapter.addItem("Row Item #" + i);
            if (i % 4 == 0) {
                mSeparatedListAdapter.addHeaderItem("Section #" + i);
            }
        }
        lvNavList.setAdapter(mSeparatedListAdapter);
        lvNavList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                if (mSeparatedListAdapter.getItemViewType(position) == SeparatedListAdapter.TYPE_ITEM) {
                    Toast.makeText(getBaseContext(), "아이템 : " + id, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), "헤더 : " + id, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void initContainerView(View rootView) {
        // create Adapter
        mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        mFragmentAdapter.add(R.layout.fragment_today, getResources().getString(R.string.today));
        mFragmentAdapter.add(R.layout.fragment_history, getResources().getString(R.string.history));

        // connect adapter -> viewPager -> indicator
        mPager = (ViewPager)rootView.findViewById(R.id.main_pager);
        mPager.setAdapter(mFragmentAdapter);
        mIndicator = (PageIndicator)rootView.findViewById(R.id.main_indicator);
        mIndicator.setViewPager(mPager);
        mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if(position == 0) {
                    mMenu.findItem(R.id.menu_additem).setVisible(true);
                } else {
                    mMenu.findItem(R.id.menu_additem).setVisible(false);
                }
            }
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // splash show
        startActivity(new Intent(this, SplashActivity.class));

        // start main
        super.onCreate(savedInstanceState);
        setContentView(new LinearLayout(this)); // 꼭 기본값을 지정해야함.
        initDrawer();

        mBackPressedHandler = new BackPressedHandler(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home :
                mDrawer.toggleMenu();
                return true;
            case R.id.menu_additem :
                Intent intent = new Intent(this, AddItemActivity.class);
                startActivityForResult(intent, 1);
                overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_fade_out);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            if(requestCode == 1) {
                Toast.makeText(getBaseContext(), data.getStringExtra("item"), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        final int drawerState = mDrawer.getDrawerState();
        if (drawerState == MenuDrawer.STATE_OPEN || drawerState == MenuDrawer.STATE_OPENING) {
            mDrawer.closeMenu();
            return;
        }
        mBackPressedHandler.onBackPressed();
    }
}