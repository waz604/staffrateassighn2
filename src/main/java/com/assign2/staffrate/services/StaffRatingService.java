package com.assign2.staffrate.services;

import com.assign2.staffrate.model.StaffRating;
import com.assign2.staffrate.repo.StaffRatingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StaffRatingService {

    private final StaffRatingRepository repo;

    public StaffRatingService(StaffRatingRepository repo) {
        this.repo = repo;
    }

    public List<StaffRating> findAll() {
        return repo.findAll();
    }

    public StaffRating findByIdOrThrow(Long id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Rating not found: " + id));
    }

    @Transactional
    public StaffRating create(StaffRating rating) {
        String email = rating.getEmail();
        rating.setEmail(email);

        if (repo.existsByEmailIgnoreCase(email)) {
            throw new DuplicateEmailException();
        }
        return repo.save(rating);
    }

    @Transactional
    public StaffRating update(Long id, StaffRating updated) {
        StaffRating existing = findByIdOrThrow(id);

        String email = updated.getEmail();
        repo.findByEmailIgnoreCase(email).ifPresent(found -> {
            if (!found.getId().equals(id)) throw new DuplicateEmailException();
        });

        existing.setName(updated.getName());
        existing.setEmail(email);
        existing.setRoleType(updated.getRoleType());
        existing.setClarity(updated.getClarity());
        existing.setNiceness(updated.getNiceness());
        existing.setKnowledgeableScore(updated.getKnowledgeableScore());
        existing.setComment(updated.getComment());

        return repo.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        repo.deleteById(id);
    }


    public static class DuplicateEmailException extends RuntimeException { }
}
