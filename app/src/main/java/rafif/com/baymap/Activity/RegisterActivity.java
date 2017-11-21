package rafif.com.baymap.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import rafif.com.baymap.Controller.AppConfig;
import rafif.com.baymap.Controller.AppController;
import rafif.com.baymap.R;

public class RegisterActivity extends AppCompatActivity {
    EditText _email,_password,_konfirmasipassword,_nama,_username;
    Button btnRegister;
    String email, password, konfirmasipassword, nama, username;
    ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        _email = (EditText) findViewById(R.id.edtText_Register_email);
        _nama = (EditText)findViewById(R.id.edtText_Register_name);
        _username = (EditText) findViewById(R.id.edtText_Register_username);
        _password = (EditText) findViewById(R.id.edtText_Register_password);
        _konfirmasipassword = (EditText) findViewById(R.id.edtText_Register_konfimasiPassword);
        btnRegister = (Button) findViewById(R.id.btn_Register);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = _email.getText().toString().trim();
                nama = _nama.getText().toString().trim();
                username = _username.getText().toString().trim();
                password = _password.getText().toString().trim();
                konfirmasipassword = _konfirmasipassword.getText().toString().trim();
                if (!nama.isEmpty() && !username.isEmpty() && !password.isEmpty() && !email.isEmpty())
                {
                    if (password.equals(konfirmasipassword)){
                        doRegister();
                        pDialog.dismiss();
                        Intent _LoginIntent = new Intent(RegisterActivity.this,LoginActivity.class);
                        startActivity(_LoginIntent);
                        finish();
                    }
                    else {
                        Toast.makeText(RegisterActivity.this,"password dan konfirmasi password tidak sama",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Data harus disi semua",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void doRegister(){
        pDialog = new ProgressDialog(this, R.style.Theme_AppCompat_DayNight_Dialog);
        pDialog.setMessage("Registering..");
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_REGISTER,
        new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            //if no error in response
                            if (obj.getInt("code") == 1) {
                                Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_LONG).show();

                            } else {
                                    Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username",username);
                params.put("name",nama);
                params.put("password", password);
                params.put("email",email);
                return params;
            }
        };
        AppController.getInstance(this).addToRequestQueue(stringRequest);
    }
}
