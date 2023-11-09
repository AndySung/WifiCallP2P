package com.example.andrd_ado_vdo_tkbk_demo.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.andrd_ado_vdo_tkbk_demo.R;
import com.example.andrd_ado_vdo_tkbk_demo.activity.ConfigVoIPActivity;

public class SettingFragment extends Fragment {
    private RelativeLayout config_voip_layout;
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        view = inflater.inflate(R.layout.fragment_setting,container,false);
        initView();
        return view;
    }

    private void initView() {
        config_voip_layout = view.findViewById(R.id.config_voip_layout);
        config_voip_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ConfigVoIPActivity.class);
                startActivity(intent);
            }
        });
    }
}
