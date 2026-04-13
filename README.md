# Venu Silk Mills

Android business management application for textile/silk mill operations. Built for managing orders, dispatch, stock, ledger, and billing workflows.

## Features

- **Order Management** - Create, view, and track sale orders and job orders
- **Dispatch** - Manage dispatch entries with barcode/QR scanning support
- **Stock** - Real-time stock viewing with barcode scanning
- **Ledger & Billing** - View ledger entries, sale bills, and outstanding reports
- **Dispatch List** - Track dispatched items with challan viewing
- **PDF Reports** - Generate and share PDF reports for bills, outstanding, and stock
- **OTP Authentication** - Secure login with email-based OTP verification
- **Push Notifications** - GCM-based push notifications
- **Role-Based Access** - Admin, Manager, Party, Broker, Salesperson roles with tailored menus

## Tech Stack

- **Language:** Java
- **Min SDK:** 21 (Android 5.0)
- **Target SDK:** 34 (Android 14)
- **Architecture:** Activity/Fragment based
- **Networking:** Retrofit 2.9.0
- **Image Loading:** Picasso
- **Barcode/QR:** ZXing + ZXing Android Embedded
- **PDF Viewer:** android-pdf-viewer
- **UI:** Material Design, RecyclerView, SwipeRefreshLayout, Navigation Drawer
- **Database:** SQLite (local), SQL Server (remote API)
- **Build System:** Gradle 8.7, AGP 8.3.2, Java 17

## Setup

### Prerequisites

- Android Studio (Arctic Fox or later)
- JDK 17 (Android Studio bundled JBR recommended)
- Android SDK 34

### Configuration

1. Clone the repository:
   ```bash
   git clone https://github.com/bsudani14/venu-silk-mills.git
   ```

2. Copy the secrets template and fill in your values:
   ```bash
   cp secrets.properties.example secrets.properties
   ```

3. Edit `secrets.properties` with your configuration:
   ```properties
   API_BASE_URL=http://your-api-server.com/
   OTP_CONFIG_URL=http://your-api-server.com/config/otp.txt
   REPORT_SERVER_IP=your.server.ip
   ADMIN_DB_NAME=yourdbname
   ADMIN_DISPLAY_NAME=Admin
   ADMIN_EMAIL=admin@example.com
   SMTP_HOST=smtp.gmail.com
   ```

4. Open the project in Android Studio and sync Gradle.

5. Build and run:
   ```bash
   ./gradlew installDebug
   ```

### Secrets Management

All sensitive configuration (API URLs, server IPs, credentials) is loaded from `secrets.properties` at build time via `BuildConfig` fields. This file is **gitignored** and never committed.

See `secrets.properties.example` for the required keys.

## Project Structure

```
app/src/main/java/com/venusilkmills/app/
├── Activity/          # Feature screens (Dispatch, Stock, Orders, Reports)
├── Adapter/           # RecyclerView and ListView adapters
├── Database/          # SQLite helper
├── FCM/               # Push notification handling (GCM)
├── Fragment/          # UI fragments (Ledger, Bills, Outstanding, Party list)
├── json/              # Retrofit API client and interface
├── library/           # Utility classes
├── Model/             # Data models / POJOs
├── util/              # Helpers (connectivity, fonts, UI utilities)
├── LoginActivity.java
├── MainActivity.java
├── Otpverify.java
└── Splash_Screen.java
```

## API Endpoints

The app communicates with a REST API backend (configured via `secrets.properties`). Key features include:

| Module | Purpose |
|--------|---------|
| OTP | Login / OTP generation & verification |
| Orders | Pending orders, order scheduling, party orders |
| Dispatch | Dispatch entries and auto-dispatch |
| Stock | Barcode scanning and stock management |
| Ledger | Company list, cost centers, book/ledger data |
| Reports | Sale bills, outstanding, stock reports (PDF) |
| Party | Party data, opening balance, address management |

## Build

```bash
# Debug build
./gradlew assembleDebug

# Install on connected device
./gradlew installDebug

# Release build
./gradlew assembleRelease
```

## License

Private - All rights reserved.