package cn.foxnickel.sunshine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.ShareActionProvider;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private ShareActionProvider mProvider;
    private String forecastStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        TextView detailForecast = (TextView) findViewById(R.id.detail_forecast);
        Intent forecastIntent = getIntent();
        forecastStr = forecastIntent.getStringExtra(Intent.EXTRA_TEXT);
        /*判断Intent里有数据的时候再更新界面*/
        if (forecastIntent != null && forecastIntent.hasExtra(Intent.EXTRA_TEXT)) {
            detailForecast.setText(forecastStr);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail, menu);
        // 用ShareActionProvider定位MenuItem
        MenuItem item = menu.findItem(R.id.action_share);
        // 获取并存储ShareActionProvider
        mProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        setShareIntent();
        return true;
    }

    private void setShareIntent() {
        if (mProvider != null) {
            mProvider.setShareIntent(createShareForecastIntent());
        }
    }

    private Intent createShareForecastIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT,
                forecastStr + "#SunshineApp");
        return shareIntent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
