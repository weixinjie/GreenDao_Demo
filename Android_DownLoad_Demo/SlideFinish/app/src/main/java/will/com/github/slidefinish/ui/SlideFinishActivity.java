package will.com.github.slidefinish.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import will.com.github.slidefinish.R;
import will.com.github.slidefinish.layout.SlidingFinishLayout;

public class SlideFinishActivity extends AppCompatActivity implements SlidingFinishLayout.OnActivityFinishListener {

    SlidingFinishLayout sf_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_finish);

        sf_test = (SlidingFinishLayout) findViewById(R.id.sf_test);
        sf_test.setOnActivityFinishListener(this);
        sf_test.attachActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sf_test.detachActivity();
    }

    @Override
    public void onActivityFinish() {
        finish();
    }
}
