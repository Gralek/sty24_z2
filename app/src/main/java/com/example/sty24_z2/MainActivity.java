package com.example.sty24;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etOwnerName, etPowod;
    private ListView lvSpecies;
    private SeekBar seekBarAge;
    private TextView tvAgeLabel, tvResult;
    private TimePicker timePickerVisit;
    private Button btnSubmit;

    private String selectedSpecies = "Pies";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        etOwnerName = findViewById(R.id.etOwnerName);
        etPowod = findViewById(R.id.etVisitReason);
        lvSpecies = findViewById(R.id.lvSpecies);
        seekBarAge = findViewById(R.id.seekBarAge);
        tvAgeLabel = findViewById(R.id.tvAgeLabel);
        tvResult = findViewById(R.id.tvResult);
        timePickerVisit = findViewById(R.id.timePickerVisit);
        btnSubmit = findViewById(R.id.btnSubmit);


        timePickerVisit.setIs24HourView(true);


        String[] speciesList = {"Pies", "Kot", "Świnka morska"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, speciesList);
        lvSpecies.setAdapter(adapter);
        lvSpecies.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lvSpecies.setItemChecked(0, true);


        lvSpecies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedSpecies = speciesList[position];
                int maxAge = 20;

                switch (selectedSpecies) {
                    case "Pies":
                        maxAge = 18;
                        break;
                    case "Kot":
                        maxAge = 20;
                        break;
                    case "Świnka morska":
                        maxAge = 9;
                        break;
                }

                seekBarAge.setMax(maxAge);

                if (seekBarAge.getProgress() > maxAge) {
                    seekBarAge.setProgress(maxAge);
                    tvAgeLabel.setText("Ile ma lat? " + maxAge);
                }
            }
        });


        seekBarAge.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvAgeLabel.setText("Ile ma lat? " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String owner = etOwnerName.getText().toString().trim();
                int age = seekBarAge.getProgress();
                String reason = etPowod.getText().toString().trim();

                int hour = timePickerVisit.getHour();
                int minute = timePickerVisit.getMinute();
                String timeFormatted = String.format("%02d:%02d", hour, minute);


                String summary = owner + ", " + selectedSpecies + ", " + age + ", " + reason + ", " + timeFormatted;


                tvResult.setText(summary);


                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Zapisana Wizyta")
                        .setMessage(summary)
                        .setPositiveButton("OK", null)
                        .show();
            }
        });
    }
}