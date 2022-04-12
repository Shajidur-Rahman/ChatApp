package com.shajidurrahman.chatapp.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.shajidurrahman.chatapp.Fragments.CallsFragment;
import com.shajidurrahman.chatapp.Fragments.ChatsFragment;
import com.shajidurrahman.chatapp.Fragments.StatusFragment;

public class AdapterFragments extends FragmentPagerAdapter {
    public AdapterFragments(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0: return new ChatsFragment();
            case 1: return new StatusFragment();
            case 2: return new CallsFragment();
            default: return new ChatsFragment();
        }

    }

    @Override
    public int getCount() {
        return 3;
    }
}
