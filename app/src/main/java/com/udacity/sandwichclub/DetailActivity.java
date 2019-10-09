package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONObject;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView origin_tv,detail_also_known_as_label,detail_place_of_origin_label,description_tv,ingredients_tv,also_known_tv;
//    String mainName,alsoKnownAs,placeOfOrigin,description,
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }
            origin_tv=(TextView) findViewById(R.id.origin_tv);
        detail_also_known_as_label=(TextView) findViewById(R.id.also_known_tv);
        detail_place_of_origin_label=(TextView) findViewById(R.id.origin_tv);
        description_tv=(TextView) findViewById(R.id.description_tv);
        ingredients_tv=(TextView) findViewById(R.id.ingredients_tv);
        also_known_tv=(TextView) findViewById(R.id.also_known_tv);

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);
//set title of the activity
        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        origin_tv.setText(sandwich.getPlaceOfOrigin());
        List<String> alsoArray=sandwich.getAlsoKnownAs();
        String alsoString="";
        for(int i=0;i<alsoArray.size();i++){
            alsoString+= alsoArray.get(i);
        }
        detail_also_known_as_label.setText(alsoString);

        List<String> ingreds=sandwich.getIngredients();
        String ingredString="";
        for(int i=0;i<ingreds.size();i++){
            alsoString+= ingreds.get(i);
        }
        ingredients_tv.setText(ingredString);
        detail_place_of_origin_label.setText(sandwich.getPlaceOfOrigin());
        description_tv.setText(sandwich.getDescription());
        also_known_tv.setText(alsoString);
    }
}
