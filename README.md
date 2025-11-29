# Design Pattern: Fluent Factory

Immutable object creation through fluent interface with factory methods.

## 🎯 Pattern Concept

Create immutable objects using fluent method chaining that returns new instances, eliminating the need for builders.

## 🏗️ Core Implementation

```java
public final class Customer {
    private final String name;
    private final Integer age;
    private final String email;
    
    private Customer(String name, Integer age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }
    
    // Factory entry point
    public static Customer named(String name) {
        return new Customer(name, null, null);
    }
    
    // Fluent wither methods
    public Customer withAge(Integer age) {
        return new Customer(this.name, age, this.email);
    }
    
    public Customer withEmail(String email) {
        return new Customer(this.name, this.age, email);
    }
}
```

## Usage

``` java
Customer customer = Customer.named("John Smith").withAge(30).withEmail("john@example.com");
```

## Pattern Benefits

Immutability - Thread-safe objects

Fluent API - Readable method chaining

No Builders - Direct object creation

Type Safety - Compile-time checks

## 🔄 Fluent Factory vs Builder Pattern

| Aspect | Fluent Factory | Classic Builder |
|--------|----------------|-----------------|
| **Object Return** | Each method returns new instance | Only via `.build()` method |
| **Immutability** | ✅ Built-in by design | ✅ Configurable |
| **Method Chaining** | On target object itself | On separate builder object |
| **Construction Flow** | Direct object creation | Intermediate builder step |
| **API Style** | Fluent wither methods | Fluent setter methods |

## Ideal Use Cases

- **Domain entities** with optional parameters
- **Configuration objects** requiring step-wise setup  
- **Value objects** in Domain-Driven Design
- **Any scenario** requiring immutable fluent APIs
- **When you want** to eliminate `.build()` calls
