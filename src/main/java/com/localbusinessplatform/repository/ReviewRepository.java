package com.localbusinessplatform.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.localbusinessplatform.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
	
	List<Review> findByreviewerUsername(String reviewer_username);
	List<Review> findByrevieweeUsername(String reviewee_username);
	
}
