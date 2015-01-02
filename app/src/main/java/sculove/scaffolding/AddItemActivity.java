package sculove.scaffolding;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;

import sculove.scaffolding.adapter.ExpandableListAdapter;

/**
 * Created by sculove on 14. 12. 23..
 */
public class AddItemActivity extends ActionBarActivity {
    protected ExpandableListAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#795548")));

        // Retrive the ExpandableListView from the layout
        ExpandableListView listView = (ExpandableListView) findViewById(R.id.item_listView);

        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener()
        {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id)
            {
                Intent intent = getIntent();
                intent.putExtra("item", mAdapter.childValue(groupPosition, childPosition));
                setResult(RESULT_OK,intent);
                finish();
                return false;
            }
        });

//        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener()
//        {
//
//            @Override
//            public boolean onGroupClick(ExpandableListView arg0, View arg1, int arg2, long arg3)
//            {
//                Toast.makeText(getBaseContext(), "Group clicked", Toast.LENGTH_LONG).show();
//                return false;
//            }
//        });

        ArrayList<String> group = new ArrayList<String>();
        ArrayList<ArrayList<String>> children = new ArrayList<ArrayList<String>>();
        ArrayList<String> child = new ArrayList<String>();

        group.add("커피");
        child = new ArrayList<String>();
        child.add("아메리카노");
        child.add("믹스커피");
        child.add("프렌치까페");
        children.add(child);
        group.add("차");
        child = new ArrayList<String>();
        child.add("녹차");
        child.add("홍차");
        children.add(child);
        group.add("에너지음료");
        child = new ArrayList<String>();
        child.add("에너지1");
        child.add("에너지2");
        children.add(child);

        // Initialize the adapter with blank groups and children
        // We will be adding children on a thread, and then update the ListView
        mAdapter = new ExpandableListAdapter(this, group, children);

        // Set this blank adapter to the list view
        listView.setAdapter(mAdapter);

        int count = mAdapter.getGroupCount();
        for (int position = 1; position <= count; position++)
            listView.expandGroup(position - 1);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = getIntent();
                setResult(RESULT_CANCELED,intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
