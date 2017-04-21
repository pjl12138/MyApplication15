package com.example.administrator.myapplication;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends Activity {
    private TextView tv;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv=(TextView)findViewById(R.id.tv);
        pb=(ProgressBar)findViewById(R.id.pb);
    }
    public void change(View view){
        String url="https://www.baidu.com";
        new NetWorkTask(tv,pb).execute();

    }
}
class NetWorkTask extends AsyncTask<String,Integer,String>{
    private TextView tv;
    private ProgressBar pb;
    public NetWorkTask(TextView tv,ProgressBar pb){
        this.tv=tv;
        this.pb=pb;
    }
    public NetWorkTask(){

    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pb.setProgress(0);
    }

    @Override
    protected String doInBackground(String... params) {
        try{
            URL url=new URL(params[0]);
            HttpsURLConnection connection=(HttpsURLConnection)url.openConnection();
            InputStream inputStream=connection.getInputStream();
            int total=connection.getContentLength();
            int downloadSize=0;
            byte[] bytes=new byte[1];
            int in;
            StringBuffer result=new StringBuffer();
            while((in=inputStream.read())!=-1){
                downloadSize=downloadSize+in;
             int prograss=(int)((float)downloadSize/total*100);
                publishProgress(prograss);
                result.append(prograss);
            }
            return result.toString();
        } catch (Exception e) {
            return "error";
        }

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        pb.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(s!=null){
            tv.setText(s);
        }
    }
}
