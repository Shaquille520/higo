package higo.com.todo;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import higo.com.adapter.TodoAdapter;
import higo.com.bean.TodoItem;
import higo.com.db.TodoDao;
import higo.com.util.DateformatUtil;

public class MainActivity extends AppCompatActivity {


    public static final int EDIT_ITEM_REQUEST_CODE=647;
    private ListView todoListView ;
    private EditText txtTitle;
    private TodoAdapter adapter;
    private List<TodoItem> todoItems;
    private TodoDao todoDao;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context=getApplicationContext();
        txtTitle=(EditText)findViewById(R.id.txtTitle) ;

        todoListView=(ListView)findViewById(R.id.todoListView);
        todoItems=new ArrayList<TodoItem>();

        adapter=new TodoAdapter(context,todoItems);

        todoListView.setAdapter(adapter);

        todoDao=new TodoDao(context);

        FloatingActionButton fabAddItem = (FloatingActionButton) findViewById(R.id.fabAddItem);
        fabAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!txtTitle.getText().toString().equals("")){
                    todoDao.save(new TodoItem(null,txtTitle.getText().toString(), DateformatUtil.getCurrentDatetime()));
                    txtTitle.setText("");

                    InputMethodManager imm = (InputMethodManager) view.getContext( ).getSystemService( Context.INPUT_METHOD_SERVICE );
                    imm.hideSoftInputFromWindow( view.getApplicationWindowToken( ) , 0 );

                    loadData();

                }else{
                    Toast.makeText(context,context.getString(R.string.edit_empty_tip),Toast.LENGTH_SHORT).show();
                }

            }
        });
        loadData();

        setupListViewListener();

    }
     private void setupListViewListener(){
         todoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                 Intent intent=new Intent(MainActivity.this,EditTodoItemActivity.class);
                 intent.putExtra("position",position);
                 intent.putExtra("title",todoItems.get(position).getTitle());
                 startActivityForResult(intent,EDIT_ITEM_REQUEST_CODE);

             }
         });
         todoListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
             @Override
             public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                 Toast.makeText(context,"delete",Toast.LENGTH_SHORT).show();
                 dialogDeleteItem(i);
                 return true;
             }
         });
     }
     private void loadData(){
         todoItems.clear();
         todoItems.addAll(todoDao.loadItems());
         adapter.notifyDataSetChanged();
     }
     private void dialogDeleteItem(final int position){
         AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
// Add the buttons
         builder.setPositiveButton(R.string.dialog_delete, new DialogInterface.OnClickListener() {
             public void onClick(DialogInterface dialog, int id) {
                 todoDao.delete(todoItems.get(position).getId());
                 todoItems.remove(position);
                 adapter.notifyDataSetChanged();
                 dialog.dismiss();
             }
         });
         builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
             public void onClick(DialogInterface dialog, int id) {
                 dialog.dismiss();
             }
         });
         builder.setMessage(R.string.dialog_delete_msg);
         builder.setTitle(R.string.dialog_delete_title);
         builder.show();
     }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==EDIT_ITEM_REQUEST_CODE){
            if(resultCode==RESULT_OK){
                String title=data.getExtras().getString("title");
                int position=data.getIntExtra("position",-1);
                TodoItem todoItem=todoItems.get(position);
                todoItem.setTitle(title);
                todoItems.set(position,todoItem);
                adapter.notifyDataSetChanged();
                todoDao.update(todoItem);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
