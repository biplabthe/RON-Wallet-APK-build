package com.qorion.ronwallet;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    int darkBlue = Color.rgb(7, 21, 46);
    int blue = Color.rgb(10, 55, 110);
    int cyan = Color.rgb(0, 217, 255);
    int white = Color.WHITE;
    int gray = Color.rgb(175, 195, 215);
    int green = Color.rgb(60, 220, 150);

    LinearLayout root;
    LinearLayout content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showHome();
    }

    TextView text(String value, int size, int color, boolean bold) {
        TextView t = new TextView(this);
        t.setText(value);
        t.setTextSize(size);
        t.setTextColor(color);
        t.setGravity(Gravity.CENTER_VERTICAL);

        if (bold) {
            t.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        }

        t.setPadding(8, 8, 8, 8);
        return t;
    }

    GradientDrawable box(int color, int radius) {
        GradientDrawable g = new GradientDrawable();
        g.setColor(color);
        g.setCornerRadius(radius);
        return g;
    }

    Button actionButton(String title) {

        Button b = new Button(this);

        b.setText(title);
        b.setTextColor(white);
        b.setTextSize(14);
        b.setAllCaps(false);
        b.setTypeface(Typeface.DEFAULT, Typeface.BOLD);

        b.setBackground(box(blue, 28));

        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(
                        0,
                        60,
                        1
                );

        p.setMargins(6, 6, 6, 6);

        b.setLayoutParams(p);

        return b;
    }

    void showHome() {

        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(darkBlue);

        // HEADER
        LinearLayout header = new LinearLayout(this);
        header.setGravity(Gravity.CENTER_VERTICAL);
        header.setPadding(18, 20, 18, 12);

        TextView logo = text(
                "QORION",
                26,
                cyan,
                true
        );

        header.addView(
                logo,
                new LinearLayout.LayoutParams(
                        0,
                        60,
                        1
                )
        );

        TextView secure = text(
                "● SECURE",
                12,
                green,
                true
        );

        secure.setGravity(Gravity.CENTER);

        header.addView(
                secure,
                new LinearLayout.LayoutParams(
                        90,
                        50
                )
        );

        root.addView(header);

        // SCROLL CONTENT
        ScrollView scroll = new ScrollView(this);

        content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(18, 5, 18, 25);

        scroll.addView(content);

        // WELCOME
        TextView welcome = text(
                "Quantum-Resilient Wallet",
                15,
                gray,
                false
        );

        content.addView(welcome);

        // PORTFOLIO CARD
        LinearLayout portfolio =
                new LinearLayout(this);

        portfolio.setOrientation(
                LinearLayout.VERTICAL
        );

        portfolio.setPadding(
                22,
                22,
                22,
                22
        );

        portfolio.setBackground(
                box(
                        Color.rgb(12, 43, 82),
                        35
                )
        );

        TextView label = text(
                "TOTAL PORTFOLIO",
                12,
                gray,
                true
        );

        portfolio.addView(label);

        TextView balance = text(
                "$12,842.50",
                34,
                white,
                true
        );

        balance.setPadding(8, 12, 8, 5);

        portfolio.addView(balance);

        TextView change = text(
                "+4.82%   •   Today",
                14,
                green,
                true
        );

        portfolio.addView(change);

        TextView wallet = text(
                "0x7A9F...42B8",
                13,
                gray,
                false
        );

        wallet.setPadding(8, 18, 8, 4);

        portfolio.addView(wallet);

        content.addView(
                portfolio,
                new LinearLayout.LayoutParams(
                        -1,
                        -2
                )
        );

        // SPACE
        addSpace(12);

        // ACTION BUTTONS
        LinearLayout actions = new LinearLayout(this);

        actions.setOrientation(
                LinearLayout.HORIZONTAL
        );

        Button send = actionButton("SEND");
        Button receive = actionButton("RECEIVE");
        Button swap = actionButton("SWAP");

        actions.addView(send);
        actions.addView(receive);
        actions.addView(swap);

        content.addView(actions);

        send.setOnClickListener(v ->
                showMessage("Send screen • Demo UI")
        );

        receive.setOnClickListener(v ->
                showMessage("Receive screen • Demo UI")
        );

        swap.setOnClickListener(v ->
                showMessage("Swap screen • Demo UI")
        );

        addSpace(15);

        // ASSETS TITLE
        TextView assetsTitle = text(
                "YOUR ASSETS",
                17,
                white,
                true
        );

        content.addView(assetsTitle);

        addAsset(
                "RON",
                "QORION Network",
                "12,500 RON",
                "$8,750.00"
        );

        addAsset(
                "USDT",
                "Stablecoin",
                "2,500 USDT",
                "$2,500.00"
        );

        addAsset(
                "QOR",
                "QORION Ecosystem",
                "1,592 QOR",
                "$1,592.50"
        );

        addSpace(18);

        // RECENT TRANSACTIONS
        TextView txTitle = text(
                "RECENT ACTIVITY",
                17,
                white,
                true
        );

        content.addView(txTitle);

        addTransaction(
                "Received",
                "+500 RON",
                "Today • 10:42 AM",
                green
        );

        addTransaction(
                "Sent",
                "-120 RON",
                "Yesterday • 08:25 PM",
                Color.rgb(255, 120, 120)
        );

        addTransaction(
                "Swap",
                "USDT → RON",
                "Yesterday • 04:12 PM",
                cyan
        );

        addSpace(18);

        // SECURITY CARD
        LinearLayout security =
                new LinearLayout(this);

        security.setOrientation(
                LinearLayout.VERTICAL
        );

        security.setPadding(
                18,
                18,
                18,
                18
        );

        security.setBackground(
                box(
                        Color.rgb(9, 35, 68),
                        28
                )
        );

        TextView securityTitle = text(
                "🛡 Wallet Security",
                16,
                white,
                true
        );

        security.addView(securityTitle);

        TextView securityText = text(
                "Quantum-resilient security layer\n"
                + "Biometric protection enabled\n"
                + "Private keys remain under your control",
                13,
                gray,
                false
        );

        securityText.setPadding(
                8,
                12,
                8,
                4
        );

        security.addView(securityText);

        content.addView(security);

        addSpace(20);

        TextView demo = text(
                "DEMO UI • NO REAL TRANSACTIONS",
                11,
                cyan,
                true
        );

        demo.setGravity(Gravity.CENTER);

        content.addView(demo);

        TextView tagline = text(
                "A QUANTUM-RESILIENT TOMORROW.",
                12,
                gray,
                false
        );

        tagline.setGravity(Gravity.CENTER);

        content.addView(tagline);

        root.addView(
                scroll,
                new LinearLayout.LayoutParams(
                        -1,
                        0,
                        1
                )
        );

        // BOTTOM NAVIGATION
        LinearLayout nav = new LinearLayout(this);

        nav.setGravity(Gravity.CENTER);
        nav.setPadding(8, 8, 8, 10);

        nav.setBackgroundColor(
                Color.rgb(5, 16, 35)
        );

        Button home = actionButton("HOME");
        Button assets = actionButton("ASSETS");
        Button activity = actionButton("ACTIVITY");
        Button settings = actionButton("SETTINGS");

        nav.addView(home);
        nav.addView(assets);
        nav.addView(activity);
        nav.addView(settings);

        root.addView(nav);

        assets.setOnClickListener(v ->
                showMessage("Assets • Demo UI")
        );

        activity.setOnClickListener(v ->
                showMessage("Activity • Demo UI")
        );

        settings.setOnClickListener(v ->
                showMessage("Settings • Demo UI")
        );

        setContentView(root);
    }

    void addAsset(
            String symbol,
            String name,
            String amount,
            String value
    ) {

        LinearLayout card =
                new LinearLayout(this);

        card.setOrientation(
                LinearLayout.HORIZONTAL
        );

        card.setGravity(
                Gravity.CENTER_VERTICAL
        );

        card.setPadding(
                16,
                14,
                16,
                14
        );

        card.setBackground(
                box(
                        Color.rgb(10, 31, 59),
                        24
                )
        );

        LinearLayout left =
                new LinearLayout(this);

        left.setOrientation(
                LinearLayout.VERTICAL
        );

        TextView s = text(
                symbol,
                18,
                cyan,
                true
        );

        TextView n = text(
                name,
                11,
                gray,
                false
        );

        left.addView(s);
        left.addView(n);

        card.addView(
                left,
                new LinearLayout.LayoutParams(
                        0,
                        -2,
                        1
                )
        );

        LinearLayout right =
                new LinearLayout(this);

        right.setOrientation(
                LinearLayout.VERTICAL
        );

        TextView a = text(
                amount,
                14,
                white,
                true
        );

        a.setGravity(Gravity.RIGHT);

        TextView v = text(
                value,
                12,
                gray,
                false
        );

        v.setGravity(Gravity.RIGHT);

        right.addView(a);
        right.addView(v);

        card.addView(right);

        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(
                        -1,
                        -2
                );

        p.setMargins(0, 8, 0, 0);

        content.addView(card, p);
    }

    void addTransaction(
            String title,
            String amount,
            String time,
            int amountColor
    ) {

        LinearLayout row =
                new LinearLayout(this);

        row.setOrientation(
                LinearLayout.HORIZONTAL
        );

        row.setGravity(
                Gravity.CENTER_VERTICAL
        );

        row.setPadding(
                12,
                12,
                12,
                12
        );

        TextView icon = text(
                "●",
                20,
                cyan,
                true
        );

        row.addView(
                icon,
                new LinearLayout.LayoutParams(
                        35,
                        55
                )
        );

        LinearLayout info =
                new LinearLayout(this);

        info.setOrientation(
                LinearLayout.VERTICAL
        );

        TextView t = text(
                title,
                14,
                white,
                true
        );

        TextView tm = text(
                time,
                11,
                gray,
                false
        );

        info.addView(t);
        info.addView(tm);

        row.addView(
                info,
                new LinearLayout.LayoutParams(
                        0,
                        -2,
                        1
                )
        );

        TextView am = text(
                amount,
                13,
                amountColor,
                true
        );

        am.setGravity(Gravity.RIGHT);

        row.addView(am);

        content.addView(row);
    }

    void addSpace(int dp) {

        View space = new View(this);

        content.addView(
                space,
                new LinearLayout.LayoutParams(
                        1,
                        dp
                )
        );
    }

    void showMessage(String message) {

        Toast.makeText(
                this,
                message,
                Toast.LENGTH_SHORT
        ).show();
    }
            }
