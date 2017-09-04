package higo.com.todo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by user on 2017/8/26.
 */

public class EditTodoItemActivity extends AppCompatActivity {
    private EditText txtTitle;
    private Button btnSave,btnCancel;
    private Context context;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context=getApplicationContext();
        txtTitle=(EditText)findViewById(R.id.txtTitle);
        txtTitle.setText(getIntent().getStringExtra("title"));
        btnSave=(Button)findViewById(R.id.btnSave);
        btnCancel=(Button)findViewById(R.id.btnCancel);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!txtTitle.getText().toString().equals("")) {
                    Intent intent = getIntent();
                    intent.setClass(EditTodoItemActivity.this, MainActivity.class);
                    intent.putExtra("title",txtTitle.getText().toString());
                    setResult(RESULT_OK,intent);
                    finish();
                }else{
                    Toast.makeText(context,context.getString(R.string.edit_empty_tip),Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = getIntent();
                intent.setClass(EditTodoItemActivity.this, MainActivity.class);
                setResult(RESULT_CANCELED,intent);
                finish();
            }
        });

    }

}
