package com.uh4t.imotocareapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Range;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.uh4t.imotocareapp.Adapters.PersonelAdapter;
import com.uh4t.imotocareapp.Api.PersonelService;
import com.uh4t.imotocareapp.Models.Personell;
import com.uh4t.imotocareapp.SQLite.PersonelDbContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Intent _intent;
    private EditText _rangeIndicator;
    private Button _increaseRangeBtn, _decreaseRange;
    private Spinner _cityListSpinner;
    private ListView _peopleList;
    private String _rangeText;
    ProgressDialog p;

    private PersonelAdapter _personelAdapter;
    ArrayList<Personell> _people;
    private String typeEndPoint = "people/type/";

    private PersonelDbContext _conext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _people = new ArrayList<>();

        _intent = getIntent();
        String _type = _intent.getStringExtra("type");
        _rangeIndicator = (EditText)findViewById(R.id.RangeIndicator);
        _increaseRangeBtn = (Button) findViewById(R.id.IncreaseRangeBtn);
        _decreaseRange = (Button) findViewById(R.id.ReduceRangeBtn);
        _cityListSpinner = (Spinner)findViewById(R.id.CityListSpinner);
        _peopleList = (ListView)findViewById(R.id.PersonellList);
        _peopleList.setDivider(null);
        _rangeIndicator.setText("0");

        _increaseRangeBtn.setOnClickListener(this);
        _decreaseRange.setOnClickListener(this);

        _personelAdapter = new PersonelAdapter(MainActivity.this, _people);

        Log.d("===================", _type);


        AsyncTaskExample asyncTask=new AsyncTaskExample();


        _conext = new PersonelDbContext(this);

        //for (Personell _person: _conext.getPeople("54.1","0.34")) {
        //    _people.add(_person);
        //}

        //_peopleList.setAdapter(_personelAdapter);

        asyncTask.execute(typeEndPoint + _type);


        Toast.makeText(this, _type, Toast.LENGTH_LONG ).show();

    }
    @Override
    public void onClick(View view){

        _rangeText = _rangeIndicator.getText().toString();
        String newText = "";
        switch (view.getId()){
            case R.id.ReduceRangeBtn:
                newText = ReduceRaange(_rangeText);
                break;
            case R.id.IncreaseRangeBtn:
                newText = IncreaseRaange(_rangeText);
                break;
        }
        _rangeIndicator.setText(newText);
    }
    private String IncreaseRaange(String range){
        Integer _range = Integer.parseInt(range);
        if (_range < 1000)
        {
            _range += 10;
        }

        return _range.toString();
    }
    private String ReduceRaange(String range){
        Integer _range = Integer.parseInt(range);
        if (_range > 0)
        {
            _range -= 10;
        }

        return _range.toString();

    }

    private class AsyncTaskExample extends AsyncTask<String, String, PersonelAdapter> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p = new ProgressDialog(MainActivity.this);
            p.setMessage("Please wait...It is downloading");
            p.setIndeterminate(false);
            p.setCancelable(false);
            p.show();
        }
        @Override
        protected PersonelAdapter doInBackground(String... strings) {
            try {
                List<Header> headers = new ArrayList<>();
                headers.add(new BasicHeader("Accept", "application/json"));
                Log.d("========== url", strings[0]);
                PersonelService.get(strings[0],  new JsonHttpResponseHandler(){
                    @Override
                    public void onStart() {
                        // Initiated the request
                    }
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        ArrayList<Personell> noteArray = new ArrayList<Personell>();
                        _personelAdapter = new PersonelAdapter(MainActivity.this, noteArray);
                        Log.d("Response Object", response.toString());
                        try {
                            JSONArray arr = response.getJSONArray("data");
                            for (int i = 0; i < arr.length(); i++) {
                                try {
                                    JSONObject obj = arr.getJSONObject(i);
                                    Log.i("==== newObject ++++", obj.toString());
                                    _personelAdapter.add(new Personell(obj));
                                    PersonelDbContext db = new PersonelDbContext(MainActivity.this);
                                    //db.Insert(new Personell(obj));


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        catch (JSONException jsonEx){
                            jsonEx.printStackTrace();
                        }
                    }
                    @Override
                    public void onRetry(int retryNo) {
                        // Request was retried
                    }

                    @Override
                    public void onFinish() {
                        // Completed the request (either success or failure)
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("++++++++error", e.getMessage());
            }
            return _personelAdapter;
        }
        @Override
        protected void onPostExecute(PersonelAdapter _personelAdapter) {
            super.onPostExecute(_personelAdapter);
            if(_personelAdapter!= null) {
                p.hide();
                _peopleList = findViewById(R.id.PersonellList);
                _peopleList.setAdapter(_personelAdapter);
            }else {
                p.show();
            }
        }
    }
}
