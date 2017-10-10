package com.gothamite.remindme.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.gothamite.remindme.dto.RemindDTO;
import com.gothamite.remindme.fragment.AbstractTabFragment;
import com.gothamite.remindme.fragment.BirthdayFragment;
import com.gothamite.remindme.fragment.HistoryFragment;
import com.gothamite.remindme.fragment.IdeasFragment;
import com.gothamite.remindme.fragment.TodoFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TabsFragmentAdapter extends FragmentPagerAdapter {

    private Map<Integer, AbstractTabFragment> tabs;
    private Context context;
    private List<RemindDTO> data;
    private HistoryFragment historyFragment;

    public TabsFragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
        this.data = new ArrayList<>();
        initTabsMap(context);

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position).getTitle();
    }


    @Override
    public Fragment getItem(int position) {
        return tabs.get(position);
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    private void initTabsMap(Context context) {
        tabs = new HashMap<>();
        historyFragment = HistoryFragment.getInstance(context, data);
        tabs.put(0, historyFragment);
        tabs.put(1, IdeasFragment.getInstance(context));
        tabs.put(2, TodoFragment.getInstance(context));
        tabs.put(3, BirthdayFragment.getInstance(context));
    }


    public void setData(List<RemindDTO> data) {
        this.data = data;
        historyFragment.refreshData(data);
    }
}
