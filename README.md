# IP Address Range Tree

A high-performance Java implementation of a **Balanced Binary Search Tree (BST)** designed for lightning-fast IP-to-Country (GeoIP) lookups.

This utility is optimized for scenarios where you need to map an IPv4 address to a metadata attribute (like a Country Code) based on non-overlapping IP ranges, commonly found in CSV datasets used by Nginx or gRPC services.

## 🚀 Key Features

* **Logarithmic Search Time:** Guarantees  lookup complexity by constructing a perfectly balanced tree.
* **Memory Efficient:** Uses static inner classes to minimize object overhead and utilizes primitives (`long`) for address storage.
* **Edge-Case Safe:** Specifically handles the "unsigned integer" problem in Java. By converting IPv4 addresses to 64-bit `long` types, it avoids the signed-integer overflow that causes comparison logic to fail on addresses above `127.255.255.255`.
* **Thread-Safe:** Once the tree is built, it is immutable, making it safe for high-concurrency environments like web servers.

## 🛠️ Technical Implementation

### The "Balanced" Secret

The tree is built by sorting the input ranges and recursively picking the **median** element as the parent node. This ensures the tree height is kept to a minimum ().

### Data Conversion

To ensure mathematical accuracy in a language without unsigned 32-bit integers, the conversion uses bit-shifting and masking:

```java
result = (result << 8) | (Integer.parseInt(octet) & 0xFF);

```

The `& 0xFF` mask is critical—it ensures that byte values above 127 are treated as positive values when promoted to a `long`.

## 📈 Performance Characteristics

| Operation | Complexity | Note |
| --- | --- | --- |
| **Build Tree** |  | Primarily driven by the initial sort. |
| **Lookup** |  | Roughly 20 comparisons for 1 million ranges. |
| **Space** |  | One `Node` object per range. |

## 📦 Usage

### Input Format

The constructor expects a 2D String array where each row represents:
`[ "Start IP", "End IP", "Metadata/Country Code" ]`

### Example

```java
String[][] data = {
    {"8.8.8.0", "8.8.8.255", "US"},
    {"210.0.0.0", "210.255.255.255", "KR"}
};

AddressTree tree = new AddressTree(data);
String country = tree.find("8.8.8.8"); // Returns "US"

```

### Running Included Tests:

```
> javac Main.java AddressTree
> java Main
```

## 🏗️ Requirements

* **JDK 8** or higher (uses `Comparator.comparingLong`).
* No external dependencies.
