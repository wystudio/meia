package com.meia.activity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.Window;

import com.meia.R;

@SuppressLint("MainActivity")
public class MainActivity extends FragmentActivity implements
		OnPageChangeListener, OnClickListener {
	private ViewPager mViewPager;
	private ActionBar mActionBar;
	private List<Fragment> mTabs = new ArrayList<Fragment>();
	private FragmentPagerAdapter mAdapter;

	private String[] mWebUrl = new String[] { "file:///android_asset/web/new.html", "file:///android_asset/web/store.html",
			"file:///android_asset/web/happy.html", "file:///android_asset/web/me.html" };

	private List<ChangeColorIconWithTextView> mTabIndicator = new ArrayList<ChangeColorIconWithTextView>();

	private ActionBar.LayoutParams lp = null;
	private View viewActionBarHome = null;
	private View viewActionBarDiscover = null;
	private View viewActionBarBuy = null;
	private View viewActionBarPersonal = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		setOverflowShowingAlways();
		mActionBar = getActionBar();
		mActionBar.setDisplayShowHomeEnabled(false);

		mViewPager = (ViewPager) findViewById(R.id.id_viewpager);

		initDatas();

		mViewPager.setAdapter(mAdapter);
		mViewPager.setOnPageChangeListener(this);
	}

	private void initDatas() {
		for (int i = 0; i < mWebUrl.length; i++) {
			TabFragment tabFragment = new TabFragment();
			Bundle args = new Bundle();
			args.putString("weburl", mWebUrl[i]);
			tabFragment.setArguments(args);
			mTabs.add(tabFragment);
		}

		mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

			@Override
			public int getCount() {
				return mTabs.size();
			}

			@Override
			public Fragment getItem(int arg0) {
				return mTabs.get(arg0);
			}
		};

		initTabIndicator();
		//

		if (lp == null) {
			lp = new ActionBar.LayoutParams(
					ActionBar.LayoutParams.MATCH_PARENT,
					ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
		}
		if (viewActionBarHome == null) {
			viewActionBarHome = getLayoutInflater().inflate(
					R.layout.action_bar_home, null);
		}
		if (viewActionBarDiscover == null) {
			viewActionBarDiscover = getLayoutInflater().inflate(
					R.layout.action_bar_discover, null);
		}
		if (viewActionBarBuy == null) {
			viewActionBarBuy = getLayoutInflater().inflate(
					R.layout.action_bar_buy, null);
		}
		if (viewActionBarPersonal == null) {
			viewActionBarPersonal = getLayoutInflater().inflate(
					R.layout.action_bar_personal, null);
		}
		
		mTabIndicator.get(0).setIconAlpha(1.0f);
		mViewPager.setCurrentItem(0, false);
		// set action bar customer view
		setActionBarCustomerView(viewActionBarHome, lp);

	}

	private void initTabIndicator() {
		ChangeColorIconWithTextView one = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_one);
		ChangeColorIconWithTextView two = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_two);
		ChangeColorIconWithTextView three = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_three);
		ChangeColorIconWithTextView four = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_four);

		mTabIndicator.add(one);
		mTabIndicator.add(two);
		mTabIndicator.add(three);
		mTabIndicator.add(four);

		one.setOnClickListener(this);
		two.setOnClickListener(this);
		three.setOnClickListener(this);
		four.setOnClickListener(this);

		one.setIconAlpha(1.0f);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onPageSelected(int arg0) {
	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {

		if (positionOffset > 0) {
			ChangeColorIconWithTextView left = mTabIndicator.get(position);
			ChangeColorIconWithTextView right = mTabIndicator.get(position + 1);

			left.setIconAlpha(1 - positionOffset);
			right.setIconAlpha(positionOffset);
		}

	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}

	@Override
	public void onClick(View v) {

		resetOtherTabs();

		switch (v.getId()) {
		case R.id.id_indicator_one:
			mTabIndicator.get(0).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(0, false);
			// set action bar customer view
			setActionBarCustomerView(viewActionBarHome, lp);
			//mActionBar.show();
			break;
		case R.id.id_indicator_two:
			mTabIndicator.get(1).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(1, false);
			// set action bar customer view
			setActionBarCustomerView(viewActionBarDiscover, lp);
		    //mActionBar.show();
			break;
		case R.id.id_indicator_three:
			mTabIndicator.get(2).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(2, false);
			setActionBarCustomerView(viewActionBarBuy, lp);
			//mActionBar.hide();
			break;
		case R.id.id_indicator_four:
			mTabIndicator.get(3).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(3, false);
			setActionBarCustomerView(viewActionBarPersonal, lp);
			//mActionBar.hide();
			break;

		}

	}

	private void setActionBarCustomerView(View customerView,
			ActionBar.LayoutParams lp) {
		mActionBar.setCustomView(customerView, lp);
		mActionBar.setDisplayShowHomeEnabled(false);
		mActionBar.setDisplayShowTitleEnabled(false);
		mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		mActionBar.setDisplayShowCustomEnabled(true);
	}

	/*
	 * private void setTabs(){
	 * mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS); // 去掉默认标题栏
	 * mActionBar.setDisplayShowHomeEnabled(false);
	 * mActionBar.setDisplayShowTitleEnabled(false); // Set up tabs
	 * addTab(TabState.GROUPS, R.drawable.ic_tab_groups); addTab(TabState.ALL,
	 * R.drawable.ic_tab_all); addTab(TabState.FAVORITES,
	 * R.drawable.ic_tab_starred); }
	 */

	/**
	 * 重置其他的Tab
	 */
	private void resetOtherTabs() {
		for (int i = 0; i < mTabIndicator.size(); i++) {
			mTabIndicator.get(i).setIconAlpha(0);
		}
	}

	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
			if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
				try {
					Method m = menu.getClass().getDeclaredMethod(
							"setOptionalIconsVisible", Boolean.TYPE);
					m.setAccessible(true);
					m.invoke(menu, true);
				} catch (Exception e) {
				}
			}
		}
		return super.onMenuOpened(featureId, menu);
	}

	private void setOverflowShowingAlways() {
		try {
			// true if a permanent menu key is present, false otherwise.
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			menuKeyField.setAccessible(true);
			menuKeyField.setBoolean(config, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
