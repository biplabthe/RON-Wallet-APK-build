package com.qorion.ronwallet;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;

public class MainActivity extends Activity {

    int blue = Color.rgb(8, 22, 45);
    int cyan = Color.rgb(0, 210, 255);
    int white = Color.WHITE;
    int gray = Color.rgb(180, 195, 210);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(32, 45, 32, 32);
        root.setBackgroundColor(blue);

        TextView logo = new TextView(this);
        logo.setText("◉ RON");
        logo.setTextColor(cyan);
        logo.setTextSize(30);
        logo.setTypeface(Typeface.DEFAULT_BOLD);
        logo.setGravity(Gravity.CENTER);
        root.addView(logo);

        TextView title = new TextView(this);
        title.setText("QUANTUM-RESILIENT WALLET");
        title.setTextColor(white);
        title.setTextSize(14);
        title.setGravity(Gravity.CENTER);
        root.addView(title);

        TextView balance = new TextView(this);
        balance.setText("\n$12,842.50");
        balance.setTextColor(white);
        balance.setTextSize(32);
        balance.setTypeface(Typeface.DEFAULT_BOLD);
        balance.setGravity(Gravity.CENTER);
        root.addView(balance);

        TextView ron = new TextView(this);
        ron.setText("12,500 RON");
        ron.setTextColor(cyan);
        ron.setTextSize(18);
        ron.setGravity(Gravity.CENTER);
        root.addView(ron);

        LinearLayout buttons = new LinearLayout(this);
        buttons.setOrientation(LinearLayout.HORIZONTAL);
        buttons.setGravity(Gravity.CENTER);
        buttons.setPadding(0, 35, 0, 25);

        Button send = new Button(this);
        send.setText("SEND");
        buttons.addView(send);

        Button receive = new Button(this);
        receive.setText("RECEIVE");
        buttons.addView(receive);

        Button swap = new Button(this);
        swap.setText("SWAP");
        buttons.addView(swap);

        root.addView(buttons);

        TextView assets = new TextView(this);
        assets.setText("ASSETS\n\nRON     12,500\nUSDT    2,450\nBNB     3.20");
        assets.setTextColor(white);
        assets.setTextSize(17);
        assets.setPadding(10, 20, 10, 20);
        root.addView(assets);

        TextView transactions = new TextView(this);
        transactions.setText(
                "RECENT TRANSACTIONS\n\n" +
                "Received      +500 RON\n" +
                "Sent          -120 RON\n" +
                "Swap          RON → USDT"
        );
        transactions.setTextColor(gray);
        transactions.setTextSize(15);
        transactions.setPadding(10, 20, 10, 20);
        root.addView(transactions);

        TextView footer = new TextView(this);
        footer.setText("\nA QUANTUM-RESILIENT TOMORROW.");
        footer.setTextColor(cyan);
        footer.setTextSize(13);
        footer.setGravity(Gravity.CENTER);

        root.addView(footer);

        setContentView(root);
    }
}
