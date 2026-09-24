package com.qorion.ronwallet;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * QORION WALLET - Investor Demo UI
 *
 * Fully interactive LOCAL DEMO.
 *
 * This demo simulates:
 * - Create / Import Wallet
 * - Home dashboard
 * - Assets
 * - RON details
 * - Send
 * - Receive
 * - Swap
 * - Buy
 * - Transactions
 * - Security
 * - Settings
 * - Discover
 *
 * IMPORTANT:
 * This is a UI/demo wallet.
 * It does NOT connect to blockchain,
 * does NOT hold real private keys,
 * does NOT sign real transactions,
 * and does NOT move real funds.
 *
 * Optional logo files:
 *
 * app/src/main/res/drawable/qorion_logo.png
 * app/src/main/res/drawable/ron_logo.png
 */
public class MainActivity extends Activity {
    // ============================================================
    // QORION BRAND COLORS
    // ============================================================
    private static final int BG =
            Color.rgb(4, 18, 48);
    private static final int NAVY =
            Color.rgb(7, 29, 65);
    private static final int CARD =
            Color.rgb(246, 249, 253);
    private static final int CARD_SOFT =
            Color.rgb(232, 241, 251);
    private static final int BLUE =
            Color.rgb(0, 126, 255);
    private static final int BLUE2 =
            Color.rgb(34, 170, 255);
    private static final int CYAN =
            Color.rgb(0, 213, 255);
    private static final int WHITE =
            Color.WHITE;
    private static final int TEXT =
            Color.rgb(18, 35, 65);
    private static final int MUTED =
            Color.rgb(102, 122, 150);
    private static final int GREEN =
            Color.rgb(22, 185, 108);
    private static final int RED =
            Color.rgb(225, 74, 94);
    private static final int PURPLE =
            Color.rgb(98, 92, 220);
    private final DecimalFormat money =
            new DecimalFormat("#,##0.00");
    // ============================================================
    // DEMO WALLET STATE
    // ============================================================
    private double ron = 12500.0;
    private double usdt = 1240.0;
    private double bnb = 2.35;
    private double eth = 0.85;
    private double ronPrice = 0.70;
    private boolean biometric = true;
    private boolean notifications = true;
    private boolean walletCreated = false;
    private final List<Tx> transactions =
            new ArrayList<>();
    private LinearLayout root;
    private LinearLayout body;
    private TextView portfolioValue;
    private TextView portfolioChange;
    private final String demoAddress =
            "0x7a9F3B21A88C...8d2e9f4c1";
    // ============================================================
    // ACTIVITY START
    // ============================================================
    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().setStatusBarColor(BG);
        getWindow().setNavigationBarColor(BG);
        seedTransactions();
        showWelcome();
    }
    // ============================================================
    // TRANSACTION MODEL
    // ============================================================
    private static class Tx {
        String type;
        String detail;
        String amount;
        int color;
        Tx(
                String type,
                String detail,
                String amount,
                int color
        ) {
            this.type = type;
            this.detail = detail;
            this.amount = amount;
            this.color = color;
        }
    }
    // ============================================================
    // INITIAL DEMO TRANSACTIONS
    // ============================================================
    private void seedTransactions() {
        if (!transactions.isEmpty()) {
            return;
        }
        transactions.add(
                new Tx(
                        "Received",
                        "From: 0x3a...2d8e",
                        "+500 RON",
                        GREEN
                )
        );
        transactions.add(
                new Tx(
                        "Sent",
                        "To: 0x7b...4e21",
                        "-100 RON",
                        RED
                )
        );
        transactions.add(
                new Tx(
                        "Swap",
                        "To: USDT",
                        "-200 RON",
                        RED
                )
        );
        transactions.add(
                new Tx(
                        "Received",
                        "From: 0x9d...8f44",
                        "+1,000 RON",
                        GREEN
                )
        );
        transactions.add(
                new Tx(
                        "Sent",
                        "To: 0x11...aa9",
                        "-50 RON",
                        RED
                )
        );
    }
    // ============================================================
    // PORTFOLIO CALCULATION
    // ============================================================
    private double portfolio() {
        return
                ron * ronPrice
                        + usdt
                        + bnb * 500.0
                        + eth * 1973.0;
    }
    // ============================================================
    // BASE SCREEN
    // ============================================================
    private void base(
            String title,
            boolean back
    ) {
        root = new LinearLayout(this);
        root.setOrientation(
                LinearLayout.VERTICAL
        );
        root.setBackgroundColor(BG);
        root.addView(
                header(title, back)
        );
        ScrollView scroll =
                new ScrollView(this);
        scroll.setFillViewport(true);
        body = new LinearLayout(this);
        body.setOrientation(
                LinearLayout.VERTICAL
        );
        body.setPadding(
                14,
                10,
                14,
                22
        );
        scroll.addView(body);
        root.addView(
                scroll,
                new LinearLayout.LayoutParams(
                        -1,
                        0,
                        1
                )
        );
        if (
                !title.equals("Welcome")
                        &&
                !title.equals("Create / Import")
        ) {
            root.addView(
                    bottomNav()
            );
        }
        setContentView(root);
    }
    // ============================================================
    // TOP HEADER
    // ============================================================
    private View header(
            String title,
            boolean back
    ) {
        LinearLayout h =
                new LinearLayout(this);
        h.setGravity(
                Gravity.CENTER_VERTICAL
        );
        h.setPadding(
                16,
                8,
                14,
                8
        );
        if (back) {
            TextView b =
                    tv(
                            "‹",
                            38,
                            WHITE,
                            false
                    );
            b.setGravity(
                    Gravity.CENTER
            );
            h.addView(
                    b,
                    new LinearLayout.LayoutParams(
                            48,
                            54
                    )
            );
            b.setOnClickListener(
                    v -> showHome()
            );
        }
        ImageView logo =
                new ImageView(this);
        setImageByName(
                logo,
                "qorion_logo"
        );
        logo.setScaleType(
                ImageView.ScaleType.CENTER_INSIDE
        );
        h.addView(
                logo,
                new LinearLayout.LayoutParams(
                        56,
                        54
                )
        );
        TextView t =
                tv(
                        title,
                        18,
                        WHITE,
                        true
                );
        h.addView(
                t,
                new LinearLayout.LayoutParams(
                        0,
                        54,
                        1
                )
        );
        TextView bell =
                tv(
                        "♧",
                        23,
                        WHITE,
                        false
                );
        bell.setGravity(
                Gravity.CENTER
        );
        h.addView(
                bell,
                new LinearLayout.LayoutParams(
                        42,
                        54
                )
        );
        TextView more =
                tv(
                        "⋮",
                        28,
                        WHITE,
                        false
                );
        more.setGravity(
                Gravity.CENTER
        );
        h.addView(
                more,
                new LinearLayout.LayoutParams(
                        35,
                        54
                )
        );
        return h;
    }
    // ============================================================
    // BOTTOM NAVIGATION
    // ============================================================
    private LinearLayout bottomNav() {
        LinearLayout nav =
                new LinearLayout(this);
        nav.setGravity(
                Gravity.CENTER
        );
        nav.setPadding(
                4,
                5,
                4,
                5
        );
        nav.setBackgroundColor(
                Color.rgb(7, 28, 62)
        );
        nav.addView(
                navItem(
                        "⌂",
                        "Home",
                        () -> showHome()
                )
        );
        nav.addView(
                navItem(
                        "▣",
                        "Wallet",
                        () -> showWallet()
                )
        );
        nav.addView(
                navItem(
                        "⇄",
                        "Swap",
                        () -> showSwap()
                )
        );
        nav.addView(
                navItem(
                        "◉",
                        "Discover",
                        () -> showDiscover()
                )
        );
        nav.addView(
                navItem(
                        "⚙",
                        "Settings",
                        () -> showSettings()
                )
        );
        return nav;
    }
    private TextView navItem(
            String icon,
            String label,
            final Runnable action
    ) {
        TextView v =
                tv(
                        icon + "\n" + label,
                        11,
                        MUTED,
                        true
                );
        v.setGravity(
                Gravity.CENTER
        );
        v.setPadding(
                2,
                4,
                2,
                4
        );
        v.setOnClickListener(
                x -> action.run()
        );
        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(
                        0,
                        64,
                        1
                );
        v.setLayoutParams(p);
        return v;
    }
    // ============================================================
    // WELCOME
    // ============================================================
    private void showWelcome() {
        base(
                "Welcome",
                false
        );
        LinearLayout hero =
                new LinearLayout(this);
        hero.setOrientation(
                LinearLayout.VERTICAL
        );
        hero.setGravity(
                Gravity.CENTER_HORIZONTAL
        );
        hero.setPadding(
                16,
                20,
                16,
                20
        );
        ImageView logo =
                new ImageView(this);
        setImageByName(
                logo,
                "qorion_logo"
        );
        logo.setScaleType(
                ImageView.ScaleType.CENTER_INSIDE
        );
        hero.addView(
                logo,
                new LinearLayout.LayoutParams(
                        -1,
                        220
                )
        );
        TextView title =
                tv(
                        "QORION",
                        42,
                        WHITE,
                        true
                );
        title.setGravity(
                Gravity.CENTER
        );
        hero.addView(title);
        TextView wallet =
                tv(
                        "W A L L E T",
                        16,
                        CYAN,
                        true
                );
        wallet.setGravity(
                Gravity.CENTER
        );
        hero.addView(wallet);
        TextView tag =
                tv(
                        "A QUANTUM-RESILIENT\nTOMORROW",
                        15,
                        CYAN,
                        true
                );
        tag.setGravity(
                Gravity.CENTER
        );
        tag.setPadding(
                0,
                12,
                0,
                20
        );
        hero.addView(tag);
        TextView sub =
                tv(
                        "Secure • Fast • Global",
                        14,
                        WHITE,
                        false
                );
        sub.setGravity(
                Gravity.CENTER
        );
        hero.addView(sub);
        Button start =
                primary(
                        "Get Started"
                );
        hero.addView(
                start,
                new LinearLayout.LayoutParams(
                        -1,
                        58
                )
        );
        start.setOnClickListener(
                v -> showOnboarding()
        );
        Button imp =
                outline(
                        "Import Wallet"
                );
        hero.addView(
                imp,
                new LinearLayout.LayoutParams(
                        -1,
                        56
                )
        );
        imp.setOnClickListener(
                v -> showImport()
        );
        body.addView(hero);
    }
    // ============================================================
    // ONBOARDING
    // ============================================================
    private void showOnboarding() {
        base(
                "Create / Import",
                true
        );
        body.addView(
                tv(
                        "Create or Import Wallet",
                        28,
                        WHITE,
                        true
                )
        );
        body.addView(
                tv(
                        "Choose how you want to enter the QORION Wallet demo.",
                        14,
                        MUTED,
                        false
                )
        );
        View create =
                optionCard(
                        "＋",
                        "Create New Wallet",
                        "Set up a new QORION wallet with secure recovery phrase.",
                        BLUE
                );
        body.addView(create);
        create.setOnClickListener(
                v -> showCreate()
        );
        View imp =
                optionCard(
                        "⇩",
                        "Import Existing Wallet",
                        "Import using a recovery phrase or private key.",
                        PURPLE
                );
        body.addView(imp);
        imp.setOnClickListener(
                v -> showImport()
        );
        View hardware =
                optionCard(
                        "▣",
                        "Connect Hardware Wallet",
                        "Use an external hardware wallet for more security.",
                        CYAN
                );
        body.addView(hardware);
        hardware.setOnClickListener(
                v ->
                        toast(
                                "Hardware connection is simulated in this demo."
                        )
        );
    }
    // ============================================================
    // CREATE WALLET
    // ============================================================
    private void showCreate() {
        base(
                "Create Wallet",
                true
        );
        body.addView(
                tv(
                        "Create your QORION Wallet",
                        26,
                        WHITE,
                        true
                )
        );
        body.addView(
                tv(
                        "This demo generates a sample recovery phrase locally for presentation.",
                        14,
                        MUTED,
                        false
                )
        );
        String phrase =
                "quantum galaxy orbit resilient\n"
                        + "wallet future secure network\n"
                        + "blue nova shield horizon";
        LinearLayout phraseCard =
                whiteCard();
        TextView p =
                tv(
                        phrase,
                        17,
                        TEXT,
                        true
                );
        p.setPadding(
                10,
                10,
                10,
                10
        );
        phraseCard.addView(p);
        body.addView(
                phraseCard
        );
        Button copied =
                primary(
                        "Copy Recovery Phrase"
                );
        body.addView(copied);
        copied.setOnClickListener(
                v -> copyText(phrase)
        );
        CheckBox check =
                new CheckBox(this);
        check.setText(
                "I understand this is a demo recovery phrase."
        );
        check.setTextColor(
                WHITE
        );
        body.addView(check);
        Button create =
                primary(
                        "Create Wallet"
                );
        body.addView(create);
        create.setOnClickListener(
                v -> {
                    if (!check.isChecked()) {
                        toast(
                                "Please confirm the demo phrase."
                        );
                        return;
                    }
                    walletCreated = true;
                    toast(
                            "QORION Wallet created."
                    );
                    showHome();
                }
        );
    }
    // ============================================================
    // IMPORT WALLET
    // ============================================================
    private void showImport() {
        base(
                "Import Wallet",
                true
        );
        body.addView(
                tv(
                        "Import Existing Wallet",
                        26,
                        WHITE,
                        true
                )
        );
        body.addView(
                tv(
                        "Enter a demo phrase or private-key placeholder.",
                        14,
                        MUTED,
                        false
                )
        );
        EditText phrase =
                input(
                        "Recovery phrase / private key"
                );
        phrase.setInputType(
                InputType.TYPE_CLASS_TEXT
                        |
                        InputType.TYPE_TEXT_VARIATION_PASSWORD
        );
        body.addView(phrase);
        Button importBtn =
                primary(
                        "Import Wallet"
                );
        body.addView(importBtn);
        importBtn.setOnClickListener(
                v -> {
                    if (
                            phrase
                                    .getText()
                                    .toString()
                                    .trim()
                                    .length() < 6
                    ) {
                        toast(
                                "Enter a demo value to continue."
                        );
                        return;
                    }
                    walletCreated = true;
                    toast(
                            "Wallet imported in demo mode."
                    );
                    showHome();
                }
        );
    }
    // ============================================================
    // HOME
    // ============================================================
    private void showHome() {
        base(
                "QORION Wallet",
                false
        );
        // --------------------------------------------------------
        // Portfolio card
        // --------------------------------------------------------
        LinearLayout portfolioCard =
                darkCard();
        TextView label =
                tv(
                        "TOTAL PORTFOLIO",
                        14,
                        Color.rgb(180, 204, 232),
                        true
                );
        portfolioCard.addView(label);
        portfolioValue =
                tv(
                        "$" + money.format(portfolio()),
                        40,
                        WHITE,
                        true
                );
        portfolioCard.addView(
                portfolioValue
        );
        portfolioChange =
                tv(
                        "+5.24%  •  24h",
                        16,
                        GREEN,
                        true
                );
        portfolioCard.addView(
                portfolioChange
        );
        TextView address =
                tv(
                        demoAddress,
                        14,
                        Color.rgb(175, 198, 228),
                        false
                );
        address.setPadding(
                0,
                8,
                0,
                0
        );
        portfolioCard.addView(address);
        body.addView(
                portfolioCard
        );
        // --------------------------------------------------------
        // Quick actions
        // --------------------------------------------------------
        LinearLayout actions =
                new LinearLayout(this);
        actions.setGravity(
                Gravity.CENTER
        );
        actions.setPadding(
                0,
                8,
                0,
                10
        );
        actions.addView(
                actionCircle(
                        "↗",
                        "Send",
                        BLUE,
                        () -> showSend()
                )
        );
        actions.addView(
                actionCircle(
                        "⇩",
                        "Receive",
                        CYAN,
                        () -> showReceive()
                )
        );
        actions.addView(
                actionCircle(
                        "⇄",
                        "Swap",
                        PURPLE,
                        () -> showSwap()
                )
        );
        actions.addView(
                actionCircle(
                        "$",
                        "Buy",
                        GREEN,
                        () -> showBuy()
                )
        );
        body.addView(actions);
        // --------------------------------------------------------
        // Assets title
        // --------------------------------------------------------
        body.addView(
                tv(
                        "Assets",
                        24,
                        WHITE,
                        true
                )
        );
        // --------------------------------------------------------
        // RON
        // --------------------------------------------------------
        View ronCard =
                assetCard(
                        "RON",
                        "QORION Network",
                        money.format(ron) + " RON",
                        "$" + money.format(ron * ronPrice),
                        "+4.21%",
                        BLUE
                );
        body.addView(ronCard);
        ronCard.setOnClickListener(
                v -> showRonDetail()
        );
        // --------------------------------------------------------
        // USDT
        // --------------------------------------------------------
        body.addView(
                assetCard(
                        "USDT",
                        "BEP-20",
                        money.format(usdt) + " USDT",
                        "$" + money.format(usdt),
                        "+0.01%",
                        GREEN
                )
        );
        // --------------------------------------------------------
        // BNB
        // --------------------------------------------------------
        body.addView(
                assetCard(
                        "BNB",
                        "BNB Chain",
                        money.format(bnb) + " BNB",
                        "$" + money.format(bnb * 500),
                        "+2.14%",
                        Color.rgb(242, 174, 0)
                )
        );
        // --------------------------------------------------------
        // ETH
        // --------------------------------------------------------
        body.addView(
                assetCard(
                        "ETH",
                        "Ethereum",
                        money.format(eth) + " ETH",
                        "$" + money.format(eth * 1973),
                        "+1.32%",
                        PURPLE
                )
        );
        // --------------------------------------------------------
        // Recent activity
        // --------------------------------------------------------
        body.addView(
                tv(
                        "Recent Activity",
                        24,
                        WHITE,
                        true
                )
        );
        for (
                int i = 0;
                i < Math.min(3, transactions.size());
                i++
        ) {
            body.addView(
                    transactionRow(
                            transactions.get(i)
                    )
            );
        }
        // --------------------------------------------------------
        // Security card
        // --------------------------------------------------------
        LinearLayout security =
                darkCard();
        TextView st =
                tv(
                        "🛡 Wallet Security",
                        20,
                        WHITE,
                        true
                );
        security.addView(st);
        security.addView(
                tv(
                        "Quantum-resilient security layer",
                        14,
                        Color.rgb(177, 199, 227),
                        false
                )
        );
        security.addView(
                tv(
                        "Biometric protection " +
                                (biometric
                                        ? "enabled"
                                        : "disabled"),
                        14,
                        Color.rgb(177, 199, 227),
                        false
                )
        );
        security.addView(
                tv(
                        "Private keys remain under your control",
                        14,
                        Color.rgb(177, 199, 227),
                        false
                )
        );
        body.addView(security);
        TextView demo =
                tv(
                        "DEMO UI • NO REAL TRANSACTIONS",
                        13,
                        CYAN,
                        true
                );
        demo.setGravity(
                Gravity.CENTER
        );
        body.addView(demo);
        TextView tagline =
                tv(
                        "A QUANTUM-RESILIENT TOMORROW.",
                        13,
                        Color.rgb(175, 197, 225),
                        false
                );
        tagline.setGravity(
                Gravity.CENTER
        );
        body.addView(tagline);
    }
    // ============================================================
    // WALLET / ASSETS SCREEN
    // ============================================================
    private void showWallet() {
        base(
                "Wallet",
                false
        );
        body.addView(
                tv(
                        "Your Assets",
                        28,
                        WHITE,
                        true
                )
        );
        body.addView(
                tv(
                        "Manage your demo portfolio",
                        14,
                        MUTED,
                        false
                )
        );
        View ronCard =
                assetCard(
                        "RON",
                        "QORION Network",
                        money.format(ron) + " RON",
                        "$" + money.format(ron * ronPrice),
                        "+4.21%",
                        BLUE
                );
        body.addView(ronCard);
        ronCard.setOnClickListener(
                v -> showRonDetail()
        );
        View usdtCard =
                assetCard(
                        "USDT",
                        "BEP-20",
                        money.format(usdt) + " USDT",
                        "$" + money.format(usdt),
                        "+0.01%",
                        GREEN
                );
        body.addView(usdtCard);
        body.addView(
                assetCard(
                        "BNB",
                        "BNB Chain",
                        money.format(bnb) + " BNB",
                        "$" + money.format(bnb * 500),
                        "+2.14%",
                        Color.rgb(242, 174, 0)
                )
        );
        body.addView(
                assetCard(
                        "ETH",
                        "Ethereum",
                        money.format(eth) + " ETH",
                        "$" + money.format(eth * 1973),
                        "+1.32%",
                        PURPLE
                )
        );
        Button receive =
                primary(
                        "Receive RON"
                );
        body.addView(receive);
        receive.setOnClickListener(
                v -> showReceive()
        );
        Button send =
                outline(
                        "Send RON"
                );
        body.addView(send);
        send.setOnClickListener(
                v -> showSend()
        );
    }
    // ============================================================
    // RON DETAIL
    // ============================================================
    private void showRonDetail() {
        base(
                "RON",
                true
        );
        LinearLayout top =
                darkCard();
        ImageView logo =
                new ImageView(this);
        setImageByName(
                logo,
                "ron_logo"
        );
        logo.setScaleType(
                ImageView.ScaleType.CENTER_INSIDE
        );
        top.addView(
                logo,
                new LinearLayout.LayoutParams(
                        -1,
                        100
                )
        );
        TextView name =
                tv(
                        "RON",
                        30,
                        WHITE,
                        true
                );
        name.setGravity(
                Gravity.CENTER
        );
        top.addView(name);
        TextView amount =
                tv(
                        money.format(ron) + " RON",
                        24,
                        WHITE,
                        true
                );
        amount.setGravity(
                Gravity.CENTER
        );
        top.addView(amount);
        TextView value =
                tv(
                        "$" + money.format(ron * ronPrice)
                                + "   +4.21%",
                        15,
                        GREEN,
                        true
                );
        value.setGravity(
                Gravity.CENTER
        );
        top.addView(value);
        body.addView(top);
        // --------------------------------------------------------
        // Action buttons
        // --------------------------------------------------------
        LinearLayout actions =
                new LinearLayout(this);
        actions.setGravity(
                Gravity.CENTER
        );
        actions.addView(
                actionCircle(
                        "↗",
                        "Send",
                        BLUE,
                        () -> showSend()
                )
        );
        actions.addView(
                actionCircle(
                        "⇩",
                        "Receive",
                        CYAN,
                        () -> showReceive()
                )
        );
        actions.addView(
                actionCircle(
                        "⇄",
                        "Swap",
                        PURPLE,
                        () -> showSwap()
                )
        );
        body.addView(actions);
        // --------------------------------------------------------
        // Chart placeholder
        // --------------------------------------------------------
        LinearLayout chart =
                whiteCard();
        chart.addView(
                tv(
                        "RON Price",
                        18,
                        TEXT,
                        true
                )
        );
        chart.addView(
                tv(
                        "$0.70",
                        28,
                        TEXT,
                        true
                )
        );
        chart.addView(
                chartView()
        );
        body.addView(chart);
        body.addView(
                tv(
                        "Overview",
                        22,
                        WHITE,
                        true
                )
        );
        body.addView(
                infoRow(
                        "Network",
                        "QORION Network"
                )
        );
        body.addView(
                infoRow(
                        "Token",
                        "RON"
                )
        );
        body.addView(
                infoRow(
                        "Balance",
                        money.format(ron) + " RON"
                )
        );
        body.addView(
                infoRow(
                        "Value",
                        "$" + money.format(ron * ronPrice)
                )
        );
    }
    // ============================================================
    // SEND SCREEN
    // ============================================================
    private void showSend() {
        base(
                "Send",
                true
        );
        LinearLayout card =
                whiteCard();
        TextView token =
                tv(
                        "🔵  RON   ⌄",
                        21,
                        TEXT,
                        true
                );
        card.addView(token);
        TextView balance =
                tv(
                        "Balance: " +
                                money.format(ron)
                                + " RON",
                        12,
                        MUTED,
                        false
                );
        balance.setGravity(
                Gravity.RIGHT
        );
        card.addView(balance);
        card.addView(
                tv(
                        "Recipient Address",
                        14,
                        TEXT,
                        true
                )
        );
        EditText address =
                input(
                        "0x... or QR name"
                );
        card.addView(address);
        card.addView(
                tv(
                        "Amount",
                        14,
                        TEXT,
                        true
                )
        );
        EditText amount =
                input(
                        "0 RON"
                );
        amount.setInputType(
                InputType.TYPE_CLASS_NUMBER
                        |
                        InputType.TYPE_NUMBER_FLAG_DECIMAL
        );
        card.addView(amount);
        LinearLayout presets =
                new LinearLayout(this);
        presets.setGravity(
                Gravity.CENTER
        );
        double[] values =
                {
                        0.25,
                        0.50,
                        0.75,
                        1.00
                };
        for (double f : values) {
            Button p =
                    smallButton(
                            (int)(f * 100)
                                    + "%"
                    );
            presets.addView(p);
            p.setOnClickListener(
                    v -> amount.setText(
                            money.format(
                                    ron * f
                            )
                    )
            );
        }
        card.addView(presets);
        card.addView(
                tv(
                        "Network Fee",
                        14,
                        TEXT,
                        true
                )
        );
        TextView fee =
                tv(
                        "~ 0.0005 BNB ($0.15)",
                        14,
                        MUTED,
                        false
                );
        fee.setPadding(
                12,
                12,
                12,
                12
        );
        card.addView(fee);
        Button cont =
                primary(
                        "Continue"
                );
        card.addView(cont);
        cont.setOnClickListener(
                v -> {
                    String a =
                            address
                                    .getText()
                                    .toString()
                                    .trim();
                    String s =
                            amount
                                    .getText()
                                    .toString()
                                    .trim();
                    if (a.length() < 5) {
                        toast(
                                "Enter a recipient address."
                        );
                        return;
                    }
                    if (s.isEmpty()) {
                        toast(
                                "Enter amount."
                        );
                        return;
                    }
                    double value;
                    try {
                        value =
                                Double.parseDouble(s);
                    }
                    catch (Exception e) {
                        toast(
                                "Invalid amount."
                        );
                        return;
                    }
                    if (value <= 0) {
                        toast(
                                "Amount must be greater than zero."
                        );
                        return;
                    }
                    if (value > ron) {
                        toast(
                                "Insufficient RON balance."
                        );
                        return;
                    }
                    confirmSend(
                            a,
                            value
                    );
                }
        );
        body.addView(card);
    }
    // ============================================================
    // SEND CONFIRMATION
    // ============================================================
    private void confirmSend(
            String address,
            double amount
    ) {
        AlertDialog dialog =
                new AlertDialog.Builder(this)
                        .setTitle(
                                "Confirm Demo Send"
                        )
                        .setMessage(
                                "Send "
                                        + money.format(amount)
                                        + " RON\n\nTo:\n"
                                        + address
                                        + "\n\n"
                                        + "This is a simulated transaction."
                        )
                        .setNegativeButton(
                                "Cancel",
                                null
                        )
                        .setPositiveButton(
                                "Confirm",
                                null
                        )
                        .create();
        dialog.setOnShowListener(
                x -> {
                    dialog
                            .getButton(
                                    AlertDialog.BUTTON_POSITIVE
                            )
                            .setOnClickListener(
                                    v -> {
                                        ron -= amount;
                                        transactions.add(
                                                0,
                                                new Tx(
                                                        "Sent",
                                                        "To: "
                                                                + shortAddress(address),
                                                        "-"
                                                                + money.format(amount)
                                                                + " RON",
                                                        RED
                                                )
                                        );
                                        dialog.dismiss();
                                        toast(
                                                "Demo transaction completed."
                                        );
                                        showHome();
                                    }
                            );
                }
        );
        dialog.show();
    }
    // ============================================================
    // RECEIVE SCREEN
    // ============================================================
    private void showReceive() {
        base(
                "Receive",
                true
        );
        LinearLayout card =
                whiteCard();
        ImageView logo =
                new ImageView(this);
        setImageByName(
                logo,
                "ron_logo"
        );
        logo.setScaleType(
                ImageView.ScaleType.CENTER_INSIDE
        );
        card.addView(
                logo,
                new LinearLayout.LayoutParams(
                        -1,
                        70
                )
        );
        TextView name =
                tv(
                        "RON",
                        22,
                        TEXT,
                        true
                );
        name.setGravity(
                Gravity.CENTER
        );
        card.addView(name);
        TextView network =
                tv(
                        "QORION Network",
                        13,
                        MUTED,
                        false
                );
        network.setGravity(
                Gravity.CENTER
        );
        card.addView(network);
        // QR style visual
        card.addView(
                qrView()
        );
        TextView address =
                tv(
                        demoAddress,
                        15,
                        TEXT,
                        true
                );
        address.setGravity(
                Gravity.CENTER
        );
        address.setPadding(
                8,
                14,
                8,
                14
        );
        card.addView(address);
        Button copy =
                primary(
                        "Copy Address"
                );
        card.addView(copy);
        copy.setOnClickListener(
                v ->
                        copyText(
                                demoAddress
                        )
        );
        Button share =
                outline(
                        "Share Address"
                );
        card.addView(share);
        share.setOnClickListener(
                v -> {
                    Intent send =
                            new Intent(
                                    Intent.ACTION_SEND
                            );
                    send.setType(
                            "text/plain"
                    );
                    send.putExtra(
                            Intent.EXTRA_TEXT,
                            "QORION RON Address:\n"
                                    + demoAddress
                    );
                    startActivity(
                            Intent.createChooser(
                                    send,
                                    "Share address"
                            )
                    );
                }
        );
        TextView warning =
                tv(
                        "Send only RON (QORION Network) to this address.",
                        13,
                        MUTED,
                        false
                );
        warning.setGravity(
                Gravity.CENTER
        );
        warning.setPadding(
                12,
                16,
                12,
                12
        );
        card.addView(warning);
        body.addView(card);
    }
    // ============================================================
    // SWAP SCREEN
    // ============================================================
    private void showSwap() {
        base(
                "Swap",
                true
        );
        LinearLayout card =
                whiteCard();
        card.addView(
                tv(
                        "From",
                        13,
                        MUTED,
                        true
                )
        );
        TextView from =
                tv(
                        "🔵  RON",
                        21,
                        TEXT,
                        true
                );
        card.addView(from);
        TextView balance =
                tv(
                        "Balance: "
                                + money.format(ron),
                        12,
                        MUTED,
                        false
                );
        balance.setGravity(
                Gravity.RIGHT
        );
        card.addView(balance);
        EditText amount =
                input(
                        "100"
                );
        amount.setInputType(
                InputType.TYPE_CLASS_NUMBER
                        |
                        InputType.TYPE_NUMBER_FLAG_DECIMAL
        );
        card.addView(amount);
        TextView arrow =
                tv(
                        "⇅",
                        34,
                        BLUE,
                        true
                );
        arrow.setGravity(
                Gravity.CENTER
        );
        card.addView(arrow);
        card.addView(
                tv(
                        "To",
                        13,
                        MUTED,
                        true
                )
        );
        card.addView(
                tv(
                        "🟢  USDT",
                        21,
                        TEXT,
                        true
                )
        );
        EditText receive =
                input(
                        "250.36"
                );
        receive.setInputType(
                InputType.TYPE_CLASS_NUMBER
                        |
                        InputType.TYPE_NUMBER_FLAG_DECIMAL
        );
        card.addView(receive);
        card.addView(
                infoRow(
                        "Rate",
                        "1 RON ≈ 2.5036 USDT"
                )
        );
        card.addView(
                infoRow(
                        "Slippage",
                        "0.5%"
                )
        );
        card.addView(
                infoRow(
                        "Network Fee",
                        "~ $0.15"
                )
        );
        Button swap =
                primary(
                        "Swap"
                );
        card.addView(swap);
        swap.setOnClickListener(
                v -> {
                    double value;
                    try {
                        value =
                                Double.parseDouble(
                                        amount
                                                .getText()
                                                .toString()
                                );
                    }
                    catch (Exception e) {
                        toast(
                                "Enter a valid amount."
                        );
                        return;
                    }
                    if (value <= 0) {
                        toast(
                                "Amount must be greater than zero."
                        );
                        return;
                    }
                    if (value > ron) {
                        toast(
                                "Insufficient RON balance."
                        );
                        return;
                    }
                    double received =
                            value * 2.5036;
                    ron -= value;
                    usdt += received;
                    transactions.add(
                            0,
                            new Tx(
                                    "Swap",
                                    "RON → USDT",
                                    "-"
                                            + money.format(value)
                                            + " RON",
                                    PURPLE
                            )
                    );
                    toast(
                            "Swap completed in demo."
                    );
                    showHome();
                }
        );
        body.addView(card);
    }    
    // ============================================================
    // SETTINGS
    // ============================================================

    private void showSettings() {
        base("Settings", false);

        body.addView(setting("⚙", "Networks",
                "Manage blockchain networks",
                () -> toast("Network manager opened.")));

        body.addView(setting("$", "Currency",
                "USD - US Dollar",
                () -> toast("Currency: USD")));

        body.addView(setting("◎", "Language",
                "English",
                () -> toast("Language selection opened.")));

        body.addView(setting("♧", "Notifications",
                "Transaction alerts",
                () -> {
                    notifications = !notifications;
                    toast("Notifications: " +
                            (notifications ? "ON" : "OFF"));
                }));

        body.addView(setting("◉", "Appearance",
                "Light / Dark / System",
                () -> toast(
                        "Dark blue QORION theme active.")));

        body.addView(setting("ⓘ", "Help & Support",
                "Get help and support",
                () -> toast(
                        "Support center opened.")));

        body.addView(setting("◈", "About QORION Wallet",
                "v1.0.0 • Investor Demo",
                () -> showAbout()));

        body.addView(setting("●", "Security Center",
                "Biometric and wallet protection",
                () -> showSecurity()));

        body.addView(setting("▤", "Transaction History",
                "View all activity",
                () -> showTransactions()));
    }

    private void showAbout() {
        new AlertDialog.Builder(this)
                .setTitle("QORION Wallet")
                .setMessage(
                        "A quantum-resilient wallet concept.\n\n" +
                        "Investor Demonstration Build\n" +
                        "Version 1.0.0\n\n" +
                        "All transactions are simulated locally.")
                .setPositiveButton("OK", null)
                .show();
    }

    // ============================================================
    // UI COMPONENTS
    // ============================================================

    private TextView tv(
            String text,
            float size,
            int color,
            boolean bold) {

        TextView v = new TextView(this);

        v.setText(text);
        v.setTextSize(size);
        v.setTextColor(color);
        v.setPadding(2, 5, 2, 5);

        if (bold) {
            v.setTypeface(
                    Typeface.DEFAULT,
                    Typeface.BOLD);
        }

        return v;
    }

    private Button primary(String text) {

        Button b = new Button(this);

        b.setText(text);
        b.setTextColor(WHITE);
        b.setTextSize(15);
        b.setAllCaps(false);
        b.setGravity(Gravity.CENTER);

        GradientDrawable gd =
                new GradientDrawable(
                        GradientDrawable.Orientation.LEFT_RIGHT,
                        new int[]{BLUE, BLUE2});

        gd.setCornerRadius(30);

        b.setBackground(gd);

        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(
                        -1,
                        56);

        p.setMargins(0, 9, 0, 9);

        b.setLayoutParams(p);

        return b;
    }

    private Button outline(String text) {

        Button b = new Button(this);

        b.setText(text);
        b.setTextColor(WHITE);
        b.setTextSize(14);
        b.setAllCaps(false);

        GradientDrawable gd =
                new GradientDrawable();

        gd.setColor(Color.TRANSPARENT);
        gd.setStroke(1, BLUE2);
        gd.setCornerRadius(30);

        b.setBackground(gd);

        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(
                        -1,
                        54);

        p.setMargins(0, 7, 0, 7);

        b.setLayoutParams(p);

        return b;
    }

    private Button smallButton(String text) {

        Button b = new Button(this);

        b.setText(text);
        b.setTextColor(BLUE);
        b.setTextSize(12);
        b.setAllCaps(false);

        GradientDrawable gd =
                new GradientDrawable();

        gd.setColor(CARD_SOFT);
        gd.setCornerRadius(18);

        b.setBackground(gd);

        b.setPadding(4, 0, 4, 0);

        return b;
    }

    private EditText input(String hint) {

        EditText e = new EditText(this);

        e.setHint(hint);
        e.setHintTextColor(MUTED);
        e.setTextColor(TEXT);
        e.setTextSize(14);
        e.setSingleLine(true);

        e.setPadding(
                16,
                0,
                16,
                0);

        GradientDrawable gd =
                new GradientDrawable();

        gd.setColor(
                Color.rgb(
                        240,
                        246,
                        252));

        gd.setCornerRadius(20);

        e.setBackground(gd);

        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(
                        -1,
                        56);

        p.setMargins(
                0,
                6,
                0,
                9);

        e.setLayoutParams(p);

        return e;
    }

    private LinearLayout whiteCard() {

        LinearLayout l =
                new LinearLayout(this);

        l.setOrientation(
                LinearLayout.VERTICAL);

        l.setPadding(
                15,
                13,
                15,
                13);

        GradientDrawable gd =
                new GradientDrawable();

        gd.setColor(CARD);
        gd.setCornerRadius(22);

        l.setBackground(gd);

        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(
                        -1,
                        -2);

        p.setMargins(
                0,
                7,
                0,
                10);

        l.setLayoutParams(p);

        return l;
    }

    private LinearLayout darkCard() {

        LinearLayout l =
                new LinearLayout(this);

        l.setOrientation(
                LinearLayout.VERTICAL);

        l.setPadding(
                18,
                16,
                18,
                16);

        GradientDrawable gd =
                new GradientDrawable(
                        GradientDrawable.Orientation.TL_BR,
                        new int[]{
                                Color.rgb(13, 55, 112),
                                Color.rgb(5, 30, 70)
                        });

        gd.setCornerRadius(24);

        gd.setStroke(
                1,
                Color.rgb(25, 104, 180));

        l.setBackground(gd);

        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(
                        -1,
                        -2);

        p.setMargins(
                0,
                5,
                0,
                10);

        l.setLayoutParams(p);

        return l;
    }

    private View optionCard(
            String icon,
            String title,
            String subtitle,
            int accent) {

        LinearLayout box =
                whiteCard();

        box.setOrientation(
                LinearLayout.HORIZONTAL);

        box.setGravity(
                Gravity.CENTER_VERTICAL);

        TextView i =
                tv(
                        icon,
                        28,
                        accent,
                        true);

        i.setGravity(
                Gravity.CENTER);

        box.addView(
                i,
                new LinearLayout.LayoutParams(
                        58,
                        58));

        LinearLayout names =
                new LinearLayout(this);

        names.setOrientation(
                LinearLayout.VERTICAL);

        names.addView(
                tv(
                        title,
                        16,
                        TEXT,
                        true));

        names.addView(
                tv(
                        subtitle,
                        12,
                        MUTED,
                        false));

        box.addView(
                names,
                new LinearLayout.LayoutParams(
                        0,
                        -2,
                        1));

        box.addView(
                tv(
                        "›",
                        30,
                        MUTED,
                        false));

        return box;
    }

    private View setting(
            String icon,
            String title,
            String subtitle,
            final Runnable action) {

        LinearLayout box =
                whiteCard();

        box.setOrientation(
                LinearLayout.HORIZONTAL);

        box.setGravity(
                Gravity.CENTER_VERTICAL);

        TextView i =
                tv(
                        icon,
                        23,
                        BLUE,
                        true);

        i.setGravity(
                Gravity.CENTER);

        box.addView(
                i,
                new LinearLayout.LayoutParams(
                        50,
                        55));

        LinearLayout names =
                new LinearLayout(this);

        names.setOrientation(
                LinearLayout.VERTICAL);

        names.addView(
                tv(
                        title,
                        15,
                        TEXT,
                        true));

        names.addView(
                tv(
                        subtitle,
                        11,
                        MUTED,
                        false));

        box.addView(
                names,
                new LinearLayout.LayoutParams(
                        0,
                        -2,
                        1));

        box.addView(
                tv(
                        "›",
                        27,
                        MUTED,
                        false));

        box.setOnClickListener(
                v -> action.run());

        return box;
    }

    private View roundAction(
            String icon,
            String label,
            int color,
            final Runnable action) {

        LinearLayout box =
                new LinearLayout(this);

        box.setOrientation(
                LinearLayout.VERTICAL);

        box.setGravity(
                Gravity.CENTER);

        box.setPadding(
                2,
                5,
                2,
                5);

        TextView i =
                tv(
                        icon,
                        24,
                        WHITE,
                        true);

        i.setGravity(
                Gravity.CENTER);

        GradientDrawable gd =
                new GradientDrawable();

        gd.setColor(color);
        gd.setShape(
                GradientDrawable.OVAL);

        i.setBackground(gd);

        box.addView(
                i,
                new LinearLayout.LayoutParams(
                        56,
                        56));

        box.addView(
                tv(
                        label,
                        11,
                        WHITE,
                        true));

        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(
                        0,
                        86,
                        1);

        box.setLayoutParams(p);

        box.setOnClickListener(
                v -> action.run());

        return box;
    }

    private View txRow(Tx tx) {

        LinearLayout row =
                whiteCard();

        row.setOrientation(
                LinearLayout.HORIZONTAL);

        row.setGravity(
                Gravity.CENTER_VERTICAL);

        TextView icon =
                tv(
                        tx.type.equals("Received")
                                ? "↓"
                                : tx.type.equals("Sent")
                                ? "↑"
                                : "⇄",
                        24,
                        WHITE,
                        true);

        icon.setGravity(
                Gravity.CENTER);

        GradientDrawable gd =
                new GradientDrawable();

        gd.setColor(tx.color);

        gd.setShape(
                GradientDrawable.OVAL);

        icon.setBackground(gd);

        row.addView(
                icon,
                new LinearLayout.LayoutParams(
                        48,
                        48));

        LinearLayout names =
                new LinearLayout(this);

        names.setOrientation(
                LinearLayout.VERTICAL);

        names.addView(
                tv(
                        tx.type,
                        15,
                        TEXT,
                        true));

        names.addView(
                tv(
                        tx.detail +
                                "\nSep 24, 09:20",
                        11,
                        MUTED,
                        false));

        row.addView(
                names,
                new LinearLayout.LayoutParams(
                        0,
                        -2,
                        1));

        row.addView(
                tv(
                        tx.amount,
                        14,
                        tx.color,
                        true));

        return row;
    }

    private View infoLine(String text) {

        TextView v =
                tv(
                        text,
                        12,
                        MUTED,
                        false);

        v.setBackgroundColor(
                Color.rgb(
                        237,
                        244,
                        250));

        v.setPadding(
                12,
                10,
                12,
                10);

        return v;
    }

    private Spinner spinner(
            String[] values) {

        Spinner s =
                new Spinner(this);

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout
                                .simple_spinner_dropdown_item,
                        values);

        s.setAdapter(adapter);

        return s;
    }

    // ============================================================
    // IMAGE / UTILITIES
    // ============================================================

    private void setImageByName(
            ImageView image,
            String name) {

        int id =
                getResources().getIdentifier(
                        name,
                        "drawable",
                        getPackageName());

        if (id != 0) {

            image.setImageResource(id);

        } else {

            image.setImageDrawable(
                    circleDrawable(
                            name.equals("ron_logo")
                                    ? BLUE
                                    : CYAN,
                            48));
        }
    }

    private android.graphics.drawable.Drawable
    circleDrawable(
            int color,
            int size) {

        GradientDrawable gd =
                new GradientDrawable();

        gd.setColor(color);

        gd.setShape(
                GradientDrawable.OVAL);

        return gd;
    }

    private void copyText(
            String value) {

        ClipboardManager cm =
                (ClipboardManager)
                        getSystemService(
                                Context.CLIPBOARD_SERVICE);

        cm.setPrimaryClip(
                ClipData.newPlainText(
                        "QORION",
                        value));

        toast(
                "Copied to clipboard.");
    }

    private double number(
            String value) {

        try {

            return Double.parseDouble(
                    value
                            .replace(",", "")
                            .trim());

        } catch (Exception e) {

            return 0;
        }
    }

    private String shortAddress(
            String a) {

        if (a.length() <= 12)
            return a;

        return a.substring(0, 7)
                + "..."
                + a.substring(
                        a.length() - 5);
    }

    private void toast(
            String message) {

        Toast.makeText(
                this,
                message,
                Toast.LENGTH_SHORT)
                .show();
    }

    // ============================================================
    // SIMPLE CHART
    // ============================================================

    private static class MiniChart
            extends View {

        private final Paint p =
                new Paint(
                        Paint.ANTI_ALIAS_FLAG);

        MiniChart(Context c) {

            super(c);

            p.setStrokeWidth(4);

            p.setStyle(
                    Paint.Style.STROKE);

            p.setColor(
                    Color.rgb(
                            0,
                            145,
                            255));
        }

        @Override
        protected void onDraw(
                Canvas c) {

            super.onDraw(c);

            float w =
                    getWidth();

            float h =
                    getHeight();

            float[] points = {
                    0.78f,
                    0.62f,
                    0.69f,
                    0.43f,
                    0.55f,
                    0.34f,
                    0.49f,
                    0.25f,
                    0.38f,
                    0.18f,
                    0.29f,
                    0.12f
            };

            for (
                    int i = 0;
                    i < points.length - 1;
                    i++) {

                float x1 =
                        i *
                        (w /
                        (points.length - 1));

                float x2 =
                        (i + 1) *
                        (w /
                        (points.length - 1));

                float y1 =
                        points[i] * h;

                float y2 =
                        points[i + 1] * h;

                c.drawLine(
                        x1,
                        y1,
                        x2,
                        y2,
                        p);
            }
        }
    }

    // ============================================================
    // ANDROID LIFECYCLE
    // ============================================================

    @Override
    public void onBackPressed() {
        showHome();
    }
    }
    
