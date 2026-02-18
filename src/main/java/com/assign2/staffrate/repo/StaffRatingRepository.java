package com.assign2.staffrate.repo;
import com.assign2.staffrate.model.StaffRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StaffRatingRepository 
        extends JpaRepository<StaffRating, Long> {

    boolean existsByEmailIgnoreCase(String email);
    Optional<StaffRating> findByEmailIgnoreCase(String email);
}