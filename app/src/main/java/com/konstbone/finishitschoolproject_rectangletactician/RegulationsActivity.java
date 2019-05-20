package com.konstbone.finishitschoolproject_rectangletactician;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class RegulationsActivity extends AppCompatActivity {

    ListView regulations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regulations);

        regulations = findViewById(R.id.regulationsListView);

        String[] arrReg = new String[]{getResources().getString(R.string.regulations_text)};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrReg);
        regulations.setAdapter(adapter);
    }
}
