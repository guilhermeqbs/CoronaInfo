package com.example.coronainfo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.coronainfo.domain.CountryTranslation;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class MainActivity extends AppCompatActivity {

    Optional<CountryTranslation> sc;
    Button buttonSend;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            setSpinner();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        buttonSend = findViewById(R.id.buttonSend);
        buttonSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(),DataActivity.class);
                myIntent.putExtra("english",sc.get().getEnglish());
                myIntent.putExtra("isoCode",sc.get().getIsoCode());
                myIntent.putExtra("portuguese",sc.get().getPortuguese());
                startActivity(myIntent);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setSpinner() throws IOException, JSONException {

        InputStream is = getResources().openRawResource(R.raw.data_country);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } finally {
            is.close();
        }

        String jsonString = writer.toString();

        ObjectMapper mapper = new ObjectMapper();

        List<CountryTranslation> countries = mapper.readValue(jsonString, new TypeReference<List<CountryTranslation>>(){});

        Spinner dropdown = findViewById(R.id.spinner);

        ArrayList<String> items = new ArrayList<String>();

        for (CountryTranslation countryTranslation : countries) {
            items.add(countryTranslation.getPortuguese());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);

        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                //resultado.setText((String)parentView.getItemAtPosition(position));

                Optional<CountryTranslation> selectedCountry = countries.stream().filter(c->c.getPortuguese().equals( (String)parentView.getItemAtPosition(position))).findFirst();

                 sc = selectedCountry;

                //mandar pra outra tela o selectedCountry
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

    }
}