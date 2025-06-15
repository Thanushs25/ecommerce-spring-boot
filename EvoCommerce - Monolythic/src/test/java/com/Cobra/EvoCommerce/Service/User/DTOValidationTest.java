package com.Cobra.EvoCommerce.Service.User;

import com.Cobra.EvoCommerce.DTO.User.UserLoginDTO;
import com.Cobra.EvoCommerce.DTO.User.UserSignUpDTO;
import com.Cobra.EvoCommerce.Model.User.Address;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DTOValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidUserLoginDTO() {
        UserLoginDTO dto = UserLoginDTO.builder()
                .userName("john")
                .password("password123")
                .build();

        Set<ConstraintViolation<UserLoginDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidUserLoginDTO() {
        UserLoginDTO dto = UserLoginDTO.builder()
                .userName("")
                .password("short")
                .build();

        Set<ConstraintViolation<UserLoginDTO>> violations = validator.validate(dto);
        assertEquals(2, violations.size());
    }

    @Test
    void testValidUserSignUpDTO() {
        UserSignUpDTO dto = UserSignUpDTO.builder()
                .name("John Doe")
                .email("john@example.com")
                .userName("johnny")
                .password("securePass123")
                .confirmPassword("securePass123")
                .build();

        Set<ConstraintViolation<UserSignUpDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidUserSignUpDTO() {
        UserSignUpDTO dto = UserSignUpDTO.builder()
                .name("")
                .email("invalid-email")
                .userName("")
                .password("123")
                .confirmPassword("")
                .build();

        Set<ConstraintViolation<UserSignUpDTO>> violations = validator.validate(dto);
        assertEquals(5, violations.size());
    }

    @Test
    void testValidAddress() {
        Address address = Address.builder()
                .flatNumber("12A")
                .street("Main Street")
                .city("Chennai")
                .country("india")
                .pincode("600001")
                .build();

        Set<ConstraintViolation<Address>> violations = validator.validate(address);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidAddress() {
        Address address = Address.builder()
                .flatNumber("")
                .street("")
                .city("")
                .country("india")
                .pincode("123") // Invalid pin
                .build();

        Set<ConstraintViolation<Address>> violations = validator.validate(address);
        assertEquals(5, violations.size());
    }
}
