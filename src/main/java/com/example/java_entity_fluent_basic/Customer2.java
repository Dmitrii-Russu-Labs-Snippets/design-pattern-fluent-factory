package com.example.java_entity_fluent_basic;

import java.util.Objects;
import org.apache.commons.lang3.Validate;      // из commons-lang3
import org.apache.commons.lang3.StringUtils;   // из commons-lang3
import org.apache.commons.validator.routines.EmailValidator;  // из commons-validator

public final class Customer2 {

    private static final EmailValidator EMAIL_VALIDATOR = EmailValidator.getInstance();

    private final String name;
    private final Integer age;
    private final String email;

    private Customer2(String name, Integer age, String email) {
        this.name = validateName(name);
        this.age = validateAge(age);
        this.email = validateEmail(email);
    }

    // Factory entry point (named) и универсальный фабричный метод
    public static Customer2 named(String name) {
        return new Customer2(name, null, null);
    }

    // Fluent методы на самом объекте
    public Customer2 withAge(Integer age) {
        return new Customer2(this.name, age, this.email);
    }

    public Customer2 withEmail(String email) {
        return new Customer2(this.name, this.age, email);
    }

    private static String validateName(String name) {
        String trimmed = Validate.notNull(name, "Name required").trim();
        Validate.isTrue(StringUtils.isNotBlank(trimmed), "Name cannot be empty");
        Validate.inclusiveBetween(2, 20, trimmed.length(), "Name must be 2-20 chars");
        Validate.isTrue(trimmed.matches("^[\\p{L}\\s\\-'.,]+$"), "Invalid characters in name");
        return trimmed;
    }

    private static Integer validateAge(Integer age) {
        if (age != null) {
            Validate.inclusiveBetween(0, 90, age, "Age must be 0-90");
        }
        return age;
    }

    private static String validateEmail(String email) {
        if (email != null) {
            String trimmed = email.trim();
            Validate.isTrue(StringUtils.isNotBlank(trimmed), "Email cannot be empty");
            Validate.isTrue(EMAIL_VALIDATOR.isValid(trimmed), "Invalid email");
        }
        return email;
    }

    public String getName() { return name;}
    public Integer getAge() { return age;}
    public String getEmail() { return email;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer2)) return false;
        Customer2 customer = (Customer2) o;
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

