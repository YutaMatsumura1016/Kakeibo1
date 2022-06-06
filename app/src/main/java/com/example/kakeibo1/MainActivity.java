package com.example.kakeibo1;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {
    public ArrayList monthArray;
    public ArrayList dayArray;
    public ArrayList contentArray;
    public ArrayList paymentArray;
    private InputMethodManager inputMethodManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        monthArray = new ArrayList<>();
        dayArray = new ArrayList<>();
        contentArray = new ArrayList<>();
        paymentArray = new ArrayList<>();

        readCSV();

        ListView monthList = (ListView) findViewById(R.id.monthList);
        ListView dayList = (ListView) findViewById(R.id.dayList);
        ListView contentList = (ListView) findViewById(R.id.contentList);
        ListView paymentList = (ListView) findViewById(R.id.paymentList);
        EditText monthIn = (EditText) findViewById(R.id.monthIn);
        EditText dayIn = (EditText) findViewById(R.id.dayIn);
        EditText contentIn = (EditText) findViewById(R.id.contentIn);
        EditText paymentIn = (EditText) findViewById(R.id.paymentIn);

        ArrayAdapter monthArrayAdapter = new ArrayAdapter(this, R.layout.list, monthArray);
        ArrayAdapter dayArrayAdapter = new ArrayAdapter(this, R.layout.list, dayArray);
        ArrayAdapter contentArrayAdapter = new ArrayAdapter(this, R.layout.list, contentArray);
        ArrayAdapter paymentArrayAdapter = new ArrayAdapter(this, R.layout.list, paymentArray);

        monthList.setAdapter(monthArrayAdapter);
        dayList.setAdapter(dayArrayAdapter);
        contentList.setAdapter(contentArrayAdapter);
        paymentList.setAdapter(paymentArrayAdapter);

        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);


        //入力
        monthIn.setOnKeyListener(new View.OnKeyListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String monthText = monthIn.getText().toString();
                    String dayText = dayIn.getText().toString();
                    String contentText = contentIn.getText().toString();
                    String paymentText = paymentIn.getText().toString();

                    if (monthText.length() != 0 && dayText.length() != 0 && contentText.length() != 0 && paymentText.length() != 0) {
                        try {
                            InputStream inputStream = getResources().getAssets().open("test1.csv");
                            convertToCSV(monthText);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    return true;
                }
                return false;
            }
        });//入力1終わり
    }


    //csvの読み込み
    public void readCSV() {
        try {
            InputStream inputStream =
                    getResources().getAssets().open("test1.csv");

            InputStreamReader inputStreamReader =
                    new InputStreamReader(inputStream);

            BufferedReader bufferReader =
                    new BufferedReader(inputStreamReader);

            String line = "";

            while ((line = bufferReader.readLine()) != null) {
                StringTokenizer stringTokenizer =
                        new StringTokenizer(line, ",");

                monthArray.add(stringTokenizer.nextToken());
                dayArray.add(stringTokenizer.nextToken());
                contentArray.add(stringTokenizer.nextToken());
                paymentArray.add(stringTokenizer.nextToken());
            }
            bufferReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //csvに書き込み
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void convertToCSV(String monthText) {

        try {
            //FileWriterインスタンスの生成
            FileWriter csvWriter = new FileWriter("test1.csv");

            //バッファに追加（行の各項目をカンマ区切りで連結）
            csvWriter.append(String.join(monthText));
            //バッファに追加（改行）
            csvWriter.append("\n");

            //ファイルへの書き込み
            csvWriter.flush();
            //ストリームを閉じる
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
