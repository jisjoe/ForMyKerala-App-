package in.co.iodev.formykerala.Activities;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import in.co.iodev.formykerala.HTTPPostGet;
import in.co.iodev.formykerala.Models.DataModel;
import in.co.iodev.formykerala.R;


public class OTPVerification extends AppCompatActivity {
    EditText phone;
    Gson gson = new Gson();
    SharedPreferences sharedPref;


    String StringData,request_post_url="https://e7i3xdj8he.execute-api.ap-south-1.amazonaws.com/Dev/otp/generate-otp",TimeIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);
    }

    public void verify(View view) {
        phone=findViewById(R.id.phone);
        StringData=phone.getText().toString();
        DataModel d=new DataModel();
        d.setPhoneNumber(StringData);
        StringData=gson.toJson(d);
        Log.i("jisjoe",StringData);

        new HTTPAsyncTask2().execute(request_post_url);



    }

private class HTTPAsyncTask2 extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... urls) {
         String response;
        // params comes from the execute() call: params[0] is the url.
        try {
            try {
                response= HTTPPostGet.getJsonResponse(urls[0],StringData);
                Log.i("jisjoe",response.toString());
                return response;
            } catch (Exception e) {
                e.printStackTrace();
                return "Error!";
            }
        } catch (Exception e) {
            return "Unable to retrieve web page. URL may be invalid.";
        }
    }

    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {
        JSONArray response;
        JSONObject responseObject;
        try {
            response = new JSONArray(result);
            responseObject = response.getJSONObject(0);
            Toast.makeText(getApplicationContext(),responseObject.getString("Message"),Toast.LENGTH_LONG).show();
           SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("TimeIndex", responseObject.getString("TimeIndex"));
            editor.apply();

            finish();

        } catch (JSONException e) {
            e.printStackTrace();
        }    }


}}

