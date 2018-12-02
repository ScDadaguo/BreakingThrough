package com.lilei.fitness.utils;


import java.util.Stack;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

public class AppManager {
	private static Stack<Activity> mActivityStack;
	private static AppManager mAppManager;

	public static AppManager getInstance() {
		if (mAppManager == null)
			mAppManager = new AppManager();
		return mAppManager;
	}

		@SuppressLint("WrongConstant")
		@SuppressWarnings("deprecation")
			public void AppExit(Context paramContext) {
			try {
				killAllActivity();
				((ActivityManager) paramContext.getSystemService("activity"))
						.restartPackage(paramContext.getPackageName());
				System.exit(0);
				return;
			} catch (Exception localException) {
			}
		}

	public void addActivity(Activity paramActivity) {
		if (mActivityStack == null)
			mActivityStack = new Stack<Activity>();
		mActivityStack.add(paramActivity);
	}







	public void killAllActivity() {
		int i = 0;
		int j = mActivityStack.size();
		while (true) {
			if (i >= j) {
				mActivityStack.clear();
				return;
			}
			if (mActivityStack.get(i) != null)
				((Activity) mActivityStack.get(i)).finish();
			i++;
		}
	}

}
