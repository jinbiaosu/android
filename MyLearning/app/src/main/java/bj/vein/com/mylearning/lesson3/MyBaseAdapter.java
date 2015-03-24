package bj.vein.com.mylearning.lesson3;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bj.vein.com.mylearning.R;

/**
 * Created by sjbyyan on 2015/2/16.
 */
public class MyBaseAdapter extends Activity {
    private TextView textView;
    private ListView listView;
    List<Map<String ,Object>> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson3);
        textView=(TextView)findViewById(R.id.lesson3_tv);
        listView=(ListView)findViewById(R.id.lesson3_lv);
        data=getData();
        MyAdapter adapter=new MyAdapter(this);
        listView.setAdapter(adapter);
    }


//make data
    private  List<Map<String, Object>> getData(){
        List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
        Map<String ,Object> map;
        for (int i=0;i<20;i++){
            map=new HashMap<String, Object>();
            map.put("img",R.drawable.launcher);
            map.put("title","哇，listview");
            map.put("info","just listview");
            list.add(map);
        }
        return  list;
    }
    // ViewHolder静态类
    static class ViewHolder {
        public ImageView img;
        public TextView title;
        public TextView info;
    }
    private  class MyAdapter extends BaseAdapter{
        private LayoutInflater mInflater = null;
        public MyAdapter(Context context) {

            super();
            // 根据context上下文加载布局，这里的是MainActivity本身，即this
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            // How many items are in the data set represented by this Adapter.
            // 在此适配器中所代表的数据集中的条目数
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            // Get the data item associated with the specified position in the
            // data set.
            // 获取数据集中与指定索引对应的数据项
            return position;
        }

        @Override
        public long getItemId(int position) {
            // Get the row id associated with the specified position in the
            // list.
            // 获取在列表中与指定索引对应的行id
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            // 如果缓存convertView为空，则需要创建View
            if (convertView == null) {
                holder = new ViewHolder();
                // 根据自定义的Item布局加载布局
                convertView = mInflater.inflate(R.layout.lesson3_list_items, null);
                holder.img = (ImageView) convertView.findViewById(R.id.img);
                holder.title = (TextView) convertView.findViewById(R.id.tv);
                holder.info = (TextView) convertView.findViewById(R.id.info);
                // 将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.img.setBackgroundResource((Integer) data.get(position).get(
                    "img"));
            holder.title.setText((String) data.get(position).get("title"));
            holder.info.setText((String) data.get(position).get("info"));

            return convertView;
        }
    }


}
