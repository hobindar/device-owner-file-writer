package net.soti.fhgadfkhgasdfhgksdfhvbikadfb;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InterruptedIOException;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button pushConfigButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pushConfigButton = findViewById(R.id.push_config_button);
        pushConfigButton.setOnClickListener((view) -> new Thread(this::logRestrictions).start());

        ComponentName admin = new ComponentName(this, DeviceAdmin.class);
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        devicePolicyManager.setPermissionGrantState(admin, getPackageName(), "android.permission.READ_EXTERNAL_STORAGE",
                DevicePolicyManager.PERMISSION_GRANT_STATE_GRANTED);
        devicePolicyManager.setPermissionGrantState(admin, getPackageName(), "android.permission.WRITE_EXTERNAL_STORAGE",
                DevicePolicyManager.PERMISSION_GRANT_STATE_GRANTED);
    }

    private void logRestrictions() {
        String filename = "foo";
        String name = "foo";

        File folder = Environment.getExternalStorageDirectory();
        File myFile = new File(folder, filename);

        try (FileOutputStream fstream = new FileOutputStream(myFile)) {
            fstream.write(name.getBytes());
        } catch (InterruptedIOException e) {
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            Log.e("SimpleAdmin", "Failed to write file!");
            runOnUiThread(() -> Toast.makeText(this, "Failed to write file!", Toast.LENGTH_LONG).show());
        }
    }

}
