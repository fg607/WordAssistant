package com.hardwork.fg607.wordassistant.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.hardwork.fg607.wordassistant.R;
import com.hardwork.fg607.wordassistant.model.Config;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TestStyleActivity extends AppCompatActivity implements View.OnClickListener{

    @Bind(R.id.choose) Button mButtonChoose;
    @Bind(R.id.spell) Button mButtonSpell;

    private String mPlanName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_style);

        ButterKnife.bind(this);
        mButtonChoose.setOnClickListener(this);
        mButtonSpell.setOnClickListener(this);

        setTitle("题型选择");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();

        if(intent != null){

            mPlanName = intent.getStringExtra("planName");
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.choose:
                Intent intent = new Intent(this,ReciteActivity.class);
                intent.putExtra("planName",mPlanName);
                intent.putExtra("whichFragment",Config.TEST_FRAGMENT);
                intent.putExtra("style", Config.STYLE_CHOOSE);
                startActivity(intent);
                finish();
                break;
            case R.id.spell:
                Intent intent1 = new Intent(this,ReciteActivity.class);
                intent1.putExtra("whichFragment",Config.TEST_FRAGMENT);
                intent1.putExtra("planName",mPlanName);
                intent1.putExtra("style", Config.STYLE_SPELL);
                startActivity(intent1);
                finish();
                break;
            default:
                break;
        }
    }

}
