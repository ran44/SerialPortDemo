package vendingcabinets.dlc.cn.vendingcabinets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicInteger;

import vendingcabinets.dlc.cn.vendingcabinets.base.serialport.blockingqueue.AbstractTaskQueue;
import vendingcabinets.dlc.cn.vendingcabinets.base.serialport.blockingqueue.Priority;

public class MainActivity extends AppCompatActivity {
    private AbstractTaskQueue abstractTaskQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 10; i++) {
//                    abstractTaskQueue.add(new Task("低级:" + i, Priority.LOW));
                    abstractTaskQueue.add(i % 2 == 0 ? new Task("低级:" + i, Priority.LOW) : new Task2("低级2:" + i, Priority.LOW));
                }
            }
        });
        findViewById(R.id.button_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 10; i++) {
                    abstractTaskQueue.add(i % 2 == 0 ? new Task("默认等级:" + i, Priority.DEFAULT) : new Task2("默认等级2:" + i, Priority.DEFAULT));
                }
            }
        });
        findViewById(R.id.button_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 10; i++) {
                    abstractTaskQueue.add(i % 2 == 0 ? new Task("高级:" + i, Priority.HIGH) : new Task2("高级2:" + i, Priority.HIGH));
                }
            }
        });
        findViewById(R.id.button_4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 10; i++) {
                    abstractTaskQueue.add(i % 2 == 0 ? new Task("马上执行:" + i, Priority.IMMEDTATELY) : new Task2("马上执行2:" + i, Priority.IMMEDTATELY));
                }
            }
        });
        abstractTaskQueue = new AbstractTaskQueue(1);
        abstractTaskQueue.start();
        findViewById(R.id.button_5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (abstractTaskQueue != null) {
                    abstractTaskQueue.stop();
                }
                abstractTaskQueue = new AbstractTaskQueue(1);
                abstractTaskQueue.start();
            }
        });
        findViewById(R.id.button_6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abstractTaskQueue.stop();
            }
        });
        findViewById(R.id.button_7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abstractTaskQueue.start();
            }
        });
        findViewById(R.id.button_8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TestActivity.class));
            }
        });
        AtomicInteger mAtomicInteger = new AtomicInteger();
        mAtomicInteger.addAndGet(4);
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
