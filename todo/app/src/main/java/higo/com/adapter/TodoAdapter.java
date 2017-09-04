package higo.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import higo.com.bean.TodoItem;
import higo.com.todo.R;

/**
 * Created by user on 2017/8/26.
 */

public class TodoAdapter extends BaseAdapter {
    private Context context;
    public List<TodoItem> todoItems;
    private LayoutInflater inflater;
    public TodoAdapter(Context context, List<TodoItem> todoItems){
        this.context=context;
        this.todoItems=todoItems;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;
        if(view==null){

            viewHolder=new ViewHolder();

            view= inflater.inflate(R.layout.todo_listview_item,null);
            viewHolder.tvCreatetime=(TextView)view.findViewById(R.id.tvCreatetime);
            viewHolder.tvTitle=(TextView)view.findViewById(R.id.tvTitle);

            view.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)view.getTag();
        }

        viewHolder.tvCreatetime.setText(todoItems.get(i).getCreatetime());
        viewHolder.tvTitle.setText(todoItems.get(i).getTitle());

        return view;
    }

    @Override
    public Object getItem(int i) {

        return todoItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getCount() {

        return todoItems.size();
    }

    class ViewHolder {
        public TextView tvCreatetime;

        public TextView tvTitle;
    }

}
