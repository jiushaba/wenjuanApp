package com.jianshushe.wenjuan.fragment.chart;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.jianshushe.wenjuan.R;
import com.jianshushe.wenjuan.entity.ChartEntity.Questions;

import java.util.ArrayList;

/**
 * 折线图Fragment
 */


@SuppressLint("ValidFragment")
public class ChartFragment extends Fragment {


    LineChart lineChart;
    Questions questions;

    @SuppressLint("ValidFragment")
    public ChartFragment(Questions questions) {
        this.questions = questions;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chart, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lineChart = view.findViewById(R.id.chart);
        LineData lineData = makeDate(questions);
        setLineChart(lineChart, lineData);
    }

    public LineData makeDate(Questions questions) {
        ArrayList<String> x = new ArrayList<>();
        ArrayList<Entry> y = new ArrayList<>();
        for (int i = 0; i < questions.type2List.size(); i++) {
            x.add(i+"");
        }
        for (int i = 0; i < questions.type2List.size(); i++) {
            Entry entry = new Entry(Integer.parseInt(questions.type2List.get(i).getAnswer()), i);
            y.add(entry);
        }
        LineDataSet lineDataSet = new LineDataSet(y, "");
        lineDataSet.setCircleColor(Color.GREEN);
        lineDataSet.setCircleColorHole(Color.GRAY);
        lineDataSet.setValueTextColor(Color.BLACK);
        lineDataSet.setValueTextSize(10f);
        lineDataSet.setLineWidth(3f);
        lineDataSet.setColor(Color.BLACK);
        ArrayList<LineDataSet> lineDataSets = new ArrayList<>();
        lineDataSets.add(lineDataSet);
        LineData lineData = new LineData(x, lineDataSets);
        return lineData;
    }

    public void setLineChart(LineChart lineChart, LineData lineData) {
        lineChart.setDrawGridBackground(false);
        lineChart.setDrawBorders(false);
        lineChart.getXAxis().setEnabled(true);
        lineChart.setData(lineData);
        lineChart.getAxisLeft().setEnabled(true);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.setDescription("");
        lineChart.getXAxis().setEnabled(true);
        lineChart.setHighlightEnabled(false);
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
    }


}
