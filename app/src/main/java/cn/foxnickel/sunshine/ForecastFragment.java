package cn.foxnickel.sunshine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class ForecastFragment extends Fragment {
    public ForecastFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forecastfragement, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            FetchWeatherTask weatherTask = new FetchWeatherTask();
            weatherTask.execute("94043");//传递参数到FetchWeatherTask
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*假数据（数组）用来填充listview*/
        String[] data = {
                "Mon 6/23 - Sunny - 31/17",
                "Tue 6/24 - Foggy - 21/8",
                "Wed 6/25 - Cloudy - 22/17",
                "Thurs 6/26 - Rainy - 18/11",
                "Fri 6/27 - Foggy - 21/10",
                "Sat 6/28 - TRAPPED IN WEATHERSTATION - 23/18",
                "Sun 6/29 - Sunny - 20/7"
        };
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);//构建界面
        ArrayList<String> weekForecast = new ArrayList<String>(Arrays.asList(data));//传递给adapter的list
        /*listview的adapter*/
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(),//Context
                R.layout.list_item_forecast,//listview加载的布局文件
                R.id.list_item_forecast_textview,//数据要加载到的控件id
                weekForecast);//要加载的数据项
        ListView weatherList = (ListView) rootView.findViewById(R.id.listview_forecast);
        weatherList.setAdapter(adapter);
        return rootView;
    }
}
