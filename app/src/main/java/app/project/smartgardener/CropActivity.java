package app.project.smartgardener;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;

public class CropActivity extends AppCompatActivity {

    Spinner crop;
    Button save;
    ArrayList<String> cropList = new ArrayList<String>(Arrays.asList("Select an Option","Potato","Onion","Tomato","Cauliflower"));
    String CropType;
    DatabaseReference db;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Crop Details");

        crop = findViewById(R.id.CropSpinner);

        save = findViewById(R.id.SaveBtn);
        progressBar = findViewById(R.id.progressBar);

        crop.setAdapter(new ArrayAdapter<String>(CropActivity.this, android.R.layout.simple_spinner_dropdown_item, cropList));

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(crop.getSelectedItem().equals("Select an Option")) {
                    new AlertDialog.Builder(CropActivity.this)
                            .setTitle("Oops!")
                            .setMessage("Crop name is required")
                            .setNegativeButton("OK", null)
                            .setIcon(R.drawable.ic_alert)
                            .show();
                }else{
                    progressBar.setVisibility(View.VISIBLE);
                    CropType = crop.getSelectedItem().toString();
                    db = FirebaseDatabase.getInstance().getReference().child("Kc");

                    if (CropType.equals("Potato")){
                        startActivity(new Intent(CropActivity.this,potato.class));

                    }else if (CropType.equals("Onion")){
                        startActivity(new Intent(CropActivity.this,onion.class));
                    }else if (CropType.equals("Tomato")){
                        startActivity(new Intent(CropActivity.this,tomato.class));
                    }else if (CropType.equals("Cauliflower")){
                        startActivity(new Intent(CropActivity.this,cauliflower.class));
                    }

                    progressBar.setVisibility(View.GONE);
                    new AlertDialog.Builder(CropActivity.this)
                            .setTitle("Loading")
                            .setMessage("Crop details loaded successfully.")
                            .setNegativeButton("OK", null)
                            .show();

                }
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id) {
            // back button
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
