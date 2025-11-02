Advanced Calculator & Converter App
A comprehensive Android calculator application that combines arithmetic calculations with advanced base conversion capabilities, featuring a professional UI and history tracking.

🚀 Features
🔢 Arithmetic Calculator
Basic operations: Addition (+), Subtraction (-), Multiplication (×), Division (÷)

Decimal and fraction calculations

Support for complex expressions with operator precedence

Real-time input validation

🔄 Base Converter
Supported Bases: Binary (2), Octal (8), Decimal (10), Hexadecimal (16)

Conversion Types:

Binary ↔ Decimal

Hexadecimal ↔ Octal

Octal ↔ Binary

Hexadecimal ↔ Binary/Decimal

And all other combinations

🧮 Advanced Base Arithmetic
Perform arithmetic operations in any base and get results in any other base

Examples:

24+30 in Decimal to Binary = 110110

1010*11 in Binary to Hexadecimal = 1E

A-5 in Hexadecimal to Octal = 5

📱 User Interface
Three Tab Layout:

Calculator: Main calculation interface

History: Track all previous calculations

Info: App information and instructions

Toggle Keyboards:

Number Keyboard (0-9, arithmetic operators)

Letter Keyboard (A-F for hexadecimal, DEL, CLEAR)

Professional Design:

Material Design components

Color-coded buttons

Rounded corners and modern styling

Responsive layout

📊 History Management
Automatic saving of all calculations

Persistent storage using SQLite database

Clear history functionality

Detailed view with timestamps

🛠️ Technical Specifications
Architecture
Language: Java

Minimum SDK: API 21 (Android 5.0)

Architecture: MVC Pattern

Database: SQLite with Room-like wrapper

Components
MainActivity: Hosts ViewPager2 with TabLayout

CalculatorFragment: Main calculation logic and UI

HistoryFragment: History display and management

InfoFragment: App information

DatabaseHelper: SQLite database operations

CalculationHistory: Data model class

Key Classes
CalculatorFragment: Handles all calculation logic

DatabaseHelper: Manages local database operations

HistoryAdapter: RecyclerView adapter for history list

ViewPagerAdapter: Manages tab navigation

📥 Installation
Clone the repository:

bash
git clone https://github.com/yourusername/calculator-app.git
Open in Android Studio:

Open Android Studio

Select "Open an existing project"

Navigate to the cloned directory

Build and Run:

Connect an Android device or start an emulator

Click "Run" or press Shift+F10

🎯 Usage

Basic Arithmetic
Select "Arithmetic" mode

Use number keyboard to input numbers

Use operator buttons (+, -, ×, ÷)

Press "=" to calculate

Base Conversion
Select "Base Converter" mode

Choose input base from "From" dropdown

Choose output base from "To" dropdown

Enter number (valid for selected input base)

Press "=" to convert

Base Arithmetic
Select "Base Converter" mode

Choose input and output bases

Enter arithmetic expression using valid characters for input base

Press "=" to calculate and convert result

History Management
Navigate to "History" tab

View all previous calculations

Use "Clear History" button to remove all records

🎨 UI Components
Input Fields
Main Input: Professional styled EditText with blue border

Result Display: Green background for clear visibility

Buttons
Number Buttons: White background with gray border

Operator Buttons: Blue background

Function Buttons:

Equals: Green

Delete: Red

Clear: Orange

Letter Buttons: Light blue background for hexadecimal input

Keyboard Layout
text
Number Keyboard:
[7] [8] [9] [+]
[4] [5] [6] [-]
[1] [2] [3] [×]
[0] [.] [÷] [=]

Letter Keyboard:
[A] [B] [C] [D]
[E] [F] [DEL] [C]
🔧 Customization
Adding New Bases
Update arrays.xml with new base options

Modify getBaseFromSpinner() method

Add conversion logic in convertDecimalToBase()

Styling Changes
Modify drawable files in res/drawable/

Update colors in res/values/colors.xml

Adjust dimensions in res/values/dimens.xml

Database Modifications
Update DatabaseHelper.java for schema changes

Modify CalculationHistory.java model class

📊 Performance
Memory Efficient: Uses RecyclerView for history list

Fast Calculations: Optimized arithmetic algorithms

Smooth UI: Main thread operations kept minimal

Efficient Storage: SQLite with proper indexing

🐛 Troubleshooting
Common Issues
Invalid Input Errors: Ensure input matches selected base

Calculation Errors: Check for division by zero

History Not Saving: Verify database permissions

Solutions
Clear app data if persistent issues occur

Check AndroidManifest.xml for storage permissions

Verify SQLite database path and permissions

🤝 Contributing
Fork the repository

Create a feature branch

Commit your changes

Push to the branch

Create a Pull Request

📄 License
This project is licensed under the MIT License - see the LICENSE.md file for details.

🙏 Acknowledgments
Material Design Components

Android Studio development tools

SQLite database system
