package kr.ac.mjc.myapplicationproject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MainPagerAdapter extends FragmentStateAdapter {

    HomeFragment homeFragment = new HomeFragment();
    SmsFragment smsFragment = new SmsFragment();
    AddFragment addFragment = new AddFragment();
    NoticeFragment noticeFragment = new NoticeFragment();
    ProfileFragment profileFragment = new ProfileFragment();

    public MainPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return noticeFragment;
            case 1:
                return smsFragment;
            case 2:
                return addFragment;
            case 3:
                return homeFragment;
            default:
                return profileFragment;
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
