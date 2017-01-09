package will.com.github.slidefinish.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import will.com.github.slidefinish.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button bt_open;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_open = (Button) findViewById(R.id.bt_open);

        bt_open.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(this, SlideFinishActivity.class));
    }
}
