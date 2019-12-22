package code.blue.androiddeveloper101.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import code.blue.androiddeveloper101.R;

public class SplashFragment extends Fragment {
//    private ImageView ivSplashLogo;
//    private TextView tvSplashText;
    private static int time = 3000;

    public static SplashFragment newInstance() {
        return new SplashFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ((MainActivity) getActivity()).goToHomeScreen();
            }
        }, time);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        View v = inflater.inflate(R.layout.splash_fragment, container, false);
//        ivSplashLogo = v.findViewById(R.id.iv_splash_logo);
//        tvSplashText = v.findViewById(R.id.tv_splash_text);

        return v;
    }
}
