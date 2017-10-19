package rc.loveq.rulerview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buttonOne(View view) {
        Intent intent = new Intent(this, HorizontalActivity.class);
        startActivity(intent);
    }

    public void buttonSecond(View view) {
        Intent intent = new Intent(this, VerticalActivity.class);
        startActivity(intent);
    }
}
