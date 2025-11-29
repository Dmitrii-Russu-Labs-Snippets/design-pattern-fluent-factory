package com.example.java_entity_fluent_basic;

import java.util.Objects;
import org.apache.commons.validator.routines.EmailValidator;

public final class Customer {

    private final String name;
    private final Integer age;
    private final String email;

    private Customer(String name, Integer age, String email) {
        this.name = validateName(name);
        this.age = validateAge(age);
        this.email = validateEmail(email);
    }

    // Factory entry point (named) и универсальный фабричный метод
    public static Customer named(String name) {
        return new Customer(name, null, null);
    }

    // Fluent методы на самом объекте
    public Customer withAge(Integer age) {
        return new Customer(this.name, age, this.email);
    }

    public Customer withEmail(String email) {
        return new Customer(this.name, this.age, email);
    }

    private static void validate(boolean condition, String message) {
        if (!condition) throw new IllegalArgumentException(message);
    }

    private static String validateName(String name) {
        String trimmed = Objects.requireNonNull(name, "Name required").trim();
        validate(!trimmed.isEmpty(), "Name cannot be empty");
        validate(trimmed.length() >= 2 && trimmed.length() <= 20, "Name must be 2-20 chars");
        validate(trimmed.matches("^[\\p{L}\\s\\-'.,]+$"), "Invalid characters in name");
        return trimmed;
    }

    private static Integer validateAge(Integer age) {
        if(age != null) validate(age >= 0 && age <= 90, "Age must be 0-90");
        return age;
    }

    private static String validateEmail(String email) {
        if (email == null) return null;
        String trimmed = email.trim();
        validate(!trimmed.isEmpty(), "Email cannot be empty");
        validate(EmailValidator.getInstance().isValid(trimmed), "Invalid email");
        return trimmed;
    }

    public String getName() { return name;}
    public Integer getAge() { return age;}
    public String getEmail() { return email;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return Objects.equals(name, customer.name) &&
                Objects.equals(age, customer.age) &&
                Objects.equals(email, customer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, email);
    }

    public String toString() {
        return String.format("Customer{name='%s', age=%s, email='%s'}", name, age, email);
    }

}
