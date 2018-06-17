package com.example.t00578223.the_alarmclock;


//https://demonuts.com/android-listview-swipe-delete/
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;





//test
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Toast;
import com.hudomju.swipe.SwipeToDismissTouchListener;
import com.hudomju.swipe.adapter.ListViewAdapter;
import java.util.Arrays;

import static android.widget.Toast.LENGTH_SHORT;
//test



public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    String key = "shared";
    String [] AlarmSet;



    ArrayList<String> listItems;
    ArrayAdapter<String> adapter;

    ListView listView;

    //test
    private CustomAdapter customAdapter;
    private ListView lv;
    private ArrayList<Model> AlarmArray;

    String ALARMS = "";
    //test


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getApplicationContext().getSharedPreferences(key, MODE_PRIVATE);

        listItems=new ArrayList<>();

        lv =  findViewById(R.id.list);

        listView = findViewById(R.id.list);
        adapter=new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                listItems);
        listView.setAdapter(adapter);

        final String Alarm = sharedPreferences.getString(key,"");


        AlarmArray = new ArrayList<>();

        if(Alarm != null) {
            AlarmSet = Alarm.split(",");



            for(int x = 0; x < AlarmSet.length;x++){
                Model model = new Model();
                model.setName(AlarmSet[x]);

                AlarmArray.add(model);
            }
/*
            for(int i = 0; i < AlarmSet.length; i++){
                listItems.add(AlarmSet[i]);

            }
            adapter.notifyDataSetChanged();
*/

            //test
            customAdapter = new CustomAdapter(this,AlarmArray);
            lv.setAdapter(customAdapter);

            final SwipeToDismissTouchListener<ListViewAdapter> touchListener =
                    new SwipeToDismissTouchListener<>(
                            new ListViewAdapter(lv),
                            new SwipeToDismissTouchListener.DismissCallbacks<ListViewAdapter>() {
                                @Override
                                public boolean canDismiss(int position) {
                                    return true;
                                }

                                @Override
                                public void onDismiss(ListViewAdapter view, int position) {
                                    customAdapter.remove(position);

                                    AlarmSet = deleteArray(AlarmSet,position);








                                }
                            });

            lv.setOnTouchListener(touchListener);
            lv.setOnScrollListener((AbsListView.OnScrollListener) touchListener.makeScrollListener());
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (touchListener.existPendingDismisses()) {
                        touchListener.undoPendingDismiss();
                    } else {
                        Toast.makeText(MainActivity.this, "Position " + position, LENGTH_SHORT).show();
                    }
                }
            });


            //test



//            SharedPreferences.Editor edit = sharedPreferences.edit();
//            edit.putString(key,sb.toString());
//            edit.apply();

        }









    }



    public String[] deleteArray(String arr[], int i )
    {
        StringBuilder sb = new StringBuilder();

        String[] newArr = new String[arr.length-1];
        int j = 0;
        for(int x = 0; x<arr.length; x++){
            if(i != x){
                newArr[j] = arr[x];
                j++;
            }

        }

        for(int h = 0;h <newArr.length;h++){
            sb.append(newArr[h]).append(",");

        }

        ALARMS = sb.toString();
        SharedPreferences.Editor edit = sharedPreferences.edit();

        edit.putString(key,ALARMS);
        edit.apply();



        return newArr;


    }



    public void goToAdd(View view){
        startActivity(new Intent(MainActivity.this, add.class));
    }
}
