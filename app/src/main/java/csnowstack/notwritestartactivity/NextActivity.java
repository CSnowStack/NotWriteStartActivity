package csnowstack.notwritestartactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import csnowstack.lib.StartActivityAnnotation;

/**
 *
 */

@StartActivityAnnotation(
        flag = Intent.FLAG_ACTIVITY_NEW_TASK,
        keys = "String,"+Constants.BUNDLE_TITLE
)
public class NextActivity extends AppCompatActivity{

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        ((TextView) findViewById(R.id.txt)).setText(getIntent().getStringExtra(Constants.BUNDLE_TITLE));
    }
}
