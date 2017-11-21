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
import rafif.com.baymap.Controller.SessionManager;
import rafif.com.baymap.Model.User;
import rafif.com.baymap.R;

public class LoginActivity extends AppCompatActivity {

    EditText _username , _password;
    String username , password;
    Button btnLogin, btnRegister;
    SessionManager session;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new SessionManager(getApplicationContext());

        _username = (EditText) findViewById(R.id.edtText_Login_Username);
        _password = (EditText) findViewById(R.id.edtText_Login_password);
        btnLogin = (Button) findViewById(R.id.btn_Login_action);
        btnRegister = (Button) findViewById(R.id.btn_Login_RegisterLink);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                username = _username.getText().toString().trim();
                password = _password.getText().toString().trim();

                if (username.isEmpty() && password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Field username dan Password Harus di Isi", Toast.LENGTH_LONG).show();
                }else {
                    doLogin(username, password);

                }
            }
        });

    }

    private void doLogin(final String username, final String password) {
        pDialog = new ProgressDialog(this, R.style.Theme_AppCompat_DayNight_Dialog);
        pDialog.setMessage("Authenticating..");
        pDialog.show();
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        pDialog.hide();

                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
                            pDialog.setMessage("Loading...");
                            //if no error in response
                            if (obj.getInt("code") == 1) {

                                Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_LONG).show();
                                JSONObject userJson = obj.getJSONObject("data");

                                User user = new User(
                                        userJson.getInt("id"),
                                        userJson.getString("name"),
                                        userJson.getString("username"),
                                        userJson.getString("email")
                                );

                                // TODO ambil data ke page selanjutnya
                                session.createLoginSession(
                                        user.getName(),
                                        user.getEmail(),
                                        user.getUsername()
                                );

                                //TODO pindah Intent
                                pDialog.dismiss();
                                Intent afterlogin = new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(afterlogin);
                                finish();
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
                        pDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };
        AppController.getInstance(this).addToRequestQueue(stringRequest);
    }
}
