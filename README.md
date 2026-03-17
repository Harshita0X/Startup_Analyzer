# Starthub - AI Startup Strategy Lab 🚀

**Starthub** is a high-fidelity, AI-powered desktop application designed to help visionaries and entrepreneurs transform raw ideas into comprehensive, investor-ready startup strategies. Featuring a premium, flashy UI with glassmorphism effects, it provides deep analytical insights including SWOT analysis, audience archetypes, and feasibility calculations.

![Starthub UI](https://img.shields.io/badge/UI-Glassmorphism-blueviolet)
![Java](https://img.shields.io/badge/Language-Java_17-orange)
![MySQL](https://img.shields.io/badge/Database-MySQL-blue)
![FlatLaf](https://img.shields.io/badge/Theme-FlatLaf_Dark-darkgreen)

---

## ✨ Key Features

### 1. Intelligent Strategy Lab
Describe your business concept in plain language, and our analysis engine will construct a multi-dimensional strategy:
- **Problem Hypothesis**: Defines the core pain point being addressed.
- **Solution Paradigm**: Outlines the unique approach and USP.
- **Audience Archetypes**: Generates detailed demographic and psychographic profiles.
- **Strategic SWOT Matrix**: Automated Strengths, Weaknesses, Opportunities, and Threats analysis.

### 2. Feasibility Calculator 📊
Input your **Budget**, **Team Size**, and **Timeframe** to get a realistic feasibility score. The system analyzes resource allocation against industry standards to help you understand if your vision is executable.

### 3. Side-by-Side Idea Comparison ⚖️
Can't decide between two ventures? Use the comparison tool to contrast innovation scores, market demand, and SWOT profiles side-by-side.

### 4. Market Trending & Inspiration 🔥
Stay ahead of the curve with a dynamic dashboard showing trending opportunities in sectors like Crypto Banking, Ethical AI, and Greener Logistics.

### 5. Persistent Strategy Lab (MySQL) 💾
All your ideas and reports are securely stored in a MySQL database. Save, revisist, or discard strategies as your vision evolves.

---

## 🎨 Design Philosophy
The application follows a **"Fedric AI"** inspired aesthetic:
- **Glassmorphism**: Semi-transparent panels with interactive hover effects.
- **Premium Typography**: Integration of the "Outfit" font family for a modern feel.
- **Vibrant Visuals**: Custom-generated abstract gradients and high-fidelity trading card thumbnails.

---

## 🛠️ Tech Stack
- **Core**: Java 17
- **GUI Framework**: Java Swing + [FlatLaf](https://github.com/JFormDesigner/FlatLaf)
- **Database**: MySQL (JDBC)
- **Dependency Management**: Maven

---

## 🚀 Getting Started

### Prerequisites
- JDK 17 or higher
- MySQL Server 8.0+
- Maven

### Database Setup
1. Create a database named `startup_analyzer`.
2. Ensure you have a user with appropriate permissions (default configuration uses `root` / `harshita`).
3. The application will automatically create the required `users` and `startup_ideas` tables upon first run.

### Running the Application
```bash
# Clone the repository
git clone https://github.com/Harshita0X/Startup_Analyzer.git

# Navigate to the project directory
cd Startup_Analyzer

# Build the project
mvn clean package

# Run the executable JAR
java -jar target/idea-analyzer-1.0-SNAPSHOT.jar
```

---

## 📄 License
This project is for educational purposes as part of a student project. Feel free to use and modify for your own learning!

---
*Built with ❤️ for the next generation of visionaries.*
