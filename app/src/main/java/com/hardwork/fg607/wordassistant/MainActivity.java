package com.hardwork.fg607.wordassistant;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.hardwork.fg607.wordassistant.adapter.MyPagerAdapter;
import com.hardwork.fg607.wordassistant.model.Config;
import com.hardwork.fg607.wordassistant.model.WordInfoSugar;
import com.hardwork.fg607.wordassistant.presenter.TranslatePresenter;
import com.hardwork.fg607.wordassistant.view.HistoryFragment;
import com.hardwork.fg607.wordassistant.view.ReciteActivity;
import com.hardwork.fg607.wordassistant.view.RecitePlanFragment;
import com.hardwork.fg607.wordassistant.view.TranslateFragment;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

@EActivity
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Bind(R.id.viewPager) ViewPager mViewPager;
    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.search_view) MaterialSearchView mSearchView;
    @Bind(R.id.tabs) TabLayout mTabs;
    @Bind(R.id.fab) FloatingActionButton mFloatingActionButton;

    private TranslateFragment mTranslateFragment = new TranslateFragment();
    private HistoryFragment mHistoryFragment = new HistoryFragment();
    private RecitePlanFragment mReciteFragment = new RecitePlanFragment();

    private String mOldText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }


    public void init() {

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        setupSearchView();

        setupViewPager();

        mHistoryFragment.setOnQueryHistoryListener(new HistoryFragment.OnQueryHistoryListener() {
            @Override
            public void OnQueryHistory(String word) {

                mViewPager.setCurrentItem(0);

                mTranslateFragment.query(word);
            }
        });

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, ReciteActivity.class);

                intent.putExtra("whichFragment", Config.NEWPLAN_FRAGMENT);

                MainActivity.this.startActivityForResult(intent, 0);
            }
        });

        mFloatingActionButton.hide();

        mReciteFragment.setFab(mFloatingActionButton);

    }

    private void setupSearchView() {

        mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                mViewPager.setCurrentItem(0);

                mTranslateFragment.query(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                //查询数据库内容

                if (!TextUtils.isEmpty(newText)) {

                    if (!"".equals(mOldText) && newText.indexOf(mOldText) == 0){

                        return false;
                    }

                    getSuggest(newText.substring(0, 1));

                    mOldText = newText.substring(0, 1);

                }

                return false;
            }
        });
        mSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

                mTabs.setVisibility(View.GONE);
                mTranslateFragment.hideDaywordsPic();
            }

            @Override
            public void onSearchViewClosed() {

                mTabs.setVisibility(View.VISIBLE);
                mTranslateFragment.showDaywordsPic();
            }
        });

        mSearchView.setCursorDrawable(R.drawable.search_cursor);
    }

    @Background
    public void getSuggest(String firstChar) {

        List<WordInfoSugar> suggestList = TranslatePresenter.querySuggestDB(firstChar);

        final ArrayList<String> list = new ArrayList<String>();

        for (WordInfoSugar sugar : suggestList) {

            list.add(sugar.getName());


            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    mSearchView.setSuggestions((String[])list.toArray(new String[0]));
                }
            });

            }
    }


    public void setupViewPager(){

        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(mTranslateFragment, getString(R.string.translate_fragment));
        adapter.addFragment(mHistoryFragment, getString(R.string.history_fragment));
        adapter.addFragment(mReciteFragment, getString(R.string.recite_fragment));

        mViewPager.setAdapter(adapter);

        mTabs.setupWithViewPager(mViewPager);

        mViewPager.setOffscreenPageLimit(3);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                switch (position) {
                    case 0:
                        mFloatingActionButton.hide();
                        break;
                    case 1:
                        mFloatingActionButton.hide();
                        mHistoryFragment.refreshHistory();
                        break;
                    case 2:
                        mFloatingActionButton.show();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        mSearchView.setMenuItem(item);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_exit) {

            finish();
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onBackPressed() {
        if (mSearchView.isSearchOpen()) {
            mSearchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Config.NEW_PLAN){

            mReciteFragment.refreshPlan();

        }
    }
}
