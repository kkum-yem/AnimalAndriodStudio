package kr.ac.mjc.myapplicationproject;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewpager);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Adapter 에서 Fragment 객체를 계속 유지하려면 off screen page limit 도 조절해야 함.
        viewPager.setOffscreenPageLimit(4);
        viewPager.setUserInputEnabled(false);

        MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(this); //()안에는 생성자, 프래그먼트를 관리할 수 있게 manager을 넣어줌
        viewPager.setAdapter(mainPagerAdapter);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_notice:
                    bottomNavigationView.setItemIconTintList(new ColorStateList(
                            new int[][]{
                                    new int[]{android.R.attr.state_checked},
                                    new int[]{}
                            },
                            new int[]{
                                    ContextCompat.getColor(this, R.color.colorBr),
                                    ContextCompat.getColor(this, R.color.black)
                            }
                    ));

                    viewPager.setCurrentItem(0, false);
                    break;

                case R.id.action_chat:
                    bottomNavigationView.setItemIconTintList(new ColorStateList(
                            new int[][]{
                                    new int[]{android.R.attr.state_checked},
                                    new int[]{}
                            },
                            new int[]{
                                    ContextCompat.getColor(this, R.color.colorYellow),
                                    ContextCompat.getColor(this, R.color.black)
                            }
                    ));

                    viewPager.setCurrentItem(1, false);
                    break;

                case R.id.action_add:
                    bottomNavigationView.setItemIconTintList(new ColorStateList(
                            new int[][]{
                                    new int[]{android.R.attr.state_checked},
                                    new int[]{}
                            },
                            new int[]{
                                    ContextCompat.getColor(this, R.color.colorBlue),
                                    ContextCompat.getColor(this, R.color.black)
                            }
                    ));

                    viewPager.setCurrentItem(2, false);
                    break;

                case R.id.action_home:
                    bottomNavigationView.setItemIconTintList(new ColorStateList(
                            new int[][]{
                                    new int[]{android.R.attr.state_checked},
                                    new int[]{}
                            },
                            new int[]{
                                    ContextCompat.getColor(this, R.color.colorGreen),
                                    ContextCompat.getColor(this, R.color.black)
                            }
                    ));

                    viewPager.setCurrentItem(3, false);
                    break;

                case R.id.action_profile:
                    bottomNavigationView.setItemIconTintList(new ColorStateList(
                            new int[][]{
                                    new int[]{android.R.attr.state_checked},
                                    new int[]{}
                            },
                            new int[]{
                                    ContextCompat.getColor(this, R.color.colorPink),
                                    ContextCompat.getColor(this, R.color.black)
                            }
                    ));

                    viewPager.setCurrentItem(4, false);
                    break;
            }

            return true;
        });

        bottomNavigationView.setSelectedItemId(R.id.action_notice);
    }

    public void moveTab(@IdRes int id) {
        if (id != R.id.action_notice &&
                id != R.id.action_chat &&
                id != R.id.action_add &&
                id != R.id.action_home &&
                id != R.id.action_profile) {
            return;
        }

        bottomNavigationView.setSelectedItemId(id);
    }
}