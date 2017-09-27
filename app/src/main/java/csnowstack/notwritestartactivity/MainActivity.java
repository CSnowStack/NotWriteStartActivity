package csnowstack.notwritestartactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.txt).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
             csnowstack.notwritestartactivity.NextActivity_Start.start(MainActivity.this,
                        "我不想写StartActivityForResult");

            }
        });

    }
}
