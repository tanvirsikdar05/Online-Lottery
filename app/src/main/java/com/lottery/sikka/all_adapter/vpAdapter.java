package com.lottery.sikka.all_adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.lottery.sikka.wallets.deposite;
import com.lottery.sikka.wallets.request;
import com.lottery.sikka.wallets.withdraw;

public class vpAdapter extends FragmentStateAdapter {

    public vpAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new withdraw();
            case 2:
               return new request();
        }
        return new deposite();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
