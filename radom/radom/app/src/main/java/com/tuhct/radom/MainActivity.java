package com.tuhct.radom;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/*import com.dao.UserDao;
*/
import java.io.Serializable;
import java.text.BreakIterator;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;



public class MainActivity extends AppCompatActivity {
    //public static final String EXTRA_MESSAGE="com.example.radom.MESSAGE";
    con helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper=new con(MainActivity.this);

       /* database.mymysql();*/

        Button button2=findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,Activity2.class);
                startActivity(intent);
            }
        });

/*        UserDao user=new UserDao();
        try {
            user.select("qd");
        } catch (Exception e) {
            e.printStackTrace();
        }
*/

        Button button1 = findViewById(R.id.button);
//        在代码中修改按钮rollButton文本属性
        button1.setText("Pick up");

//        给按钮rollButton设置点击监听器，一旦用户点击按钮，就触发监听器的onClick方法
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

//                产生从1-5的随机数
                Random rand = new Random();
                int randNumber = rand.nextInt(5) + 1;
//                String randNumber2 = rand.nextInt(5)+1;

//                获取对ImageView\TextView对象的引用
                ImageView img = findViewById(R.id.imageView);

                TextView text = findViewById(R.id.textView);

                int drawableResource;
//                将随机数与对应的图片资源联系起来
                switch (randNumber) {
                    case 1:
                        drawableResource = R.drawable.one_1;
                        break;
                    case 2:
                        drawableResource = R.drawable.one_2;
                        break;
                    case 3:
                        drawableResource = R.drawable.one_3;
                        break;
                    case 4:
                        drawableResource = R.drawable.one_4;
                        break;
                    case 5:
                        drawableResource = R.drawable.one_5;
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + randNumber);
                }

//                随机图片根据rangNumber的值对应drawableResource的int值，实例Drawable类
                Drawable drawable = getBaseContext().getResources().getDrawable(drawableResource);
//                文字根据图片编号显示，将编号从int 转化为string
                String text1= String.valueOf(randNumber);
//                设置ImageView控件最终显示的图片
                img.setImageDrawable(drawable);
                text.setText(text1);

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss ");
                Date curDate = new Date(System.currentTimeMillis());
                String str = formatter.format(curDate);

                helper.getWritableDatabase();
                SQLiteDatabase db=helper.getWritableDatabase();
                ContentValues values=new ContentValues();
                //values.put("id","1");
                values.put("records",text.getText().toString()+" "+str);
                db.insert("qd_1",null,values);
                values.clear();

                /*'''
                SQLiteDatabase db=helper.getWritableDatabase();

                // Create a new map of values, where column names are the keys
                ContentValues values = new ContentValues();
                values.put(db.COLUMN_NAME_TITLE, title);
                values.put(db.COLUMN_NAME_SUBTITLE, subtitle);

                // Insert the new row, returning the primary key value of the new row
                long newRowId = db.insert(db.qd_1, null, (text1+" "+str));'''*/
                
                //ContentValues values = new ContentValues();
                //values.put(db.qd_, text.getText().toString());

                Toast.makeText(MainActivity.this,text1+" "+str,Toast.LENGTH_LONG).show();

                /*Bundle detain=new Bundle();
                detain.putString("name",text.getText().toString()+" "+str);
                detain.putInt("flag",1);

                Intent intent2=new Intent(MainActivity.this,Activity2.class);
               // String message=text.getText().toString();
                intent2.putExtras(detain);
                startActivity(intent2);*/

                //实例化一个新的窗口，并指定从那个窗口到哪个窗口
            //    Intent intent = new Intent(MainActivity.this, Activity2.class);
            //    //将数据发送到intent中
            //    BreakIterator ed_text = null;
            //    intent.putExtra("name",ed_text.getText().toString().trim() );
                //启动新窗口
            //    startActivity(intent);

            }



        });

    }

    /*public void sendMessage(View view){
        Intent intent=new Intent(MainActivity.this,Activity2.class);
        TextView text2= (TextView) findViewById(R.id.textView);
        String message=text2.getText().toString();
        intent.putExtras(EXTRA_MESSAGE, message);
    }*/
}