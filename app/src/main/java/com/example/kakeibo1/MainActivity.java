package com.example.kakeibo1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
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

        ArrayAdapter monthArrayAdapter =
                new ArrayAdapter(
                        this, R.layout.list, monthArray);
        ArrayAdapter dayArrayAdapter =
                new ArrayAdapter(
                        this, R.layout.list, dayArray);
        ArrayAdapter contentArrayAdapter =
                new ArrayAdapter(
                        this, R.layout.list, contentArray);
        ArrayAdapter paymentArrayAdapter =
                new ArrayAdapter(
                        this, R.layout.list, paymentArray);

        monthList.setAdapter(monthArrayAdapter);
        dayList.setAdapter(dayArrayAdapter);
        contentList.setAdapter(contentArrayAdapter);
        paymentList.setAdapter(paymentArrayAdapter);
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
}
