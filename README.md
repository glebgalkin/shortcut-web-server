# 🚀 Shortcut Web Server
*A fast, multithreaded HTTP server built in Java*

[![Java](https://img.shields.io/badge/Java-21%2B-blue)](https://www.oracle.com/java/)  
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

---

## 🌟 Overview
Shortcut Web Server is a lightweight, **high-performance HTTP server** written in Java.  
It supports **multithreading, custom logging, and dynamic request handling**—perfect for building microservices, proxies, or learning server internals.

### Demonstration
![Shortcut Web Server Demo](assets/demonstration.gif)

### 🚀 Key Features
- ✅ **Multithreaded Request Handling** (Custom Named Threads 🏆)
- ✅ **Supports Custom Request Handlers**
- ✅ **Configurable via `config.properties`**
- ✅ **Configurable Network Binding** (Supports localhost or external access via `0.0.0.0`)
- ✅ **Log Request Processing with Named Threads (Napoleon Bonaparte? 😆)**

---

## 🛠️ Installation & Setup

### **1️⃣ Clone the Repository**
```sh
git clone https://github.com/yourusername/shortcut-web-server.git
cd shortcut-web-server
```

### **2️⃣ Build & Run**
#### **With Gradle**
```sh
./gradlew build
./gradlew run
```
#### **Or Run Directly**
```sh
java -jar build/libs/shortcut-web-server.jar
```

---

## 🎯 Usage

### **Start the Server**
Run the application:
```sh
java -jar build/libs/shortcut-web-server.jar
```
Server starts at:
```
🚀 Shortcut Web Server started at: http://localhost:8080
```

### **Make a GET Request**
```sh
curl -X GET http://localhost:8080/
```
✔ **Server Logs the Request**
```
🔥 Julius Caesar is handling a client request!
```

---

## ⚙️ Configuration
Modify `config.properties` to customize server settings:

```ini
server.port=8080
server.threads=10
logging.level=INFO
```

You can also modify **named threads** inside `NamedThreadFactory.java`:

```java
private static final List<String> THREAD_NAMES = List.of(
    "Napoleon Bonaparte", "Julius Caesar", "Genghis Khan",
    "The Terminator", "Darth Vader", "Sherlock Holmes"
);
```

---

## 🏗️ Contributing
🔥 PRs are welcome! To contribute:
1. **Fork the repo**
2. **Create a feature branch**
3. **Commit your changes**
4. **Submit a pull request**

---

## 📜 License
Licensed under the [MIT License](LICENSE).

---

## 🚀 Future Enhancements
- [ ] 🔥 **HTTP/2 Support**
- [ ] 📂 **POST Request Handling**
- [ ] 🛠 **JSON File Processing**

---

### **💡 Have a Feature Request?**
Open an [issue](https://github.com/yourusername/shortcut-web-server/issues) or reach out! 🚀

