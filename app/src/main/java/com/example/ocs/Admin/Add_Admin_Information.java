package com.example.ocs.Admin;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.complientsystem.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import Models.Information_Model;

public class Add_Admin_Information extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Add_Admin_Information";
    EditText apply_date;
    EditText apply_title;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    private String inApplyDate;
    private String inProDate;
    private String inProTime;
    private String inTitle;
    Button info_send_Button;
    EditText pro_date;
    EditText pro_time;
    private TextWatcher textWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!Add_Admin_Information.this.pro_date.getText().toString().trim().isEmpty()) {
                Calendar c = Calendar.getInstance();
                int mHour = c.get(11);
                int mMinute = c.get(12);
                Add_Admin_Information.this.timePickerDialog = new TimePickerDialog(Add_Admin_Information.this, new TimePickerDialog.OnTimeSetListener() {
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Add_Admin_Information.this.pro_time.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
                Add_Admin_Information.this.timePickerDialog.show();
            }
        }

        public void afterTextChanged(Editable s) {
            if (!Add_Admin_Information.this.pro_time.getText().toString().trim().isEmpty()) {
                Add_Admin_Information.this.timePickerDialog.dismiss();
            }
        }
    };
    TimePickerDialog timePickerDialog;
    /* access modifiers changed from: private */
    public long timestamp;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity__admin__information);
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        this.apply_date = (EditText) findViewById(R.id.in_applyDate);
        this.apply_title = (EditText) findViewById(R.id.in_title);
        this.pro_date = (EditText) findViewById(R.id.in_subdate);
        this.pro_time = (EditText) findViewById(R.id.in_time);
        this.info_send_Button = (Button) findViewById(R.id.send_btn);
        this.apply_date.setOnClickListener(this);
        this.pro_date.setOnClickListener(this);
        this.pro_time.setOnClickListener(this);
        this.pro_date.addTextChangedListener(this.textWatcher);
        this.pro_time.addTextChangedListener(this.textWatcher);
        this.info_send_Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Add_Admin_Information.this.storeInformation();
            }
        });
    }

    public void onClick(View v) {
        if (v == this.apply_date) {
            Calendar c = Calendar.getInstance();
            new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    Add_Admin_Information.this.apply_date.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                }
            }, c.get(1), c.get(2), c.get(5)).show();
        }
        if (v == this.pro_date) {
            Calendar c2 = Calendar.getInstance();
            new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    Add_Admin_Information.this.pro_date.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
                    Log.d(Add_Admin_Information.TAG, "DAte:\t" + Add_Admin_Information.this.pro_date.getText().toString());
                    try {
                        Date date = sdf.parse(Add_Admin_Information.this.pro_date.getText().toString());
                        Log.d(Add_Admin_Information.TAG, "Time Millis:" + date.getTime());
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        Log.d(Add_Admin_Information.TAG, "tims:\t" + calendar.getTimeInMillis());
                        long unused = Add_Admin_Information.this.timestamp = calendar.getTimeInMillis();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }, c2.get(1), c2.get(2), c2.get(5)).show();
        }
        if (v == this.pro_time) {
            Calendar c3 = Calendar.getInstance();
            new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    Add_Admin_Information.this.pro_time.setText(hourOfDay + ":" + minute);
                }
            }, c3.get(11), c3.get(12), false).show();
        }
    }

    private boolean validateTitle() {
        String trim = this.apply_title.getText().toString().trim();
        this.inTitle = trim;
        if (trim.isEmpty()) {
            this.apply_title.setError("Please Enter Title..!");
            return false;
        }
        this.apply_title.setError((CharSequence) null);
        return true;
    }

    private boolean validateApplyDate() {
        String trim = this.apply_date.getText().toString().trim();
        this.inApplyDate = trim;
        if (trim.isEmpty()) {
            this.apply_date.setError("Please Enter Title..!");
            return false;
        }
        this.apply_date.setError((CharSequence) null);
        return true;
    }

    private boolean validateProDate() {
        String trim = this.pro_date.getText().toString().trim();
        this.inProDate = trim;
        if (trim.isEmpty()) {
            this.pro_date.setError("Please Enter Title..!");
            return false;
        }
        this.pro_date.setError((CharSequence) null);
        return true;
    }

    private boolean validateProTime() {
        String trim = this.pro_time.getText().toString().trim();
        this.inProTime = trim;
        if (trim.isEmpty()) {
            this.pro_time.setError("Please Enter Title..!");
            return false;
        }
        this.pro_time.setError((CharSequence) null);
        return true;
    }

    /* access modifiers changed from: private */
    public void storeInformation() {
        if (!((!validateTitle()) | (!validateApplyDate()) | (!validateProDate())) && !(!validateProTime())) {
            FirebaseFirestore.getInstance().collection("Admin Info").add(new Information_Model(this.inTitle, this.inApplyDate, this.inProDate, this.inProTime, this.timestamp)).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                public void onSuccess(DocumentReference documentReference) {
                    Log.d(Add_Admin_Information.TAG, "Successful stored..!");
                    Toast.makeText(Add_Admin_Information.this, "Information Send..!",Toast.LENGTH_SHORT).show();
                    Add_Admin_Information.this.finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                public void onFailure(Exception e) {
                    Log.d(Add_Admin_Information.TAG, "Store Info Error:\t" + e);
                    e.printStackTrace();
                }
            });
        }
    }
}
