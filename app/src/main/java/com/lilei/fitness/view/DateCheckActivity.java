package com.lilei.fitness.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lilei.fitness.R;
import com.lilei.fitness.entity.User;
import com.lilei.fitness.utils.Constants;
import com.lilei.fitness.utils.DateUtils;
import com.lilei.fitness.utils.MyDialogHandler;
import com.lilei.fitness.utils.SharedPreferencesUtils;
import com.lilei.fitness.view.base.BaseActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.aigestudio.datepicker.bizs.calendars.DPCManager;
import cn.aigestudio.datepicker.bizs.decors.DPDecor;
import cn.aigestudio.datepicker.bizs.themes.DPBaseTheme;
import cn.aigestudio.datepicker.bizs.themes.DPCNTheme;
import cn.aigestudio.datepicker.bizs.themes.DPTManager;
import cn.aigestudio.datepicker.bizs.themes.DPTheme;
import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.views.DatePicker;
import okhttp3.Call;

import static com.lilei.fitness.utils.DateUtils.getCurrentDatetime;

/**
 * Created by djzhao on 17/05/01.
 */

public class DateCheckActivity extends BaseActivity implements View.OnClickListener {
    DPTheme dpTheme;
    private String TITLE_NAME = "每日打卡";
//    private View title_back;
    private TextView titleText;

    private Context mContext;
    private DatePicker picker;
    private Button btnPick;


    private MyDialogHandler uiFlusHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setDpTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_check);
        findViewById();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        echoChecked();
    }

    /**
     * 获取签到记录
     */
    private void getDailyCheck() {
        uiFlusHandler.sendEmptyMessage(SHOW_LOADING_DIALOG);
        String url = Constants.BASE_URL + "DailyCheck?method=getCheckedList";
        OkHttpUtils
                .post()
                .url(url)
                .id(2)
                .addParams("userId", Constants.USER.getUserId() + "")
                .build()
                .execute(new MyStringCallback());
    }

    @Override
    protected void findViewById() {
//        this.title_back = $(R.id.title_back);
        this.titleText = $(R.id.titleText);

        picker = (DatePicker) findViewById(R.id.date_date_picker);
        btnPick = (Button) findViewById(R.id.date_btn_check);
    }

    @Override
    protected void initView() {
        mContext = this;
//        this.title_back.setOnClickListener(this);
//        this.titleText.setText(TITLE_NAME);
        btnPick.setOnClickListener(this);
        uiFlusHandler = new MyDialogHandler(mContext, "刷新数据...");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.title_back:
//                this.finish();
//                break;
            case R.id.date_btn_check:
                todayCheck();
                break;
        }
    }

    /**
     * 今日打卡
     */
    private void todayCheck() {

        uiFlusHandler.setTip("正在打卡...");
        uiFlusHandler.sendEmptyMessage(SHOW_LOADING_DIALOG);
        String url = Constants.BASE_URL + "DailyCheck?method=check";
        OkHttpUtils
                .post()
                .url(url)
                .id(1)
                .addParams("userId", Constants.USER.getUserId() + "")
                .build()
                .execute(new MyStringCallback());
    }

    public class MyStringCallback extends StringCallback {
        @Override
        public void onResponse(String response, int id) {
            uiFlusHandler.sendEmptyMessage(DISMISS_LOADING_DIALOG);
            switch (id) {
                case 1:
                    if (response.contains("success")) {



                        DisplayToast("今日打卡成功");
                    } else {
                        DisplayToast(response);
                    }
                    break;
                case 2:
                    if (response.contains("error")) {
                        DisplayToast("暂时无法获取数据");
                    } else {
                        String[] dates = response.split(",");
                        for (String s : dates) {

                        }
                    }
                    break;
            }
        }

        @Override
        public void onError(Call arg0, Exception arg1, int arg2) {
            uiFlusHandler.sendEmptyMessage(DISMISS_LOADING_DIALOG);
            DisplayToast("网络链接出错！");
        }
    }

    /**
     * 已经打卡数据展示
     */
    public void echoChecked() {


        DPCManager.getInstance().setDecorTR(Constants.DAILYCHECKEDLIST);

        Calendar today = Calendar.getInstance();

        picker.setDate(today.get(Calendar.YEAR), today.get(Calendar.MONTH) + 1);

        picker.setFestivalDisplay(false);

        picker.setTodayDisplay(true);
        picker.setHolidayDisplay(false);
        picker.setDeferredDisplay(false);
        picker.setMode(DPMode.NONE);
        picker.setDPDecor(new DPDecor() {
            @Override
            public void drawDecorTR(Canvas canvas, Rect rect, Paint paint, String data) {
                super.drawDecorTR(canvas, rect, paint, data);
                paint.setColor(0xFFF7CF2E);
                canvas.drawCircle(rect.centerX(), rect.centerY(), rect.width() / 2, paint);
            }
        });
//        Canvas canvas=new Canvas();
//        Paint p = new Paint();
//        p.setColor(Color.RED);// 设置红色
//
//        canvas.drawText("画圆：", 10, 20, p);// 画文本
//        canvas.drawCircle(60, 20, 10, p);// 小圆
//        p.setAntiAlias(true);// 设置画笔的锯齿效果。 true是去除，大家一看效果就明白了
//        canvas.drawCircle(120, 20, 20, p);


    }

    //设置主题
    public void setDpTheme() {
        dpTheme = new DPTheme() {
            @Override
            public int colorBG() {
                return 0xFFFFFFFF;
            }

            @Override
            public int colorBGCircle() {
                return Color.YELLOW;
            }

            @Override
            public int colorTitleBG() {
                return 0xFF423c46;
            }

            @Override
            public int colorTitle() {
                return 0xEEFFFFFF;
            }

            @Override
            public int colorToday() {
                return 0x5FF7CF2E;
            }

            @Override
            public int colorG() {
                return 0xEE333333;
            }

            @Override
            public int colorF() {
                return 0xEEC08AA4;
            }

            @Override
            public int colorWeekend() {
                return 0xFF716D73;
            }

            @Override
            public int colorHoliday() {
                return 0x80FED6D6;
            }
        };
        DPTManager.getInstance().initCalendar(dpTheme);
    }

    public String removeHeadingZero(String str) {
        if (str.startsWith("0")) {
            return str.substring(1);
        } else {
            return str;
        }
    }
}
