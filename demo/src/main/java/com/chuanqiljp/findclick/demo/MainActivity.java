package com.chuanqiljp.findclick.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chuanqiljp.findclick.BindView;
import com.chuanqiljp.findclick.FindClick;
import com.chuanqiljp.findclick.OnClick;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.Button)
    Button mButton;
    @BindView(R.id.TextView)
    TextView mTextView;
    @BindView(R.id.EditText)
    EditText mEditText;
    @BindView(R.id.CheckBox)
    CheckBox mCheckBox;
    @BindView(R.id.RadioButton1)
    RadioButton mRadioButton1;
    @BindView(R.id.RadioButton2)
    RadioButton mRadioButton2;
    @BindView(R.id.RadioGroup)
    RadioGroup mRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FindClick.bind(this);
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                log("beforeTextChanged: " + s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                log("afterTextChanged: " + mEditText.getText());
            }
        });
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                log("onCheckedChanged: " + isChecked);
            }
        });

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = group.findViewById(checkedId);
                log("onCheckedChanged: " + radioButton.getText() + "," + radioButton.isChecked());
            }
        });

    }

    @OnClick({R.id.Button, R.id.TextView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Button:
                log("onViewClicked: Button");
                break;
            case R.id.TextView:
                log("onViewClicked: TextView");
                break;
        }
    }


    private void log(String msg) {
        Log.e(TAG, msg);
    }
}
