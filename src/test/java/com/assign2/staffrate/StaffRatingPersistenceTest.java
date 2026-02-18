package com.assign2.staffrate;

import com.assign2.staffrate.model.StaffRating;
import com.assign2.staffrate.model.StaffRoleType;
import com.assign2.staffrate.repo.StaffRatingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StaffRatingPersistenceTest {

    @Autowired
    StaffRatingRepository repo;

    private StaffRating make() {
        StaffRating r = new StaffRating();
        r.setName("Sam");
        r.setEmail("sam" + System.nanoTime() + "@test.com");
        r.setRoleType(StaffRoleType.PROF);
        r.setClarity(6);
        r.setNiceness(7);
        r.setKnowledgeableScore(8);
        r.setComment("ok");
        return r;
    }

    @Test
    void savingAndRetrievingWorks() {
        StaffRating saved = repo.save(make());
        assertNotNull(saved.getId());

        StaffRating found = repo.findById(saved.getId()).orElseThrow();
        assertEquals(saved.getEmail(), found.getEmail());
    }

    @Test
    void deleteRemovesEntry() {
        StaffRating saved = repo.save(make());
        Long id = saved.getId();

        repo.deleteById(id);
        assertTrue(repo.findById(id).isEmpty());
    }
}
