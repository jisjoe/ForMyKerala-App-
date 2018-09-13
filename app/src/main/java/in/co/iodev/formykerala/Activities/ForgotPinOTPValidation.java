package in.co.iodev.formykerala.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import in.co.iodev.formykerala.Controllers.CheckInternet;
import in.co.iodev.formykerala.Controllers.HTTPPostGet;
import in.co.iodev.formykerala.Controllers.ProgressBarHider;
import in.co.iodev.formykerala.Models.DataModel;
import in.co.iodev.formykerala.Controllers.OTPTextEditor;
import in.co.iodev.formykerala.R;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static in.co.iodev.formykerala.Constants.Constants.Resend_OTP;
import static in.co.iodev.formykerala.Constants.Constants.Verify_OTP;

public class ForgotPinOTPValidation extends AppCompatActivity {
    SharedPreferences sharedPref;
    EditText otp1,otp2,otp3,otp4;
    Button verify,resend_otp;
    CardView otp_resend;
    Gson gson = new Gson();
    ImageView back;
    int minutes=2,seconds=00;

    Context context;
    ProgressBarHider hider;
    long delay=120000;
    String StringData,request_post_url=Verify_OTP,TimeIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.setAppLocale(MainActivity.languagePreferences.getString("LOCALE_CODE", null), getResources());
        setContentView(R.layout.activity_otpvalidation);
        sharedPref=getDefaultSharedPreferences(getApplicationContext());
        otp1=findViewById(R.id.otp1);
        otp2=findViewById(R.id.otp2);
        otp3=findViewById(R.id.otp3);
        otp4=findViewById(R.id.otp4);
        final String localeCode=MainActivity.languagePreferences.getString("LOCALE_CODE", null);
        final ImageView voice=findViewById(R.id.voice);
        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer mp = new MediaPlayer();
                voice.setClickable(false);
                try {
                    if(localeCode.equals("ml"))
                    { mp=MediaPlayer.create(getApplicationContext(),R.raw.otpvalidation_mal);
                        mp.start();}
                    else if (localeCode.equals("en"))
                    {
                        mp=MediaPlayer.create(getApplicationContext(),R.raw.otpvalidation_eng);
                        mp.start();
                    }
                    mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            voice.setClickable(true);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        verify=findViewById(R.id.otp_verify);
        otp_resend=findViewById(R.id.resend_otp);
        TextView phone=findViewById(R.id.phone);
        phone.setText("to "+sharedPref.getString("PhoneNumber",""));
        hider=new ProgressBarHider(verify.getRootView(),verify);
        timer();

        new Timer().schedule(new resendotp(),delay);
        context=this;
        otp1.addTextChangedListener(new OTPTextEditor(otp1,otp1.getRootView()));
        otp2.addTextChangedListener(new OTPTextEditor(otp2,otp2.getRootView()));
        otp3.addTextChangedListener(new OTPTextEditor(otp3,otp3.getRootView()));
        otp4.addTextChangedListener(new OTPTextEditor(otp4,otp4.getRootView()));
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verify();
            }
        });
        TimeIndex=sharedPref.getString("TimeIndex","");
        back=findViewById(R.id.back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        resend_otp=findViewById(R.id.otp_resend);
        resend_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataModel d=new DataModel();
                d.setTimeIndex(TimeIndex);
                d.setPhoneNumber(sharedPref.getString("PhoneNumber",""));
                StringData=gson.toJson(d);
                new HTTPAsyncTask3().execute(Resend_OTP);
            }
        });


    }
public void timer()
{    final TextView textTimer=findViewById(R.id.timer);
    new CountDownTimer(120000, 1000
    ) {

        public void onTick(long millisUntilFinished) {
            textTimer.setText("Resend OTP in "+minutes+":"+checkDigit(seconds));

            if(seconds==0)
            {
                seconds=59;
                minutes--;
            }
            else
                seconds--;
        }

        public void onFinish() {
            minutes=2;
            seconds=0;
            textTimer.setText("try again");
        }

    }.start();




}
    public String checkDigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);}

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ForgotPinOTPValidation.this,ForgotPin.class));
        ForgotPinOTPValidation.this.finish();
        super.onBackPressed();
    }
    public void verify() {
        if(otp1.getText().toString().equals("")||otp2.getText().toString().equals("")||otp3.getText().toString().equals("")||otp4.getText().toString().equals("")){
            String toastText = getString(R.string.toast_valid_otp);
            Toast.makeText(ForgotPinOTPValidation.this, toastText,Toast.LENGTH_LONG).show();
        }
        else {
            hider.show();
            StringData = otp1.getText().toString() + otp2.getText().toString() + otp3.getText().toString() + otp4.getText().toString();

            DataModel d = new DataModel();
            d.setOTP(StringData);
            d.setTimeIndex(TimeIndex);
            StringData = gson.toJson(d);
            Log.i("jisjoe", StringData);

            new HTTPAsyncTask2().execute(request_post_url);
        }
    }

    private class resendotp extends TimerTask
    {

        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    otp_resend.setVisibility(View.VISIBLE);

                }
            });
        }
    }
    private class HTTPAsyncTask2 extends AsyncTask<String, Void, String> {
        String response="Network Error";

        @Override
        protected String doInBackground(String... urls) {
            // params comes from the execute() call: params[0] is the url.
            try {
                try {
                    response= HTTPPostGet.getJsonResponse(urls[0],StringData);
                    Log.i("jisjoe",response.toString());
                    return response;
                } catch (Exception e) {
                    e.printStackTrace();
                    return "Error!";
                }finally {
                    hider.hide();
                }
            } catch (Exception e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        @Override
        protected void onPreExecute() {
            CheckInternet CI=new CheckInternet();
            CI.isOnline(context);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            hider.hide();
            JSONObject responseObject= null;
            try {
                responseObject = new JSONObject(response);
                Toast.makeText(getApplicationContext(),responseObject.getString("Message"),Toast.LENGTH_LONG).show();
                if(responseObject.getString("Message").equals("Success"))
                {
                           startActivity(new Intent(ForgotPinOTPValidation.this,PinReset.class));
                }
                else {
                    String toastText = getString(R.string.wrong_otp);
                    Toast.makeText(getApplicationContext(), toastText,Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }    }


    }
    private class HTTPAsyncTask3 extends AsyncTask<String, Void, String> {
        String response="Network Error";
        @Override
        protected String doInBackground(String... urls) {

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
        @Override
        protected void onPreExecute() {
            CheckInternet CI=new CheckInternet();
            otp_resend.setVisibility(View.INVISIBLE);
            CI.isOnline(context);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            minutes=2;
            seconds=0;
            otp_resend.setVisibility(View.INVISIBLE);
            timer();

            new Timer().schedule(new resendotp(),delay);
            JSONObject responseObject= null;
            try {
                responseObject = new JSONObject(response);
                Toast.makeText(getApplicationContext(),responseObject.getString("Message"),Toast.LENGTH_LONG).show();


            } catch (JSONException e) {
                e.printStackTrace();
            }    }


    }}


