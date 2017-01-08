package rfreitas.com.br.record;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import rfreitas.com.br.record.player.PlayerFragment;
import rfreitas.com.br.record.record.RecordFragment;

/**
 * Created by rafaelfreitas on 1/7/17.
 */

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;

    private TabLayout.Tab recordTab;
    private TabLayout.Tab playerTab;

    private Fragment recordFragment, playerFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        recordFragment = new RecordFragment();
        playerFragment = new PlayerFragment();

        recordTab = tabLayout.newTab().setText("Record").setTag(recordFragment);
        playerTab = tabLayout.newTab().setText("Play").setTag(playerFragment);

        tabLayout.addOnTabSelectedListener(getTabSelectedListener());

        tabLayout.addTab(recordTab, 0, true);
        tabLayout.addTab(playerTab, 1);
    }

    public TabLayout.OnTabSelectedListener getTabSelectedListener(){
        return new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                init((Fragment) tab.getTag(), R.id.activity_container);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                die((Fragment) tab.getTag());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        };
    }

    private void init(Fragment fragment, int layout){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(layout, fragment);
        transaction.commit();
    }

    public void die(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.remove(fragment);
        transaction.commit();
    }

}
