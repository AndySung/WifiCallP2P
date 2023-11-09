package com.example.tablayout;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;

import com.example.tablayout.Fragments.DialerFragment;
import com.example.tablayout.Fragments.HomeFragment;
import com.example.tablayout.Fragments.SettingFragment;
import com.example.tablayout.tab.BarEntity;
import com.example.tablayout.tab.BottomTabBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements BottomTabBar.OnSelectListener{
    private BottomTabBar tb ;
    private List<BarEntity> bars ;
    private HomeFragment homeFragment ;
    private DialerFragment recentCallFragment ;
    private SettingFragment settingFragment ;
    private FragmentManager manager ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setStatusBarColor(getColor(R.color.bg_color));
        initView();
    }

    private void initView() {
        manager = getSupportFragmentManager();
        tb = findViewById(R.id.tb);
        bars = new ArrayList<>();
        bars.add(new BarEntity("最近联系",R.mipmap.home_icon_select,R.mipmap.home_icon_unselect));
        bars.add(new BarEntity("VIOP",R.mipmap.voip_call_icon_select,R.mipmap.voip_call_icon_unselect));
        bars.add(new BarEntity("配置",R.mipmap.setting_icon_select,R.mipmap.setting_icon_unselect));

        tb.setManager(manager).setOnSelectListener(this).setBars(bars);
    }

    @Override
    public void onSelect(int position) {
        switch (position){
            case 0:
                if (homeFragment==null){
                    homeFragment = new HomeFragment();
                }
                tb.switchContent(homeFragment);
                break;
            case 1:
                if (recentCallFragment==null){
                    recentCallFragment = new DialerFragment();
                }
                tb.switchContent(recentCallFragment);
                break;
            case 2:
                if (settingFragment==null){
                    settingFragment = new SettingFragment();
                }
                tb.switchContent(settingFragment);
                break;
            default:
                break;
        }
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_setting){
            //todo
            return false;
        }
        return super.onOptionsItemSelected(item);
    }
}