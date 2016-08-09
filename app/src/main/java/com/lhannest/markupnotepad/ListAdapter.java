package com.lhannest.markupnotepad;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends BaseAdapter {
    private List<NoteData> list;
    private static LayoutInflater inflater;

    public class ViewHolder {
        public TextView textView;
    }

    public ListAdapter(Activity activity, List<NoteData> list) {

        this.list = list;
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (list.size() <= 0) {
            return 1;
        } else {
            return list.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        TextView textView;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custom_listview_item, null);
            holder.textView = (TextView) convertView.findViewById(R.id.list_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        if (list.size() <= 0) {
            holder.textView.setText("No Notes");
        } else {
            NoteData noteData = list.get(position);

            holder.textView.setText(noteData.getTitle());
        }

        return convertView;
    }
}
