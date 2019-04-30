/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.camerademo.camera;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;

/**
 * 创建日期：2019/4/30 on 9:52 AM.
 * 作者：liuxun
 * 描述：
 */

public class FaceView extends View {

    private Paint mPaint;
    private String mCorlor = "#42ed45";
    ArrayList<RectF> mFaces = null; //人脸信息

    public FaceView(Context context) {
        super(context);
    }

    public FaceView(Context context,
            @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FaceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public FaceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr,
                    int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor(mCorlor));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1f,
                getContext().getResources().getDisplayMetrics()));
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mFaces == null){
            return;
        }
        for (RectF e : mFaces) {
            canvas.drawRect(e, mPaint);
        }
    }

    void setFaces(ArrayList<RectF> faces) {
        this.mFaces = faces;
        invalidate();
    }


}
