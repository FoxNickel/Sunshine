package cn.foxnickel.sunshine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        TextView detailForecast = (TextView) findViewById(R.id.detail_forecast);
        Intent forecast = getIntent();
        detailForecast.setText(forecast.getStringExtra(Intent.EXTRA_TEXT));
    }
}
