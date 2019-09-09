package com.mingyuechunqiu.agileproject.feature.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.mingyuechunqiu.agile.ui.fragment.BaseFragment;
import com.mingyuechunqiu.agile.util.FragmentUtils;
import com.mingyuechunqiu.agileproject.R;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/8/19
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class JumpActivity extends AppCompatActivity implements BaseFragment.Callback {

    private Fragment mSelectedFg;
    private Fragment mCurrentFg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agile_layout_frame);
        mSelectedFg = new MainFragment();
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.fl_agile_frame_container, mSelectedFg)
//                .commitAllowingStateLoss();
        FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.fl_agile_frame_container, mSelectedFg);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Fragment test = new JumpContainerFragment();
                FragmentUtils.showAndHideFragment(getSupportFragmentManager(), R.id.fl_agile_frame_container,
                        mSelectedFg, test);
                mSelectedFg = test;
            }
        }, 2000);
    }

    @Override
    public void onCall(Fragment fragment, @Nullable Bundle bundle) {
        if (fragment.getClass().getSimpleName().equals(JumpFragment2.class.getSimpleName())) {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
            }
//            getSupportFragmentManager().beginTransaction()
//                    .setCustomAnimations(R.anim.agile_slide_in_right, R.anim.agile_slide_out_left)
//                    .show(mCurrentFg)
//                    .commitAllowingStateLoss();
            return;
        }
//        getSupportFragmentManager().beginTransaction()
//                .setCustomAnimations(R.anim.agile_slide_in_right, R.anim.agile_slide_out_left)
//                .hide(mSelectedFg)
//                .commitAllowingStateLoss();

//        mCurrentFg = new JumpFragment2();
//        getSupportFragmentManager().beginTransaction()
//                .setCustomAnimations(R.anim.agile_slide_in_right, R.anim.agile_slide_out_left,
//                        R.anim.agile_slide_in_left, R.anim.agile_slide_out_right)
//                .hide(mSelectedFg)
//                .add(R.id.fl_agile_frame_container, mCurrentFg)
//                .addToBackStack(null)
//                .commitAllowingStateLoss();
//        FragmentUtils.showAndHideFragment(getSupportFragmentManager(), R.id.fl_agile_frame_container,
//                mSelectedFg, mCurrentFg, true, null, true,
//                R.anim.agile_slide_in_right, R.anim.agile_slide_out_left,
//                R.anim.agile_slide_in_left, R.anim.agile_slide_out_right);
//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.fl_agile_frame_container, new MainFragment())
//                .commitAllowingStateLoss();
        startActivity(new Intent(this, MainActivity.class));
    }
}
