# Log Analyzer

A robust Java application for analyzing log files and generating detailed JSON reports.

## 🚀 Features

- Parse multiple log file formats
- Generate detailed JSON reports
- Command-line interface support
- Customizable output formats

## 📋 Prerequisites

- Java 11 or higher
- Maven 3.6.x or higher

## 🛠️ Installation

1. **Install Java**
   ```bash
   # Verify Java installation
   java --version
   javac --version
   ```

2. **Install Maven**
   ```bash
   # Using Homebrew
   brew install maven

   # Verify Maven installation
   mvn -v

   # Verify JAVA_HOME
   echo $JAVA_HOME
   ```

## 🏗️ Project Setup

1. **Create Project**
   ```bash
   mvn archetype:generate \
   -DgroupId=com.example.myproject \
   -DartifactId=MyProject \
   -DarchetypeArtifactId=maven-archetype-quickstart \
   -DinteractiveMode=false
   ```

2. **Project Structure**
   ```
   LogAnalyzer/
   ├── src/
   │   ├── main/
   │   │   └── java/
   │   └── test/
   │       └── java/
   ├── target/
   ├── pom.xml
   └── README.md
   ```

## 🔨 Build

1. **Clean and Install**
   ```bash
   mvn clean install
   ```

2. **Package Application**
   ```bash
   mvn clean package
   ```

## ▶️ Usage

1. **Run the Application**
   ```bash
   java -jar target/log-analyzer-1.0-SNAPSHOT.jar --file test.txt
   ```

2. **Command Line Options**
   ```bash
   --file      Input log file path (required)
   --output    Output directory (optional, defaults to current directory)
   --format    Output format (optional, defaults to JSON)
   ```

## 📁 Output

The analyzer generates the following files in the root directory:
- `analysis_summary.json`: Overall analysis summary
- `error_logs.json`: Detailed error log analysis
- `performance_metrics.json`: Performance-related statistics

## 🧪 Testing

Run the test suite:
```bash
mvn test
```

## 📊 Sample Output

```json
{
  "analysisDate": "2024-03-21",
  "totalLogs": 1000,
  "errorCount": 50,
  "warningCount": 100,
  "averageResponseTime": "120ms"
}
```

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request


## 👥 Authors

- RTG



### [1.0.0] - 2024-03-21
- Initial release
- Basic log parsing functionality
- JSON report generation
