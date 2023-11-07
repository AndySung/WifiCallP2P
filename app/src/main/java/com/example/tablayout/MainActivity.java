package com.example.tablayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.Toolbar;

import com.example.tablayout.Fragments.ContactFragment;
import com.example.tablayout.Fragments.DialerFragment;
import com.example.tablayout.Fragments.HomeFragment;
import com.example.tablayout.Fragments.RecentCallFragment;
import com.example.tablayout.tab.BarEntity;
import com.example.tablayout.tab.BottomTabBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements BottomTabBar.OnSelectListener{
    private BottomTabBar tb ;
    private List<BarEntity> bars ;
    private HomeFragment homeFragment ;
    private DialerFragment recentCallFragment ;
    private ContactFragment contactFragment ;
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
        bars.add(new BarEntity("首页",R.mipmap.home_icon_select,R.mipmap.home_icon_unselect));
        bars.add(new BarEntity("最近联系",R.mipmap.recent_icon_select,R.mipmap.recent_icon_unselect));
        bars.add(new BarEntity("联系人",R.mipmap.contact_icon_select,R.mipmap.contact_icon_unselect));

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
                if (contactFragment==null){
                    contactFragment = new ContactFragment();
                }
                tb.switchContent(contactFragment);
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