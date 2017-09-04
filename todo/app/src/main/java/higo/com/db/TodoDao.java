package higo.com.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import higo.com.bean.TodoItem;

/**
 * Created by user on 2017/8/26.
 */

public class TodoDao {
     private Context context;
     TodoSQLiteOpenHelper sqliteOpenHelper;
     public TodoDao(Context context){
         this.context=context;
         sqliteOpenHelper=new TodoSQLiteOpenHelper(context);
     }

    public void save(TodoItem todoItem){
        sqliteOpenHelper.getWritableDatabase().execSQL("insert into " + TodoSQLiteOpenHelper.TABLE_NAME
                +" (TITLE, CREATETIME) values "
                +"('"
                +todoItem.getTitle().replace("'","\'")
                +"', '"
                +todoItem.getCreatetime()
                +"')"
        );
    }

    public void update(TodoItem todoItem){
//        String sql="UPDATE TABLE "+TodoSQLiteOpenHelper.TABLE_NAME+" SET TITLE='"
//                +todoItem.getTitle()
//                +"',CREATETIME = '"
//                +todoItem.getCreatetime()
//                +"' WHERE ID = "+todoItem.getId();
//        sqliteOpenHelper.getWritableDatabase().execSQL(sql);
        ContentValues cv=new ContentValues();
        cv.put("TITLE",todoItem.getTitle().replace("'","\'"));
        cv.put("CREATETIME",todoItem.getCreatetime());
        sqliteOpenHelper.getWritableDatabase().update(TodoSQLiteOpenHelper.TABLE_NAME,cv,"ID=?",
                new String[]{String.valueOf(todoItem.getId())});

    }
    public void delete(int id){
        String sql="DELETE FROM "+TodoSQLiteOpenHelper.TABLE_NAME+" WHERE ID="+id;
        sqliteOpenHelper.getWritableDatabase().execSQL(sql);
    }
    public List<TodoItem> loadItems(){
        List<TodoItem> todoItems=new ArrayList<TodoItem>();
        String sql="SELECT ID, TITLE, CREATETIME FROM TODO_ITEM ORDER BY ID DESC";
        Cursor cursor=sqliteOpenHelper.getReadableDatabase().rawQuery(sql,null);
        while(cursor.moveToNext()){
            int id=cursor.getInt(0);
            String title=cursor.getString(1);
            String createtime=cursor.getString(2);
            todoItems.add(new TodoItem(id,title,createtime));
        }
        cursor.close();
        sqliteOpenHelper.getReadableDatabase().close();
        return todoItems;

    }
}
