package com.seek.spin.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.jarvis.mytaobao.Data.Data;
import com.jarvis.mytaobao.cart.Cart_F;
import com.jarvis.mytaobao.discover.Discover_F;
import com.jarvis.mytaobao.user.User_F;
import com.seek.spin.R;
import com.javis.mytools.IBtnCallListener;
import com.seek.spin.buyer.PublishGoods;
import com.seek.spin.jpush.ExampleUtil;

import java.util.HashMap;

import cn.jpush.android.api.JPushInterface;

public class HomeActivity extends AppCompatActivity implements OnClickListener, IBtnCallListener {

    // 底部五个图标和标签选项
    private ImageView[] bt_menu = new ImageView[5];
    private int[] bt_menu_id = {R.id.iv_menu_0, R.id.iv_menu_1, R.id.iv_menu_2, R.id.iv_menu_3, R.id.iv_menu_4};

    // 底部标签选中时的状态图标
    private int[] select_on = {R.drawable.guide_home_on, R.drawable.guide_tfaccount_on, R.drawable.guide_discover_on, R.drawable.guide_cart_on, R.drawable.guide_account_on};
    // 底部标签未选中时的状态图标
    private int[] select_off = {R.drawable.bt_menu_0_select, R.drawable.bt_menu_1_select, R.drawable.bt_menu_2_select, R.drawable.bt_menu_3_select, R.drawable.bt_menu_4_select};

    private HomeFragment homeFragment;//首页信息
    private PublishGoods publishGoods;//发布信息list页面
    private Discover_F discover_F;
    private Cart_F cart_F;
    private User_F user_F;

    private EditText msgText;
    public static boolean isForeground = false;


    public HomeActivity() {

        super();

    }

    public HomeActivity(int pageId) {

        switchPage(pageId);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_fa);
        getSaveData();
        initView();
        registerMessageReceiver();  // used for receive msg
    }

    private void getSaveData() {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();

        SharedPreferences sp = getSharedPreferences("SAVE_CART", Context.MODE_PRIVATE);
        int size = sp.getInt("ArrayCart_size", 0);
        for (int i = 0; i < size; i++) {
            hashMap.put("type", sp.getString("ArrayCart_type_" + i, ""));
            hashMap.put("color", sp.getString("ArrayCart_color_" + i, ""));
            hashMap.put("num", sp.getString("ArrayCart_num_" + i, ""));
            Data.arrayList_cart.add(hashMap);
        }

    }

    private void initView() {
        for (int i = 0; i < bt_menu.length; i++) {
            bt_menu[i] = (ImageView) findViewById(bt_menu_id[i]);
            bt_menu[i].setOnClickListener(this);
        }
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
            addFragment(homeFragment);
            showFragment(homeFragment);
        } else {
            showFragment(homeFragment);
        }
        bt_menu[0].setImageResource(select_on[0]);

    }

    @Override
    public void onClick(View v) {

        switchPage(v.getId());
    }

    private void switchPage(int id) {
        switch (id) {
            case R.id.iv_menu_0:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    addFragment(homeFragment);
                    showFragment(homeFragment);
                } else {
                    if (homeFragment.isHidden()) {
                        showFragment(homeFragment);
                    }
                }

                break;
            case R.id.iv_menu_1:
                if (publishGoods == null) {
                    publishGoods = new PublishGoods();
                    if (!publishGoods.isHidden()) {
                        addFragment(publishGoods);
                        showFragment(publishGoods);
                    }
                } else {
                    if (publishGoods.isHidden()) {
                        showFragment(publishGoods);
                    }
                }

                break;
            case R.id.iv_menu_2:
                if (discover_F == null) {
                    discover_F = new Discover_F();
                    if (!discover_F.isHidden()) {
                        addFragment(discover_F);
                        showFragment(discover_F);
                    }
                } else {
                    if (discover_F.isHidden()) {
                        showFragment(discover_F);
                    }
                }

                break;
            case R.id.iv_menu_3:
                if (cart_F != null) {
                    removeFragment(cart_F);
                    cart_F = null;
                }
                cart_F = new Cart_F();
                addFragment(cart_F);
                showFragment(cart_F);

                break;
            case R.id.iv_menu_4:
                if (user_F == null) {
                    user_F = new User_F();
                    if (!user_F.isHidden()) {
                        addFragment(user_F);
                        showFragment(user_F);
                    }
                } else {
                    if (user_F.isHidden()) {
                        showFragment(user_F);
                    }
                }

                break;
        }

        for (int i = 0; i < bt_menu.length; i++) {
            bt_menu[i].setImageResource(select_off[i]);
            if (id == bt_menu_id[i]) {
                bt_menu[i].setImageResource(select_on[i]);
            }
        }
    }

    public void addFragment(Fragment fragment) {
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        ft.add(R.id.show_layout, fragment);
        ft.commit();
    }

    public void removeFragment(Fragment fragment) {
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        ft.remove(fragment);
        ft.commit();
    }

    public void showFragment(Fragment fragment) {
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.cu_push_right_in, R.anim.cu_push_left_out);

        if (homeFragment != null) {
            ft.hide(homeFragment);
        }
        if (publishGoods != null) {
            ft.hide(publishGoods);
        }
        if (discover_F != null) {
            ft.hide(discover_F);
        }
        if (cart_F != null) {
            ft.hide(cart_F);
        }
        if (user_F != null) {
            ft.hide(user_F);
        }

        ft.show(fragment);
        ft.commitAllowingStateLoss();

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "������ذ�ť", Toast.LENGTH_SHORT).show();

        super.onBackPressed();
    }

    @SuppressWarnings("unused")
    private IBtnCallListener btnCallListener;

    @Override
    public void onAttachFragment(Fragment fragment) {
        try {
            btnCallListener = (IBtnCallListener) fragment;
        } catch (Exception e) {
        }

        super.onAttachFragment(fragment);
    }

    @Override
    public void transferMsg() {
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
            addFragment(homeFragment);
            showFragment(homeFragment);
        } else {
            showFragment(homeFragment);
        }
        bt_menu[3].setImageResource(select_off[3]);
        bt_menu[0].setImageResource(select_on[0]);

    }


    // jpush start
    // 初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录。
    private void init() {
        JPushInterface.init(getApplicationContext());
    }


    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
        JPushInterface.onResume(this);
    }


    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
        JPushInterface.onPause(this);
    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }


    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                if (!ExampleUtil.isEmpty(extras)) {
                    showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                }
                setCostomMsg(showMsg.toString());
            }
        }
    }

    private void setCostomMsg(String msg) {
        if (null != msgText) {
            msgText.setText(msg);
            msgText.setVisibility(View.VISIBLE);
        }
    }
    //jpush end


}
