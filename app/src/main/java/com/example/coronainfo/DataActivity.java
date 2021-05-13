package com.example.coronainfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.view.View;
import android.view.textclassifier.ConversationActions;
import android.widget.Button;
import android.widget.TextView;

import com.example.coronainfo.domain.CountryTranslation;
import com.example.coronainfo.domain.CovidResult;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DataActivity extends AppCompatActivity {

    private TextView country;
    private TextView rank;
    private TextView population;
    private TextView totalCases;
    private TextView totalDeaths;
    private TextView totalRecovered;
    private TextView infectionRisk;
    Button buttonBack;
    private CovidResult countryResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        country = findViewById(R.id.textViewCountry);
        rank = findViewById(R.id.textViewResultRank);
        population = findViewById(R.id.textViewResultPopulacao);
        totalCases = findViewById(R.id.textViewResultTotalCasos);
        totalRecovered = findViewById(R.id.textViewResultRecuperados);
        totalDeaths = findViewById(R.id.textViewResultTotalMortes);
        infectionRisk = findViewById(R.id.textViewResultRiscoInfec);

        try {
            aplicApi();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        buttonBack = findViewById(R.id.buttonVoltar);
        buttonBack.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                finish();
            }
        });

    }


    public void aplicApi() throws IOException, ExecutionException, InterruptedException {

        //selectedCounty.getIsoCode
        //selectedCounty.getEng


        // getIntent() is a method from the started activity
        Intent myIntent = getIntent(); // gets the previously created intent
        String english = myIntent.getStringExtra("english"); // will return "FirstKeyValue"
        String isoCode= myIntent.getStringExtra("isoCode"); // will return "SecondKeyValue"
        String portuguese = myIntent.getStringExtra("portuguese"); // will return "SecondKeyValue"

        country.setText(portuguese);


        Request request = new Request.Builder()
                .url("https://vaccovid-coronavirus-vaccine-and-treatment-tracker.p.rapidapi.com/api/npm-covid-data/country-report-iso-based/"+english+"/"+isoCode)
                .get()
                .addHeader("x-rapidapi-key", "5c0bc4d5bdmsh77a9159de1fd771p1d6c10jsndab72c1c703b")
                .addHeader("x-rapidapi-host", "vaccovid-coronavirus-vaccine-and-treatment-tracker.p.rapidapi.com")
                .build();

        ExecutorService threadpool = Executors.newCachedThreadPool();
        Future futureTask = threadpool.submit(() -> {
            try {
                executaApi(request);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        while (!futureTask.isDone()) {
            System.out.println("FutureTask is not finished yet...");
        }

        rank.setText(countryResult.getRank());
        population.setText(countryResult.getPopulation());
        totalCases.setText(countryResult.getTotalCases());
        totalRecovered.setText(countryResult.getTotalRecovered());
        totalDeaths.setText(countryResult.getTotalDeaths());
        infectionRisk.setText(countryResult.getInfection_Risk());

        threadpool.shutdown();

    }

    private void executaApi(Request request) throws IOException {

        OkHttpClient client = new OkHttpClient();

        Response response =  client.newCall(request).execute();

        String res = response.body().string();

        ObjectMapper mapper = new ObjectMapper();

        List<CovidResult> covidResults = mapper.readValue(res, new TypeReference<List<CovidResult>>(){});

        countryResult = covidResults.get(0);
    }

}