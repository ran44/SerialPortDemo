package vendingcabinets.dlc.cn.vendingcabinets;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import cn.dlc.fbrtest.R;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import vendingcabinets.dlc.cn.vendingcabinets.base.serialport.blockingqueue.AbstractTaskQueue;
import vendingcabinets.dlc.cn.vendingcabinets.base.serialport.blockingqueue.Priority;

public class TaskQueueActivity extends AppCompatActivity {
    private AbstractTaskQueue abstractTaskQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_queue);
        Logger.addLogAdapter(new AndroidLogAdapter());
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
        
        
    }

   
}
