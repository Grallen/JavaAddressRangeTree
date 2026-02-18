# IP Address Range Tree

A high-performance Java implementation of a **Balanced Binary Search Tree (BST)** designed for lightning-fast IP-to-Country (GeoIP) lookups.

This utility is optimized for scenarios where you need to map an IPv4 address to a metadata attribute (like a Country Code) based on non-overlapping IP ranges, commonly found in CSV datasets used by Nginx or gRPC services.

## 🚀 Key Features

* **Logarithmic Search Time:** Guarantees  lookup complexity by constructing a perfectly balanced tree.
* **Memory Efficient:** Uses static inner classes to minimize object overhead and utilizes primitives (`long`) for address storage.
* **Edge-Case Safe:** Specifically handles the "unsigned integer" problem in Java. By converting IPv4 addresses to 64-bit `long` types, it avoids the signed-integer overflow that causes comparison logic to fail on addresses above `127.255.255.255`.
* **Thread-Safe:** Once the tree is built, it is immutable, making it safe for high-concurrency environments like web servers.

### 🛠️ Technical Implementation

**Hybrid Range Parsing**
The engine automatically detects the input format per record, allowing for mixed datasets:

* **Start/End Pairs:** Traditional three-column format (`Start IP`, `End IP`, `Metadata`).
* **CIDR Notation:** Supports both two and three-column formats (e.g., `1.2.3.0/24`). The engine calculates the range boundary using bitwise masks: `start | ((1L << (32 - prefix)) - 1)`.

**Robustness & Safety**

* **Fault Tolerance:** Malformed rows (invalid IP strings or out-of-bounds CIDR prefixes) are caught and reported to `System.err` without halting tree construction.
* **Sanitization:** Automatic `.trim()` handling to prevent `NumberFormatException` from leading/trailing whitespace in CSV data.
* **The "Unsigned" Solution:** Java lacks unsigned 32-bit integers. By converting to 64-bit `long` primitives, this implementation avoids the common overflow bug that causes IP comparisons to fail on addresses above `127.255.255.255`.

## 📈 Performance Characteristics

| Operation | Complexity | Note |
| --- | --- | --- |
| **Build Tree** |  | Primarily driven by the initial sort. |
| **Lookup** |  | Roughly 20 comparisons for 1 million ranges. |
| **Space** |  | One `Node` object per range. |

## 📦 Usage

### Input Format

The `AddressTree` constructor is designed to be agnostic of the row format:

```java
String[][] data = {
    {"1.0.0.0", "1.0.0.255", "AU"}, // Start/End format
    {"8.8.8.0/24", "US"},           // CIDR format (2-column)
    {"151.101.0.0/16", "", "US"}    // CIDR format (3-column)
};

AddressTree tree = new AddressTree(data);
System.out.println(tree.find("8.8.8.8")); // US

```

### Running Included Tests:

```
> javac Main.java AddressTree
> java Main
```

## 🏗️ Requirements

* **JDK 8** or higher (uses `Comparator.comparingLong`).
* No external dependencies.
