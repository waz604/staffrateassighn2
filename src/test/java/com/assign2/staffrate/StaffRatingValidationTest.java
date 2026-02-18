package com.assign2.staffrate;

import com.assign2.staffrate.model.StaffRating;
import com.assign2.staffrate.model.StaffRoleType;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StaffRatingValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private StaffRating valid() {
        StaffRating r = new StaffRating();
        r.setName("Alex");
        r.setEmail("alex@test.com");
        r.setRoleType(StaffRoleType.TA);
        r.setClarity(5);
        r.setNiceness(5);
        r.setKnowledgeableScore(5);
        r.setComment("ok");
        return r;
    }

    @Test
    void invalidEmailRejected() {
        StaffRating r = valid();
        r.setEmail("not-an-email");

        var violations = validator.validate(r);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    void outOfRangeScoreRejected() {
        StaffRating r = valid();
        r.setClarity(11);

        var violations = validator.validate(r);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("clarity")));
    }

    @Test
    void missingRequiredFieldsRejected() {
        StaffRating r = new StaffRating();
        var violations = validator.validate(r);

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("roleType")));
    }
}
