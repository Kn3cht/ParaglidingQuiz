package com.example.paraglidingquiz.Tools;

import android.content.Context;
import android.util.AttributeSet;
import com.github.mikephil.charting.data.PieData;

public class PieChart extends com.github.mikephil.charting.charts.PieChart {

    public PieChart(Context context) {
        super(context);
    }

    public PieChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PieChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void load(PieData data) {
        this.setUsePercentValues(true);
        this.getDescription().setEnabled(false);
        this.setDragDecelerationFrictionCoef(0.95f);
        this.setDrawHoleEnabled(true);
        this.setDrawEntryLabels(false);
        this.setTransparentCircleRadius(60f);
        this.setUsePercentValues(false);
        this.setDrawEntryLabels(false);
        this.setHapticFeedbackEnabled(false);
        this.setRotationEnabled(false);
        this.setTouchEnabled(false);

        this.setData(data);
    }
}
