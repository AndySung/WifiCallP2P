package com.example.tablayout.utils;

import android.app.Application;
import java.util.ArrayList;
import java.util.List;

public class ContactsApplication extends Application {
	public static ContactsApplication mContext;//需要使用的上下文对象
	private List<ContactEntity> contactList = new ArrayList<ContactEntity>();

	public List<ContactEntity> getContactList() {
		return contactList;
	}

	public void setContactList(List<ContactEntity> contactList) {
		this.contactList = contactList;
	}

	private List<CallLogEntity> callLogList = new ArrayList<CallLogEntity>();

	public List<CallLogEntity> getCallLogList() {
		return callLogList;
	}

	public void setCallLogList(List<CallLogEntity> callLogList) {
		this.callLogList = callLogList;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mContext = this;
		// CrashHandler crashHandler = CrashHandler.getInstance(true);
		// crashHandler.init(this, Constant.APP_SD_PATH +
		// Constant.LOG_FILE_DIR);
		// Constant.initSkinDir(this);
	}

}
