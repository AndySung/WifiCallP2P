package com.example.andrd_ado_vdo_tkbk_demo.Fragments;

import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.andrd_ado_vdo_tkbk_demo.R;

public class BaseFragment extends Fragment implements OnClickListener {
	protected static final int CONTACTS_ID = 0;
	protected static final int DIALER_ID = 1;
	protected static final int SETTINGS_ID = 2;
	protected static final int MORE_ID = 3;
	/**
	 * 联系人
	 */
	protected MenuItem contactsItem;
	/**
	 * 拨号
	 */
	protected MenuItem dialerItem;
	/**
	 * 设置
	 */
	protected MenuItem settingsItem;
	/**
	 * 更多
	 */
	protected MenuItem moreItem;

	/************************** 主题资源 ****************************/
	// Homeicon资源id
	protected static int homeIconResId;
	// 最近聯繫人icon
	protected static int recentContactIconResId;
	// 聯繫人icon
	protected static int contactIconResId;
	// 设置键icon
	//protected static int sbSettingsIconResId;
	// 更多键icon
	//protected static int sbMoreIconResId;
	// home按下icon资源id
	protected static int homePressIconResId;
	//  最近聯繫人按下icon
	protected static int srecentContactPressIconResId;
	// 按下拨号键显示icon
	protected static int contactPressIconResId;
	// 按下设置键icon
//	protected static int sbSettingsPressIconResId;

	protected void initViews(View parent) {
		setHasOptionsMenu(true);

		TypedArray a = getActivity().obtainStyledAttributes(null,
				R.styleable.ContactsAppView, R.attr.ContactsAppStyle, 0);
		homeIconResId = a.getResourceId(
				R.styleable.ContactsAppView_smartBarContactsIcon,
				R.mipmap.home_icon_unselect);
		recentContactIconResId = a.getResourceId(
				R.styleable.ContactsAppView_smartBarDialerIcon,
				R.mipmap.recent_icon_unselect);
		contactIconResId = a.getResourceId(
				R.styleable.ContactsAppView_smartBarDialerDisplayIcon,
				R.mipmap.contact_icon_unselect);

//		sbSettingsIconResId = a.getResourceId(
//				R.styleable.ContactsAppView_smartBarSettingsIcon,
//				R.drawable.ic_tab_settings_n);
//		sbMoreIconResId = a.getResourceId(
//				R.styleable.ContactsAppView_smartBarMoreIcon,
//				R.drawable.ic_tab_more_n);

		homePressIconResId = a.getResourceId(
				R.styleable.ContactsAppView_smartBarContactsPressIcon,
				R.mipmap.home_icon_select);
		recentContactIconResId = a.getResourceId(
				R.styleable.ContactsAppView_smartBarDialerPressIcon,
				R.mipmap.recent_icon_select);
		contactPressIconResId = a.getResourceId(
				R.styleable.ContactsAppView_smartBarDialerDisplayPressIcon,
				R.mipmap.contact_icon_select);
//		sbSettingsPressIconResId = a.getResourceId(
//				R.styleable.ContactsAppView_smartBarSettingsPressIcon,
//				R.drawable.ic_tab_settings_p);
		a.recycle();
	}

	@Override
	public void onClick(View view) {
		// 父类处理
	}

	/**
	 * 一律使用此启动方式，添加左侧滑出动画
	 */
	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		getActivity().overridePendingTransition(R.anim.pt__push_left_in,
				R.anim.pt__push_left_out);
	}

	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		super.startActivityForResult(intent, requestCode);
		getActivity().overridePendingTransition(R.anim.pt__push_left_in,
				R.anim.pt__push_left_out);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}