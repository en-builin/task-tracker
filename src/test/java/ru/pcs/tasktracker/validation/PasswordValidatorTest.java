package ru.pcs.tasktracker.validation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Evgeniy Builin (en.builin@gmail.com)
 * Created on 24.11.2021 in project task-tracker
 */
@SpringBootTest
@DisplayName("PasswordValidator is working when")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PasswordValidatorTest {

    @Autowired
    private PasswordValidator passwordValidator;

    @ParameterizedTest(name = "#{index} - Run test with password = {0}")
    @MethodSource("validPasswordProvider")
    void password_valid(String password) {
        assertTrue(passwordValidator.isValid(password, null));
    }

    @ParameterizedTest(name = "#{index} - Run test with password = {0}")
    @MethodSource("invalidPasswordProvider")
    void password_invalid(String password) {
        assertFalse(passwordValidator.isValid(password, null));
    }

    static Stream<String> validPasswordProvider() {
        return Stream.of(
                "AAABBBCCC123",
                "aaabbbccc123",
                "aaaBBBccc123",
                "Hello world123",
                "A!@#&()â€a1",
                "0123456789$abcdefgAB",     // test 20 chars
                "123Aa$Aa"                  // test 8 chars
        );
    }

    static Stream<String> invalidPasswordProvider() {
        return Stream.of(
                "12345678",                 // invalid, only digit
                "abcdefgh",                 // invalid, only lowercase
                "ABCDEFGH",                 // invalid, only uppercase
                "abcDEFgh",                 // invalid, at least one digit
                "123$$$$$",                 // invalid, at least one lowercase
                "________",                 // invalid
                "--------",                 // invalid
                " ",                        // empty
                "",                         // empty
                "abcdefg12345abcdefg1234"); // more than 20 characters
    }

}