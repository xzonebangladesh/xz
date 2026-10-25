package com.example.myhideapp;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private static final int BG = Color.rgb(246, 247, 251);
    private static final int INK = Color.rgb(24, 29, 43);
    private static final int MUTED = Color.rgb(105, 112, 130);
    private static final int BLUE = Color.rgb(43, 91, 232);
    private static final int GREEN = Color.rgb(20, 130, 83);
    private static final int RED = Color.rgb(196, 45, 57);
    private LinearLayout root;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showHomePage();
    }

    private void showHomePage() {
        root = createRoot();
        TextView eyebrow = text("PRIVATE  •  SIMPLE  •  CLEAR", 11, MUTED, true);
        eyebrow.setLetterSpacing(0.08f); eyebrow.setGravity(Gravity.CENTER);
        TextView title = text("My Hide App", 30, INK, true);
        title.setGravity(Gravity.CENTER); title.setPadding(0, dp(12), 0, dp(8));
        TextView subtitle = text("একটি পরিষ্কার, সহজ অভিজ্ঞতা। নিচের বাটনে চাপ দিয়ে অ্যাপের আইকন Hide করার অনুরোধ পাঠান।", 15, MUTED, false);
        subtitle.setGravity(Gravity.CENTER); subtitle.setLineSpacing(dp(4), 1f); subtitle.setPadding(dp(8), 0, dp(8), dp(24));

        LinearLayout hero = card();
        TextView symbol = text("◉", 42, BLUE, true); symbol.setGravity(Gravity.CENTER);
        TextView heroTitle = text("আপনার নিয়ন্ত্রণে", 19, INK, true); heroTitle.setGravity(Gravity.CENTER);
        TextView heroBody = text("কোনো অতিরিক্ত permission দরকার নেই। এই অ্যাপ লোকেশন, ক্যামেরা, কন্টাক্ট বা ফাইল অ্যাক্সেস চায় না।", 14, MUTED, false);
        heroBody.setGravity(Gravity.CENTER); heroBody.setLineSpacing(dp(4), 1f); heroBody.setPadding(0, dp(8), 0, 0);
        hero.addView(symbol); hero.addView(space(8)); hero.addView(heroTitle); hero.addView(heroBody);

        Button hide = button("Hide App"); hide.setOnClickListener(v -> performHideRequest());
        TextView foot = text("নোট: Android ও ফোনের Launcher অনুযায়ী আইকন Hide করার ফল ভিন্ন হতে পারে।", 12, MUTED, false);
        foot.setGravity(Gravity.CENTER); foot.setLineSpacing(dp(3), 1f); foot.setPadding(dp(8), dp(18), dp(8), 0);

        root.addView(eyebrow); root.addView(title); root.addView(subtitle); root.addView(hero, matchWrap());
        root.addView(space(22)); root.addView(hide, matchWrap()); root.addView(foot);
        setContentView(root);
    }

    private void performHideRequest() {
        String statusTitle, statusBody, report;
        int statusColor;
        ComponentName alias = new ComponentName(this, getPackageName() + ".LauncherAlias");
        try {
            getPackageManager().setComponentEnabledSetting(alias,
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                    PackageManager.DONT_KILL_APP);
            statusColor = GREEN;
            statusTitle = "Hide অনুরোধ পাঠানো হয়েছে";
            statusBody = "Android launcher-এর কাছে আইকন Hide করার অনুরোধ পাঠানো হয়েছে। তবে ফোনের Launcher পরিবর্তনটি গ্রহণ করেছে কি না, অ্যাপ নিজে সব মডেলে নিশ্চিতভাবে যাচাই করতে পারে না। আইকন সঙ্গে সঙ্গে না সরলে Launcher refresh বা কিছু সময় লাগতে পারে।";
            report = statusTitle + "\n\n" + statusBody + "\n\nPackage: " + getPackageName() + "\nResult: launcher alias disable request sent";
        } catch (Exception e) {
            statusColor = RED;
            statusTitle = "Hide করা যায়নি";
            String message = e.getMessage();
            if (message == null || message.trim().isEmpty()) message = e.getClass().getSimpleName();
            statusBody = "Android আইকন Hide করার অনুরোধ গ্রহণ করেনি। কারণ/এরর: " + message;
            report = statusTitle + "\n\n" + statusBody + "\n\nPackage: " + getPackageName() + "\nError type: " + e.getClass().getName();
        }
        showResultPage(statusColor, statusTitle, statusBody, report);
    }

    private void showResultPage(int statusColor, String statusTitle, String statusBody, String report) {
        root = createRoot();
        TextView back = text("‹  Back", 16, BLUE, true);
        back.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL); back.setPadding(0, 0, 0, dp(20)); back.setOnClickListener(v -> showHomePage());
        TextView eyebrow = text("HIDE STATUS", 11, MUTED, true); eyebrow.setGravity(Gravity.CENTER); eyebrow.setLetterSpacing(0.08f);
        TextView title = text("ফলাফল", 28, INK, true); title.setGravity(Gravity.CENTER); title.setPadding(0, dp(8), 0, dp(20));

        LinearLayout statusCard = card(); statusCard.setOnClickListener(v -> copyReport(report)); statusCard.setFocusable(true); statusCard.setClickable(true);
        TextView icon = text(statusColor == GREEN ? "✓" : "!", 34, Color.WHITE, true); icon.setGravity(Gravity.CENTER);
        GradientDrawable circle = new GradientDrawable(); circle.setColor(statusColor); circle.setShape(GradientDrawable.OVAL);
        icon.setBackground(circle); LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(dp(66), dp(66)); iconParams.gravity = Gravity.CENTER;
        statusCard.addView(icon, iconParams);
        TextView heading = text(statusTitle, 20, statusColor, true); heading.setGravity(Gravity.CENTER); heading.setPadding(0, dp(18), 0, dp(10)); statusCard.addView(heading);
        TextView body = text(statusBody, 14, INK, false); body.setGravity(Gravity.CENTER); body.setLineSpacing(dp(4), 1f); statusCard.addView(body);
        TextView hint = text("কার্ডে চাপ দিন — রিপোর্ট কপি হবে", 12, MUTED, true); hint.setGravity(Gravity.CENTER); hint.setPadding(0, dp(18), 0, 0); statusCard.addView(hint);

        Button copy = button("রিপোর্ট কপি করুন"); copy.setOnClickListener(v -> copyReport(report));
        Button home = button("হোমে ফিরুন"); home.setBackground(makeBackground(Color.WHITE, Color.rgb(222, 226, 235), 16)); home.setTextColor(INK); home.setOnClickListener(v -> showHomePage());
        TextView note = text("সীমাবদ্ধতা: সব Android ফোনে নিজস্ব অ্যাপের আইকন লুকানো সমর্থিত নয়।", 12, MUTED, false); note.setGravity(Gravity.CENTER); note.setPadding(dp(8), dp(18), dp(8), 0);

        root.addView(back); root.addView(eyebrow); root.addView(title); root.addView(statusCard, matchWrap()); root.addView(space(18));
        root.addView(copy, matchWrap()); root.addView(space(10)); root.addView(home, matchWrap()); root.addView(note); setContentView(root);
    }

    private void copyReport(String report) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard != null) {
            clipboard.setPrimaryClip(ClipData.newPlainText("Hide App report", report));
            Toast.makeText(this, "রিপোর্ট কপি হয়েছে", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(this, "ক্লিপবোর্ড পাওয়া যায়নি", Toast.LENGTH_LONG).show();
    }

    private LinearLayout createRoot() {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL); layout.setGravity(Gravity.CENTER_HORIZONTAL);
        layout.setPadding(dp(24), dp(28), dp(24), dp(28)); layout.setBackgroundColor(BG);
        return layout;
    }
    private LinearLayout card() {
        LinearLayout panel = new LinearLayout(this); panel.setOrientation(LinearLayout.VERTICAL); panel.setGravity(Gravity.CENTER_HORIZONTAL);
        panel.setPadding(dp(22), dp(24), dp(22), dp(24)); panel.setBackground(makeBackground(Color.WHITE, Color.rgb(232, 235, 243), 24)); panel.setElevation(dp(3));
        return panel;
    }
    private TextView text(String value, int size, int color, boolean bold) {
        TextView view = new TextView(this); view.setText(value); view.setTextSize(size); view.setTextColor(color);
        view.setTypeface(Typeface.create("sans-serif", bold ? Typeface.BOLD : Typeface.NORMAL)); return view;
    }
    private Button button(String label) {
        Button b = new Button(this); b.setText(label); b.setTextSize(16); b.setAllCaps(false);
        b.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL)); b.setTextColor(Color.WHITE);
        b.setPadding(dp(16), dp(8), dp(16), dp(8)); b.setBackground(makeBackground(BLUE, BLUE, 16)); return b;
    }
    private GradientDrawable makeBackground(int fill, int stroke, int radius) {
        GradientDrawable d = new GradientDrawable(); d.setColor(fill); d.setCornerRadius(dp(radius)); d.setStroke(dp(1), stroke); return d;
    }
    private LinearLayout.LayoutParams matchWrap() { return new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT); }
    private View space(int heightDp) { View v = new View(this); v.setLayoutParams(new LinearLayout.LayoutParams(1, dp(heightDp))); return v; }
    private int dp(int value) { return Math.round(value * getResources().getDisplayMetrics().density); }
}
