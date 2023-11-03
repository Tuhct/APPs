package com.tuhct.radom;
        import androidx.appcompat.app.AppCompatActivity;

        import android.annotation.SuppressLint;
        import android.content.Intent;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.os.Bundle;
        import android.util.Base64;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.ListView;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.sql.Connection;
        import java.sql.PreparedStatement;
        import java.sql.Statement;
        import java.util.ArrayList;
        import java.util.List;

@SuppressLint("Range")
public class Activity2 extends AppCompatActivity {
    //1、定义对象
    ListView listView;

    public class initdate {
        //public Bitmap bitmap;
        //public String content;
        //public String data;
        /*public  initdate (Bitmap bitmap ,String context,String time){
            this.bitmap =bitmap;
            this.content =context;
            this.data =time;
        }*/

    }


   /* public static Bitmap base64ToBitmap(String base64info) {
        byte[] bytes = Base64.decode(base64info, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record);
        //2、绑定控件
        listView=(ListView) findViewById(R.id.list_view);


        List<String> sqllist = new ArrayList<>();

        con helper = new con(Activity2.this);//创建对象
        SQLiteDatabase db=helper.getReadableDatabase();
        String table="qd_1";
        String[] columns=new String[]{"records"};
        Cursor c = db.query(table,columns, null, null, null, null, null);
        if(c!= null){
            while (c.moveToNext()) {
                sqllist.add(c.getString(c.getColumnIndex("records")));
            }
        }
        c.close();
        db.close();//关闭数据库

        // 新建临时字符串数组

            // 先遍历将原来的字符串数组数据添加到临时字符串数组

            // 在末尾添加上需要追加的数据



        //3、准备数据

        //TextView a=(ListView)findViewById(R.id.list_view);
       /* Intent intent=getIntent();
        String[] item={"message"};
        //int item_size=0;
        try {
            if (intent.getExtras() != null) {
                if (intent.getExtras().getInt("flag") == 1)
                    item[0]=(intent.getExtras().getString("name"));
            }
        }catch (NullPointerException e){
            e.printStackTrace();
            return;
        }*/

        //String message=intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        //List<String> data=new ArrayList<String>();
        //data.add(message);
        //String[] data={"菠萝","芒果","西瓜","message"};
        //4、创建适配器 连接数据源和控件的桥梁
        //参数 1：当前的上下文环境
        //参数 2：当前列表项所加载的布局文件
        //(android.R.layout.simple_list_item_1)这里的布局文件是Android内置的，里面只有一个textview控件用来显示简单的文本内容
        //参数 3：数据源
        ArrayAdapter<String> adapter=new ArrayAdapter<>(Activity2.this,android.R.layout.simple_list_item_1,sqllist);
        //5、将适配器加载到控件中
        listView.setAdapter(adapter);
        //6、为列表中选中的项添加单击响应事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                String result=((TextView)view).getText().toString();
                Toast.makeText(Activity2.this,"您选择的数据是："+result,Toast.LENGTH_LONG).show();
            }
        });

        /*//获取intent
        Intent intent=getIntent();
        //获取数据并显示到text中
        textview.setText(intent.getStringExtra("name"));

        //获得绑定的数据包
        Bundle bundle = getIntent().getExtras();
        user user=(user) bundle.getSerializable("user");
        String str="名字："+user.getName()+" 年龄："+user.getAge();
*/


    }
}
