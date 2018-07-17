package application.mobileforms;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import io.netopen.hotbitmapgg.library.view.RingProgressBar;

/**
 * Created by Inas on 22-Feb-18.
 */

public class StartAnalysis extends AppCompatActivity {
    private RingProgressBar ringprogressBar1,ringprogressBar2;
    int progress=100;
    Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
           if(msg.what ==0)
           {
               if(progress>80){
                   progress--;
                   ringprogressBar1.setProgress(progress);
                   ringprogressBar2.setProgress(progress);

               }

           }
        }
    } ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_anaylsis);
        ringprogressBar2=(RingProgressBar)findViewById(R.id.color_progress_bar);
        ringprogressBar1=(RingProgressBar)findViewById(R.id.area_progress_bar);
        ringprogressBar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(StartAnalysis.this, "Completed !!!", Toast.LENGTH_SHORT).show();
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i =100;i>80;i--)
                {
                    try {
                        Thread.sleep(80);
                        myHandler.sendEmptyMessage(0);


                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }
}
