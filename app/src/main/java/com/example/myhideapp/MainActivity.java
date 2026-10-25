
package com.example.myhideapp;

import android.app.Activity;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private ComponentName launcherComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        launcherComponent =
                new ComponentName(this, MainActivity.class);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        layout.setPadding(dp(24), dp(24), dp(24), dp(24));
        layout.setBackgroundColor(Color.rgb(245, 247, 250));

        TextView title = new TextView(this);
        title.setText("My Hide App");
        title.setTextSize(26);
        title.setTextColor(Color.rgb(25, 35, 55));
        title.setGravity(Gravity.CENTER);
        title.setPadding(0, 0, 0, dp(18));

        TextView description = new TextView(this);
        description.setText(
                "অ্যাপের আইকন লুকাতে নিচের বাটনে চাপ দিন।"
        );
        description.setTextSize(16);
        description.setTextColor(Color.rgb(70, 80, 95));
        description.setGravity(Gravity.CENTER);
        description.setPadding(0, 0, 0, dp(24));

        Button hideButton = new Button(this);
        hideButton.setText("Hide App");
        hideButton.setAllCaps(false);
        hideButton.setOnClickListener(v -> hideLauncherIcon());

        TextView note = new TextView(this);
        note.setText(
                "সতর্কতা: Hide করলে সাধারণ অ্যাপ আইকন থেকে "
                + "আর খোলা যাবে না।"
        );
        note.setTextSize(13);
        note.setTextColor(Color.rgb(95, 100, 110));
        note.setGravity(Gravity.CENTER);
        note.setPadding(0, dp(24), 0, 0);

        layout.addView(title, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        layout.addView(description, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        layout.addView(hideButton, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        layout.addView(note, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        setContentView(layout);
    }

    private void hideLauncherIcon() {
        try {
            getPackageManager().setComponentEnabledSetting(
                    launcherComponent,
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                    PackageManager.DONT_KILL_APP
            );

            Toast.makeText(
                    this,
                    "আইকন Hide করার অনুরোধ পাঠানো হয়েছে। "
                    + "ফোনের Launcher অনুযায়ী ফলাফল ভিন্ন হতে পারে।",
                    Toast.LENGTH_LONG
            ).show();

        } catch (Exception e) {
            Toast.makeText(
                    this,
                    "এই ফোনে আইকন Hide করা যায়নি: "
                    + e.getMessage(),
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    private int dp(int value) {
        return Math.round(
                value * getResources().getDisplayMetrics().density
        );
    }
}
