package com.example.whatsappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

public class PrimaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primary);

        final ListView listView = findViewById(R.id.listview);
        final ArrayList<String> waUsers = new ArrayList<>();
        final ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, waUsers);
        final SwipeRefreshLayout myswiperefreshlayout = findViewById(R.id.swiperefresh);


        try{
            ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
            parseQuery.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
            parseQuery.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> objects, ParseException e) {
                    if(e == null && objects.size() > 0){

                        for(ParseUser user : objects){

                            waUsers.add(user.getUsername());

                        }
                        listView.setAdapter(adapter);
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

        myswiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                try{

                    ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
                    parseQuery.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
                    parseQuery.whereNotContainedIn("username", waUsers);
                    parseQuery.findInBackground(new FindCallback<ParseUser>() {
                        @Override
                        public void done(List<ParseUser> objects, ParseException e) {

                            if(objects.size() > 0){

                                if(e==null){

                                    for(ParseUser user : objects){

                                        waUsers.add(user.getUsername());

                                    }
                                    adapter.notifyDataSetChanged();
                                    if(myswiperefreshlayout.isRefreshing()){
                                        myswiperefreshlayout.setRefreshing(false);
                                    }
                                }else{
                                    if(myswiperefreshlayout.isRefreshing()){
                                        myswiperefreshlayout.setRefreshing(false);
                                    }
                                }
                            }
                        }
                    });
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.itmlogout:


                ParseUser.getCurrentUser().logOutInBackground(new LogOutCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e == null){

                            FancyToast.makeText(PrimaryActivity.this,"You have successfully logged out ",
                                    FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false);

                            Intent intent = new Intent(PrimaryActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        }
                        else{

                            FancyToast.makeText(PrimaryActivity.this,"An error occured "
                                            + "\n" + e.getMessage(),
                                    FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false);


                        }
                    }
                });

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
