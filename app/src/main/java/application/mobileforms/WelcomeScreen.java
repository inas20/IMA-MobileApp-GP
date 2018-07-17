package application.mobileforms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Inas on 13-Feb-18.
 */

public class WelcomeScreen extends AppCompatActivity {
    private TextView nextSlide;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        nextSlide=(TextView)findViewById(R.id.Next);
        nextSlide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup = new Intent(WelcomeScreen.this,SignupActivity.class);
                startActivity(signup);
            }
        });

    }
}
