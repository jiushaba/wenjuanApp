package com.jianshushe.wenjuan.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jianshushe.wenjuan.R;
import com.jianshushe.wenjuan.entity.ChartEntity.Questions;

public class TextActivity extends FragmentActivity {

    ListView listView;
    Questions questions;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        listView = findViewById(R.id.listView);
         questions = (Questions) getIntent().getSerializableExtra("list");
        TextAdapter adapter = new TextAdapter();
        listView.setAdapter(adapter);
    }



    class TextAdapter extends BaseAdapter {



        public TextAdapter() {

        }

        @Override
        public int getCount() {
            return questions.type2List.size();
        }

        @Override
        public Object getItem(int position) {
            return questions.type2List.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.listview_item, null);
            TextView name = convertView.findViewById(R.id.name);
            name.setText(questions.type2List.get(position).getAnswer());
            return convertView;
        }
    }

}
