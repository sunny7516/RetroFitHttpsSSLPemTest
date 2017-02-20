package com.example.tacademy.retrofithttpssslpemtest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 누르면 회원가입
                onJoin();
            }
        });
    }

    // 회원가입
    public void onJoin() {
        Call<ResJoin> res = NetSSL.getInstance().getMemberImpFactory().join(new ReqJoin("sunny", "test", "sunny@test.com", "testtest"));
        res.enqueue(new Callback<ResJoin>() {
                        @Override
                        public void onResponse(Call<ResJoin> call, Response<ResJoin> response) {
                            if (response.body().getResult() != null) {
                                Log.i("RF", "가입 성공" + response.body().getResult().getMessage());
                            } else {
                                Log.i("RF", "가입 실패:" + response.body().getError().getMessage());
                            }
                        }

                        @Override
                        public void onFailure(Call<ResJoin> call, Throwable t) {
                            Log.i("RF", "ERR" + t.getMessage());
                        }
                    }

        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
