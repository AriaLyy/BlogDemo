package com.example.yuyu.navigationbardemo;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    private String TAG = "MainActivity";
    private RadioGroup mNavigationBar;
    private FragmentManager mFm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mFm = getSupportFragmentManager();
        mNavigationBar = (RadioGroup) findViewById(R.id.navigation);

        int size = (int) getResources().getDimension(R.dimen.navigation_top_icon_size);
        for (int i = 0, count = mNavigationBar.getChildCount(); i < count; i++) {
            RadioButton rb = (RadioButton) mNavigationBar.getChildAt(i);
            Drawable topIcon = getResources().getDrawable(R.drawable.selector_navigation_bg);
            topIcon.setBounds(0, 0, size, size);
            rb.setCompoundDrawables(null, topIcon, null, null);
            rb.setId(i);
        }
        mNavigationBar.setOnCheckedChangeListener(this);
        ((RadioButton) mNavigationBar.getChildAt(0)).setChecked(true);
    }


    private void chooseFragment(String tag) {
        int color = Color.RED;
        switch (tag) {
            case "0":
                color = Color.RED;
                break;
            case "1":
                color = Color.BLUE;
                break;
            case "2":
                color = Color.GREEN;
                break;
            case "3":
                color = Color.YELLOW;
                break;
        }
        Fragment fragment = DummyFragment.newInstance(color);
        FragmentTransaction ft = mFm.beginTransaction();
        ft.replace(R.id.content, fragment);
        ft.commit();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        RadioButton rb = (RadioButton) group.getChildAt(checkedId);
        if (rb.isChecked()) {
            chooseFragment(String.valueOf(rb.getTag()));
        }
    }

    @SuppressLint("ValidFragment")
    public static class DummyFragment extends Fragment {
        int color;

        public static DummyFragment newInstance(@ColorInt int color) {
            return new DummyFragment(color);
        }

        private DummyFragment() {
        }


        private DummyFragment(int color) {
            this.color = color;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.test_game_detail_fragment, container, false);
            view.setBackgroundColor(color);
            return view;
        }
    }

}
