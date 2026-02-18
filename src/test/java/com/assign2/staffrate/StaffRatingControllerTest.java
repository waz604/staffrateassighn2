package com.assign2.staffrate;

import com.assign2.staffrate.controller.StaffRatingController;
import com.assign2.staffrate.model.StaffRating;
import com.assign2.staffrate.model.StaffRoleType;
import com.assign2.staffrate.services.StaffRatingService;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StaffRatingControllerPlainTest {

    static class FakeService extends StaffRatingService {
        FakeService() { super(null); } 

        @Override public List<StaffRating> findAll() { return List.of(); }
        @Override public StaffRating findByIdOrThrow(Long id) { return new StaffRating(); }
        @Override public StaffRating create(StaffRating rating) { return rating; }
        @Override public StaffRating update(Long id, StaffRating updated) { return updated; }
        @Override public void delete(Long id) { /* do nothing */ }
    }

    @Test
    void listAddsRatingsAndReturnsIndexView() {
        StaffRatingController controller = new StaffRatingController(new FakeService());
        var model = new ExtendedModelMap();

        String view = controller.list(model);

        assertEquals("ratings/index", view);
        assertTrue(model.containsAttribute("ratings"));
    }

    @Test
    void createFormAddsRatingAndRolesAndReturnsFormView() {
        StaffRatingController controller = new StaffRatingController(new FakeService());
        var model = new ExtendedModelMap();

        String view = controller.createForm(model);

        assertEquals("ratings/form", view);
        assertTrue(model.containsAttribute("rating"));
        assertTrue(model.containsAttribute("roles"));

        Object roles = model.getAttribute("roles");
        assertNotNull(roles);
        assertTrue(((StaffRoleType[]) roles).length > 0);
    }

    @Test
    void createWithErrorsReturnsFormView() {
        StaffRatingController controller = new StaffRatingController(new FakeService());
        var model = new ExtendedModelMap();

        StaffRating rating = new StaffRating(); // empty = invalid
        BindingResult br = new BeanPropertyBindingResult(rating, "rating");
        br.reject("bad", "Simulated validation failure");

        String view = controller.create(rating, br, model);

        assertEquals("ratings/form", view);
        assertTrue(model.containsAttribute("roles"));
    }

    @Test
    void createSuccessRedirects() {
        StaffRatingController controller = new StaffRatingController(new FakeService());
        var model = new ExtendedModelMap();

        StaffRating rating = new StaffRating();
        rating.setName("Alex");
        rating.setEmail("alex@test.com");
        rating.setRoleType(StaffRoleType.TA);
        rating.setClarity(7);
        rating.setNiceness(8);
        rating.setKnowledgeableScore(9);

        BindingResult br = new BeanPropertyBindingResult(rating, "rating"); 

        String view = controller.create(rating, br, model);

        assertEquals("redirect:/ratings", view);
    }
}
