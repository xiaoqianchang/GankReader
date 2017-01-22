package com.xiaoqianchang.gankreader;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xiaoqianchang.gankreader.databinding.ActivityTransitionBinding;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 启动界面
 * 
 * Created by Chang.Xiao on 2017/1/22.
 * @version 1.0
 */
public class TransitionActivity extends AppCompatActivity {

    private ActivityTransitionBinding mBinding;
    private List<Integer> mDrawables = Arrays.asList(R.drawable.b_1, R.drawable.b_2,
            R.drawable.b_3, R.drawable.b_4, R.drawable.b_5, R.drawable.b_6);
    private boolean isIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_transition);

        int i = new Random().nextInt(mDrawables.size());
        mBinding.ivPic.setImageResource(mDrawables.get(i));

        new Handler().postDelayed(() -> {
            toMainActivity();
        }, 3000);

        mBinding.tvJump.setOnClickListener(v -> {
            toMainActivity();
        });
    }

    private void toMainActivity() {
        if (isIn)
            return;
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
        finish();
        isIn = true;
    }
}
