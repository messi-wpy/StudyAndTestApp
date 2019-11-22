package com.example.studyandtestapp.CustomView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.studyandtestapp.R;

public class TitleBar extends RelativeLayout {

    private ImageView iv_titlebar_left;
    private ImageView iv_titlebar_right;
    private TextView tv_titlebar_title;
    private RelativeLayout layout_titlebar_rootlayout;
    private String titleName;
    private int mColor= Color.RED;
    private int mTextColor=Color.WHITE;

    public TitleBar(Context context) {
        super(context);
        initView(context);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a=context.obtainStyledAttributes(attrs,R.styleable.TitleBar);
        mColor=a.getColor(R.styleable.TitleBar_title_bg,Color.BLUE);
        mTextColor=a.getColor(R.styleable.TitleBar_title_text_color,Color.BLACK);
        titleName=a.getString(R.styleable.TitleBar_title_text);

        a.recycle();
        initView(context);

    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context){
        LayoutInflater.from(context).inflate(R.layout.titlebar_layout,this,true);
        iv_titlebar_left=findViewById(R.id.iv_titlebar_left);
        iv_titlebar_right=findViewById(R.id.iv_titlebar_right);
        tv_titlebar_title=findViewById(R.id.tv_titlebar_title);

        layout_titlebar_rootlayout=findViewById(R.id.layout_titlebar_rootlayout);
        layout_titlebar_rootlayout.setBackgroundColor(mColor);
        tv_titlebar_title.setTextColor(mTextColor);
        setTitle(titleName);
    }

    public void setTitle(String titleName){
        if (!TextUtils.isDigitsOnly(titleName)){
            tv_titlebar_title.setText(titleName);
        }
    }
    public void setLeftListenner(OnClickListener listenner){
        iv_titlebar_left.setOnClickListener(listenner);
    }
    public void setRightListenner(OnClickListener listenner){
        iv_titlebar_right.setOnClickListener(listenner);
    }

}
