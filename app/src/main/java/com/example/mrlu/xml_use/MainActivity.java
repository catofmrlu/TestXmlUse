package com.example.mrlu.xml_use;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    List<Message> list;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Toast.makeText(this,"启动了",Toast.LENGTH_SHORT).show();

    }

    public  void  click(View v){
        //获取到src文件夹下的资源文件
        java.io.InputStream is = getClassLoader().getResourceAsStream("weather.xml");

        //拿到pull解析器对象
        XmlPullParser xp = Xml.newPullParser();
        //初始化
        try{
            xp.setInput(is, "utf-8");

            //获取当前节点的事件类型，通过事件类型的判断，我们可以知道当
            // 前节点是什么节点，从而确定我们应该做什么操作
            int type = xp.getEventType();

            Message message = null;
            while(type != XmlPullParser.END_DOCUMENT){
                //内部根据节点不同做不同的操作
                switch (type){
                    case XmlPullParser.START_TAG:
                        //					获取当前节点的名字
                        if ("city".equals(xp.getName())){
                            //创建message的javabean对象
                            list = new ArrayList<>();
                        }
                        else if("name".equals(xp.getName())){
                            message = new Message();
                            String name = xp.nextText();
                            message.setName(name);
                        }
                        else if ("pm2.5".equals(xp.getName())){
                            message.setPm25(xp.nextText());
                        }
                        else if ("temperature".equals(xp.getName())){
                            message.setTem(xp.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if ("city".equals(xp.getName())){
                            list.add(message);
                        }
                        break;
                    }
                xp.next();
                }
            for (Message c : list){
                System.out.println(c.toString());
            }
        }
        catch ( Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
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
