package com.qorion.ronwallet;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.*;

public class MainActivity extends Activity {

    // =========================
    // QORION WALLET UI
    // =========================

    private final int BG = Color.rgb(5, 20, 48);
    private final int CARD = Color.rgb(10, 40, 82);
    private final int CARD2 = Color.rgb(13, 50, 100);
    private final int CYAN = Color.rgb(0, 210, 255);
    private final int BLUE = Color.rgb(30, 100, 235);
    private final int WHITE = Color.WHITE;
    private final int MUTED = Color.rgb(165, 190, 220);
    private final int GREEN = Color.rgb(55, 225, 145);
    private final int RED = Color.rgb(255, 105, 125);

    private LinearLayout root;
    private LinearLayout content;

    private ImageView qorionLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(BG);
        getWindow().setNavigationBarColor(BG);

        showHome();
    }

    // =========================
    // MAIN HOME
    // =========================

    private void showHome() {

        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(BG);

        // Header
        LinearLayout header = new LinearLayout(this);
        header.setGravity(Gravity.CENTER_VERTICAL);
        header.setPadding(22, 18, 22, 10);

        qorionLogo = new ImageView(this);

        try {
            qorionLogo.setImageResource(
                    getResources().getIdentifier(
                            "qorion_logo",
                            "drawable",
                            getPackageName()
                    )
            );
        } catch (Exception ignored) {}

        qorionLogo.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        header.addView(
                qorionLogo,
                new LinearLayout.LayoutParams(125, 70)
        );

        TextView walletTitle = text(
                "QORION WALLET",
                19,
                WHITE,
                true
        );

        LinearLayout.LayoutParams titleParams =
                new LinearLayout.LayoutParams(
                        0,
                        -2,
                        1
                );

        titleParams.gravity = Gravity.CENTER_VERTICAL;

        header.addView(walletTitle, titleParams);

        TextView menu = text("⋮", 32, WHITE, false);
        menu.setGravity(Gravity.CENTER);

        header.addView(
                menu,
                new LinearLayout.LayoutParams(45, 60)
        );

        root.addView(header);

        // Scroll content
        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);

        content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(15, 5, 15, 20);

        scroll.addView(content);

        // Portfolio card
        LinearLayout portfolio = card();

        TextView portfolioLabel =
                text("TOTAL PORTFOLIO", 14, MUTED, true);

        portfolio.addView(portfolioLabel);

        TextView amount =
                text("$12,842.50", 42, WHITE, true);

        amount.setPadding(0, 8, 0, 2);

        portfolio.addView(amount);

        TextView profit =
                text("+4.82%   •   Today", 17, GREEN, true);

        portfolio.addView(profit);

        TextView address =
                text("0x7A9F...42B8", 15, MUTED, false);

        address.setPadding(0, 18, 0, 0);

        portfolio.addView(address);

        content.addView(
                portfolio,
                marginParams(0, 8, 0, 15)
        );

        // Action buttons
        LinearLayout actions = new LinearLayout(this);
        actions.setOrientation(LinearLayout.HORIZONTAL);

        Button send = actionButton("SEND");
        Button receive = actionButton("RECEIVE");
        Button swap = actionButton("SWAP");

        actions.addView(send, weightParams());
        actions.addView(receive, weightParams());
        actions.addView(swap, weightParams());

        send.setOnClickListener(v -> showSend());
        receive.setOnClickListener(v -> showReceive());
        swap.setOnClickListener(v -> showSwap());

        content.addView(actions);

        // Assets title
        content.addView(
                sectionTitle("YOUR ASSETS"),
                marginParams(0, 22, 0, 8)
        );

        // RON
        content.addView(
                assetCard(
                        "ron_logo",
                        "RON",
                        "QORION Network",
                        "12,500 RON",
                        "$8,750.00"
                )
        );

        // USDT
        content.addView(
                assetCard(
                        null,
                        "USDT",
                        "Stablecoin",
                        "2,500 USDT",
                        "$2,500.00"
                )
        );

        // QOR
        content.addView(
                assetCard(
                        "qorion_logo",
                        "QOR",
                        "QORION Ecosystem",
                        "1,592 QOR",
                        "$1,592.50"
                )
        );

        // Activity
        content.addView(
                sectionTitle("RECENT ACTIVITY"),
                marginParams(0, 22, 0, 8)
        );

        content.addView(
                activityCard(
                        "Received",
                        "Today • 10:42 AM",
                        "+500 RON",
                        GREEN
                )
        );

        content.addView(
                activityCard(
                        "Sent",
                        "Yesterday • 08:25 PM",
                        "-120 RON",
                        RED
                )
        );

        content.addView(
                activityCard(
                        "Swap",
                        "Yesterday • 04:12 PM",
                        "USDT → RON",
                        CYAN
                )
        );

        // Security
        LinearLayout security = card();

        TextView securityTitle =
                text("🛡  WALLET SECURITY", 19, WHITE, true);

        security.addView(securityTitle);

        TextView securityText =
                text(
                        "Quantum-resilient security layer\n" +
                        "Biometric protection enabled\n" +
                        "Private keys remain under your control",
                        15,
                        MUTED,
                        false
                );

        securityText.setPadding(0, 10, 0, 0);

        security.addView(securityText);

        content.addView(
                security,
                marginParams(0, 18, 0, 10)
        );

        TextView demo =
                text(
                        "DEMO UI • NO REAL TRANSACTIONS",
                        14,
                        CYAN,
                        true
                );

        demo.setGravity(Gravity.CENTER);

        content.addView(
                demo,
                marginParams(0, 12, 0, 5)
        );

        TextView tagline =
                text(
                        "A QUANTUM-RESILIENT TOMORROW.",
                        13,
                        MUTED,
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

        // Bottom navigation
        root.addView(bottomNavigation());

        setContentView(root);
    }

    // =========================
    // ASSET CARD
    // =========================

    private View assetCard(
            String logo,
            String symbol,
            String subtitle,
            String balance,
            String value
    ) {

        LinearLayout box = card();

        box.setPadding(14, 12, 14, 12);

        LinearLayout row =
                new LinearLayout(this);

        row.setGravity(Gravity.CENTER_VERTICAL);

        if (logo != null) {

            ImageView icon = new ImageView(this);

            int id = getResources().getIdentifier(
                    logo,
                    "drawable",
                    getPackageName()
            );

            if (id != 0) {
                icon.setImageResource(id);
            }

            icon.setScaleType(
                    ImageView.ScaleType.CENTER_INSIDE
            );

            row.addView(
                    icon,
                    new LinearLayout.LayoutParams(52, 52)
            );
        }

        LinearLayout names =
                new LinearLayout(this);

        names.setOrientation(
                LinearLayout.VERTICAL
        );

        TextView name =
                text(symbol, 22, CYAN, true);

        TextView sub =
                text(subtitle, 14, MUTED, false);

        names.addView(name);
        names.addView(sub);

        LinearLayout.LayoutParams np =
                new LinearLayout.LayoutParams(
                        0,
                        -2,
                        1
                );

        np.setMargins(10, 0, 5, 0);

        row.addView(names, np);

        LinearLayout right =
                new LinearLayout(this);

        right.setOrientation(
                LinearLayout.VERTICAL
        );

        right.setGravity(Gravity.RIGHT);

        TextView bal =
                text(balance, 17, WHITE, true);

        TextView val =
                text(value, 14, MUTED, false);

        right.addView(bal);
        right.addView(val);

        row.addView(right);

        box.addView(row);

        box.setOnClickListener(v ->
                showAsset(symbol)
        );

        return box;
    }

    // =========================
    // SEND SCREEN
    // =========================

    private void showSend() {

        screen("SEND RON");

        addBack();

        addTitle(
                "Send RON",
                "Transfer RON to another wallet"
        );

        EditText address = input(
                "Recipient wallet address"
        );

        EditText amount = input(
                "Amount • RON"
        );

        content.addView(address);
        content.addView(amount);

        Button max = primaryButton("MAX");
        content.addView(max);

        max.setOnClickListener(v ->
                amount.setText("12,500")
        );

        Button confirm =
                primaryButton("CONFIRM SEND");

        content.addView(confirm);

        confirm.setOnClickListener(v ->
                toast(
                        "Demo confirmation • No real transaction"
                )
        );
    }

    // =========================
    // RECEIVE
    // =========================

    private void showReceive() {

        screen("RECEIVE");

        addBack();

        addTitle(
                "Receive RON",
                "Share your wallet address"
        );

        LinearLayout qr = card();

        TextView qrText =
                text(
                        "▣\n\nQORION\nRON\n\n0x7A9F...42B8",
                        25,
                        WHITE,
                        true
                );

        qrText.setGravity(Gravity.CENTER);

        qr.addView(
                qrText,
                new LinearLayout.LayoutParams(
                        -1,
                        300
                )
        );

        content.addView(qr);

        TextView address =
                text(
                        "0x7A9F3B21A88C...42B8",
                        16,
                        CYAN,
                        true
                );

        address.setGravity(Gravity.CENTER);

        content.addView(
                address,
                marginParams(0, 18, 0, 10)
        );

        Button copy =
                primaryButton("COPY ADDRESS");

        content.addView(copy);

        copy.setOnClickListener(v ->
                toast("Address copied")
        );
    }

    // =========================
    // SWAP
    // =========================

    private void showSwap() {

        screen("SWAP");

        addBack();

        addTitle(
                "Swap Assets",
                "Instant demo exchange"
        );

        Spinner from = new Spinner(this);
        Spinner to = new Spinner(this);

        String[] assets = {
                "RON",
                "USDT",
                "QOR"
        };

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_dropdown_item,
                        assets
                );

        from.setAdapter(adapter);
        to.setAdapter(adapter);

        content.addView(
                spinnerBox("FROM", from)
        );

        content.addView(
                spinnerBox("TO", to)
        );

        EditText amount =
                input("Enter amount");

        content.addView(amount);

        Button swap =
                primaryButton("REVIEW SWAP");

        content.addView(swap);

        swap.setOnClickListener(v ->
                toast(
                        "Demo swap • No real transaction"
                )
        );
    }

    // =========================
    // ASSET DETAILS
    // =========================

    private void showAsset(String symbol) {

        screen(symbol);

        addBack();

        addTitle(
                symbol + " ASSET",
                "QORION Wallet"
        );

        LinearLayout info = card();

        TextView amount =
                text(
                        symbol.equals("RON")
                                ? "12,500 RON"
                                : "2,500 " + symbol,
                        32,
                        WHITE,
                        true
                );

        info.addView(amount);

        info.addView(
                text(
                        "Available balance",
                        14,
                        MUTED,
                        false
                )
        );

        content.addView(info);

        Button send =
                primaryButton("SEND");

        Button receive =
                primaryButton("RECEIVE");

        content.addView(send);
        content.addView(receive);

        send.setOnClickListener(v -> showSend());
        receive.setOnClickListener(v -> showReceive());
    }

    // =========================
    // GENERIC SCREEN
    // =========================

    private void screen(String title) {

        root.removeAllViews();

        LinearLayout top =
                new LinearLayout(this);

        top.setGravity(Gravity.CENTER_VERTICAL);
        top.setPadding(18, 18, 18, 10);

        TextView t =
                text(
                        "QORION",
                        20,
                        WHITE,
                        true
                );

        top.addView(t);

        root.addView(top);

        ScrollView scroll =
                new ScrollView(this);

        content =
                new LinearLayout(this);

        content.setOrientation(
                LinearLayout.VERTICAL
        );

        content.setPadding(
                16,
                8,
                16,
                30
        );

        scroll.addView(content);

        root.addView(
                scroll,
                new LinearLayout.LayoutParams(
                        -1,
                        0,
                        1
                )
        );
    }

    private void addBack() {

        Button back =
                new Button(this);

        back.setText("‹  BACK");
        back.setTextColor(CYAN);
        back.setBackgroundColor(Color.TRANSPARENT);

        content.addView(back);

        back.setOnClickListener(v ->
                showHome()
        );
    }

    private void addTitle(
            String title,
            String subtitle
    ) {

        content.addView(
                text(
                        title,
                        30,
                        WHITE,
                        true
                )
        );

        content.addView(
                text(
                        subtitle,
                        15,
                        MUTED,
                        false
                ),
                marginParams(0, 4, 0, 20)
        );
    }

    // =========================
    // BOTTOM NAVIGATION
    // =========================

    private View bottomNavigation() {

        LinearLayout nav =
                new LinearLayout(this);

        nav.setGravity(Gravity.CENTER);
        nav.setPadding(5, 8, 5, 8);

        String[] items = {
                "⌂\nHOME",
                "▣\nASSETS",
                "⇄\nSWAP",
                "◉\nACTIVITY",
                "⚙\nSETTINGS"
        };

        for (String item : items) {

            TextView v =
                    text(
                            item,
                            12,
                            MUTED,
                            true
                    );

            v.setGravity(Gravity.CENTER);

            nav.addView(
                    v,
                    weightParams()
            );

            if (item.contains("HOME")) {

                v.setOnClickListener(
                        x -> showHome()
                );
            }

            if (item.contains("SWAP")) {

                v.setOnClickListener(
                        x -> showSwap()
                );
            }

            if (item.contains("ASSETS")) {

                v.setOnClickListener(
                        x -> showAsset("RON")
                );
            }
        }

        return nav;
    }

    // =========================
    // UI HELPERS
    // =========================

    private TextView text(
            String value,
            float size,
            int color,
            boolean bold
    ) {

        TextView t =
                new TextView(this);

        t.setText(value);
        t.setTextSize(size);
        t.setTextColor(color);

        if (bold) {
            t.setTypeface(
                    Typeface.DEFAULT,
                    Typeface.BOLD
            );
        }

        return t;
    }

    private LinearLayout card() {

        LinearLayout box =
                new LinearLayout(this);

        box.setOrientation(
                LinearLayout.VERTICAL
        );

        GradientDrawable gd =
                new GradientDrawable();

        gd.setColor(CARD);
        gd.setCornerRadius(28);

        box.setBackground(gd);

        box.setPadding(
                18,
                18,
                18,
                18
        );

        return box;
    }

    private Button actionButton(
            String title
    ) {

        Button b =
                new Button(this);

        b.setText(title);
        b.setTextColor(WHITE);
        b.setTextSize(12);
        b.setAllCaps(false);

        GradientDrawable gd =
                new GradientDrawable();

        gd.setColor(CARD2);
        gd.setCornerRadius(25);

        b.setBackground(gd);

        return b;
    }

    private Button primaryButton(
            String title
    ) {

        Button b =
                new Button(this);

        b.setText(title);
        b.setTextColor(WHITE);
        b.setTextSize(14);

        GradientDrawable gd =
                new GradientDrawable();

        gd.setColor(BLUE);
        gd.setCornerRadius(24);

        b.setBackground(gd);

        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(
                        -1,
                        58
                );

        p.setMargins(0, 10, 0, 10);

        b.setLayoutParams(p);

        return b;
    }

    private EditText input(
            String hint
    ) {

        EditText e =
                new EditText(this);

        e.setHint(hint);
        e.setHintTextColor(MUTED);
        e.setTextColor(WHITE);
        e.setTextSize(15);
        e.setPadding(18, 0, 18, 0);

        GradientDrawable gd =
                new GradientDrawable();

        gd.setColor(CARD);
        gd.setCornerRadius(22);

        e.setBackground(gd);

        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(
                        -1,
                        60
                );

        p.setMargins(0, 8, 0, 8);

        e.setLayoutParams(p);

        return e;
    }

    private TextView sectionTitle(
            String title
    ) {

        return text(
                title,
                23,
                WHITE,
                true
        );
    }

    private View activityCard(
            String title,
            String date,
            String amount,
            int amountColor
    ) {

        LinearLayout box =
                new LinearLayout(this);

        box.setGravity(
                Gravity.CENTER_VERTICAL
        );

        box.setPadding(
                10,
                8,
                10,
                8
        );

        LinearLayout left =
                new LinearLayout(this);

        left.setOrientation(
                LinearLayout.VERTICAL
        );

        left.addView(
                text(
                        title,
                        17,
                        WHITE,
                        true
                )
        );

        left.addView(
                text(
                        date,
                        13,
                        MUTED,
                        false
                )
        );

        box.addView(
                left,
                new LinearLayout.LayoutParams(
                        0,
                        -2,
                        1
                )
        );

        TextView right =
                text(
                        amount,
                        16,
                        amountColor,
                        true
                );

        box.addView(right);

        return box;
    }

    private LinearLayout spinnerBox(
            String title,
            Spinner spinner
    ) {

        LinearLayout box =
                card();

        box.addView(
                text(
                        title,
                        13,
                        MUTED,
                        true
                )
        );

        box.addView(
                spinner
        );

        return box;
    }

    private LinearLayout.LayoutParams
    marginParams(
            int l,
            int t,
            int r,
            int b
    ) {

        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(
                        -1,
                        -2
                );

        p.setMargins(l, t, r, b);

        return p;
    }

    private LinearLayout.LayoutParams
    weightParams() {

        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(
                        0,
                        58,
                        1
                );

        p.setMargins(4, 0, 4, 0);

        return p;
    }

    private void toast(String message) {

        Toast.makeText(
                this,
                message,
                Toast.LENGTH_SHORT
        ).show();
    }
}
