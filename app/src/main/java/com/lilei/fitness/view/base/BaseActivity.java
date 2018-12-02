package com.lilei.fitness.view.base;

import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.lilei.fitness.image.ImageLoaderConfig;
import com.lilei.fitness.utils.AppManager;
import com.lilei.fitness.utils.Constants;
import com.lilei.fitness.utils.MyDialogHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public abstract class BaseActivity extends Activity {

 //服务就不开了
	protected InputMethodManager imm;
//	private TelephonyManager tManager;
	
	protected final int SHOW_LOADING_DIALOG = 0x0102;
	protected final int DISMISS_LOADING_DIALOG = 0x0103;
	protected MyDialogHandler uiFlusHandler;
	
	public void DisplayToast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
	

	
	protected abstract void findViewById();


	protected abstract void initView();

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		AppManager.getInstance().addActivity(this);
		if (!ImageLoader.getInstance().isInited())
			ImageLoaderConfig.initImageLoader(this, Constants.BASE_IMAGE_CACHE);
//		this.tManager = ((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE));
		this.imm = ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE));
		
	}
	
	protected void onDestroy() {
		super.onDestroy();
	}

	protected void onPause() {
		super.onPause();
	}

	protected void onRestart() {
		super.onRestart();
	}

	protected void onResume() {
		super.onResume();
	}

	protected void onStart() {
		super.onStart();
	}

	protected void onStop() {
		super.onStop();
	}

	protected void openActivity(Class<?> paramClass) {
		Intent localIntent = new Intent(this, paramClass);
		startActivity(localIntent);
	}



    

	protected <T> T $(int viewID) {
		return (T) findViewById(viewID);
	}

	
}