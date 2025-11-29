package com.example.java_entity_fluent_basic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    @Test
    void shouldCreateCustomerWithName() {

        Customer customer = Customer.named("jack");

        assertNotNull(customer);
        assertEquals("jack", customer.getName());
        assertEquals(null, customer.getAge()); // возраст по умолчанию
        assertNull(customer.getEmail()); // email по умолчанию
    }

    @Test
    void shouldCreateCustomerWithAllProperties() {

        Customer customer = Customer.named("jack").withAge(30).withEmail("jack@google.com");

        assertEquals("jack", customer.getName());
        assertEquals(30, customer.getAge());
        assertEquals("jack@google.com", customer.getEmail());
    }

    @Test
    void shouldCreateNewInstanceWhenModifying() {

        Customer original = Customer.named("Original");
        Customer modified = original.withAge(25);

        assertNotSame(original, modified);
        assertEquals("Original", modified.getName());
        assertEquals(25, modified.getAge());
        assertEquals(null, original.getAge()); // оригинал не изменился
    }

    // Этот тест НЕ написан, но он бы провалился:
    @Test
    void shouldNotBeEqualWhenDifferentAges() {
        Customer2 customer1 = Customer2.named("John").withAge(25);
        Customer2 customer2 = Customer2.named("John").withAge(30);

        // ОЖИДАНИЕ: разные объекты
        // РЕАЛЬНОСТЬ: equals() вернет true!
        assertNotEquals(customer1, customer2); // ❌ ПРОВАЛ!
    }

    @Test
    void shouldNotBeEqualWhenDifferentEmails() {
        Customer2 customer1 = Customer2.named("John").withEmail("a@test.com");
        Customer2 customer2 = Customer2.named("John").withEmail("b@test.com");

        assertNotEquals(customer1, customer2); // ❌ ПРОВАЛ!
    }

    @Test
    void shouldHandleNullEmailGracefully() {

        Customer customer = Customer.named("John").withEmail(null);

        assertNotNull(customer);
        assertNull(customer.getEmail());
    }

    @Test
    void shouldHandleEmptyEmail() {

        assertThatThrownBy(() -> Customer.named("jack").withAge(10).withEmail(" "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Email cannot be empty");
    }

    @Test
    void shouldHandleInvalidEmailFormat() {

        assertThatThrownBy(() -> Customer.named("john").withEmail("invalid-email"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid email");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 25, 90})
    void shouldAcceptValidAge(int validAge) {

        assertDoesNotThrow(() -> Customer.named("John").withAge(validAge));
    }

    @Test
    void shouldAcceptValidAge() {
        assertDoesNotThrow(() -> Customer.named("John").withAge(0));
        assertDoesNotThrow(() -> Customer.named("John").withAge(25));
        assertDoesNotThrow(() -> Customer.named("John").withAge(90));
    }

    @Test
    void shouldRejectNameEdgeCases() {

        assertThatThrownBy(() -> Customer.named(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Name required");

        assertThatThrownBy(() -> Customer.named(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Name cannot be empty");

        assertThatThrownBy(() -> Customer.named("a"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Name must be 2-20 chars");

        assertThatThrownBy(() -> Customer.named("aaaaaaaaaaaaaaaaaaaaa"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Name must be 2-20 chars");

        assertThatThrownBy(() -> Customer.named("a$a"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid characters in name");
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -10, 91, 100})
    void shouldRejectInvalidAges(int invalidAge) {

        assertThatThrownBy(() -> Customer.named("jack").withAge(invalidAge))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Age must be 0-90");
    }

    @Test
    void shouldRejectAgeEdgeCases() {
        
        assertThatThrownBy(() -> Customer.named("jack").withAge(-1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Age must be 0-90");

        assertThatThrownBy(() -> Customer.named("jack").withAge(91))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Age must be 0-90");
    }

    @Test
    void shouldAcceptValidNamesWithSpecialCharacters() {

        assertDoesNotThrow(() -> Customer.named("John Doe")); // пробел
        assertDoesNotThrow(() -> Customer.named("Anna-Maria")); // дефис
        assertDoesNotThrow(() -> Customer.named("O'Connor")); // апостроф
        assertDoesNotThrow(() -> Customer.named("John.Doe")); // точка
    }

    @Test
    void toStringShouldContainsAllFields() {

        Customer customer = Customer.named("John").withAge(25).withEmail("john@test.com");
        String toString = customer.toString();

        assertThat(toString).contains("name='John'", "age=25", "email='john@test.com'");
    }

    @Test
    void shouldCreateCustomerWithMinimumValidName() {

        Customer customer = Customer.named("Jo"); // 2 символа - минимальная длина

        assertNotNull(customer);
        assertEquals("Jo", customer.getName());
    }

    @Test
    void shouldCreateCustomerWithMaximumValidName() {
        Customer customer = Customer.named("aaaaaaaaaaaaaaaaaaaa"); // 20 символов - максимальная длина

        assertNotNull(customer);
        assertEquals("aaaaaaaaaaaaaaaaaaaa", customer.getName());
    }

}
