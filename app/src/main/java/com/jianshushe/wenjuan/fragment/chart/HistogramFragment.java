package com.jianshushe.wenjuan.fragment.chart;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.jianshushe.wenjuan.R;
import com.jianshushe.wenjuan.entity.ChartEntity.Questions;

import java.util.ArrayList;


/**
 * 柱状图Fragment
 */
@SuppressLint("ValidFragment")
public class HistogramFragment extends Fragment {

    Questions questions;
    private BarChart barChart;
    private XAxis xAxis;
    int type;
    @SuppressLint("ValidFragment")
    public HistogramFragment(Questions questions,int type) {
        this.questions = questions;
        this.type = type;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_histogram, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        barChart = view.findViewById(R.id.chart);
        if (type == 0) {
            initChartView();
        } else if (type==2){
            type2();
        }

    }




    private void  initChartView(){
        //1、基本设置
        xAxis=barChart.getXAxis();
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        barChart.setDrawGridBackground(false); // 是否显示表格颜色
        barChart.getAxisLeft().setDrawAxisLine(false);
        barChart.setTouchEnabled(false); // 设置是否可以触摸
        barChart.setDragEnabled(true);// 是否可以拖拽
        barChart.setScaleEnabled(true);// 是否可以缩放
        //2、y轴和比例尺

        barChart.setDescription("");// 数据描述

        barChart.getAxisLeft().setEnabled(false);
        barChart.getAxisRight().setEnabled(false);

        Legend legend = barChart.getLegend();//隐藏比例尺
        legend.setEnabled(false);

        //3、x轴数据,和显示位置
        ArrayList<String> xValues = new ArrayList<String>();
        for (int i = 0; i < questions.list.size(); i++) {
            xValues.add(questions.list.get(i).getOptionName());
        }
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//数据位于底部


        //4、y轴数据
        ArrayList<BarEntry> yValues = new ArrayList<BarEntry>();
        //new BarEntry(20, 0)前面代表数据，后面代码柱状图的位置；
        for (int i = 0; i < questions.list.size(); i++) {
            yValues.add(new BarEntry(questions.list.get(i).getNum(), i));
        }
        //5、设置显示的数字为整形
        BarDataSet barDataSet=new BarDataSet(yValues,"");
        //6、设置柱状图的颜色
        barDataSet.setColors(new int[]{Color.rgb(104, 202, 37), Color.rgb(192, 32, 32),
                Color.rgb(34, 129, 197), Color.rgb(175, 175, 175)});
        //7、显示，柱状图的宽度和动画效果
        BarData barData = new BarData(xValues, barDataSet);
        barDataSet.setBarSpacePercent(40f);//值越大，柱状图就越宽度越小；
        barChart.animateY(1000);
        barChart.setData(barData);

    }
    private void  type2(){
        //1、基本设置
        xAxis=barChart.getXAxis();
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        barChart.setDrawGridBackground(false); // 是否显示表格颜色
        barChart.getAxisLeft().setDrawAxisLine(false);
        barChart.setTouchEnabled(false); // 设置是否可以触摸
        barChart.setDragEnabled(true);// 是否可以拖拽
        barChart.setScaleEnabled(true);// 是否可以缩放
        //2、y轴和比例尺

        barChart.setDescription("");// 数据描述
        barChart.getAxisLeft().setEnabled(false);
        barChart.getAxisRight().setEnabled(false);

        Legend legend = barChart.getLegend();//隐藏比例尺
        legend.setEnabled(false);

        //3、x轴数据,和显示位置
        ArrayList<String> xValues = new ArrayList<String>();
        for (int i = 0; i <= questions.type2List.size(); i++) {
            xValues.add(i+"");
        }
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//数据位于底部


        //4、y轴数据
        ArrayList<BarEntry> yValues = new ArrayList<BarEntry>();
        //new BarEntry(20, 0)前面代表数据，后面代码柱状图的位置；
        for (int i = 0; i < questions.type2List.size(); i++) {
            yValues.add(new BarEntry(Integer.parseInt(questions.type2List.get(i).getAnswer()), i));
        }
        //5、设置显示的数字为整形
        BarDataSet barDataSet=new BarDataSet(yValues,"");
        //6、设置柱状图的颜色
        barDataSet.setColors(new int[]{Color.rgb(104, 202, 37), Color.rgb(192, 32, 32),
                Color.rgb(34, 129, 197), Color.rgb(175, 175, 175)});
        //7、显示，柱状图的宽度和动画效果
        BarData barData = new BarData(xValues, barDataSet);
        barDataSet.setBarSpacePercent(40f);//值越大，柱状图就越宽度越小；
        barChart.animateY(1000);
        barChart.setData(barData);

    }
}
