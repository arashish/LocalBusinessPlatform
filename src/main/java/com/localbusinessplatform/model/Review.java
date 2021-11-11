package com.localbusinessplatform.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="review_id")
	private long reviewId;

	@Column(name="reviewer_username")
	private String reviewerUsername;
	
	@Column(name="reviewee_username")
	private String revieweeUsername;
	
	private String comment;
	
	@Column(name="rating_value")
	private int ratingValue;
	
	@Column(name="review_date")
	private String reviewDate;

	public long getReviewId() {
		return reviewId;
	}

	public void setReviewId(long reviewId) {
		this.reviewId = reviewId;
	}

	public String getReviewerUsername() {
		return reviewerUsername;
	}

	public void setReviewerUsername(String reviewerUsername) {
		this.reviewerUsername = reviewerUsername;
	}

	public String getRevieweeUsername() {
		return revieweeUsername;
	}

	public void setRevieweeUsername(String revieweeUsername) {
		this.revieweeUsername = revieweeUsername;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getRatingValue() {
		return ratingValue;
	}

	public void setRatingValue(int ratingValue) {
		this.ratingValue = ratingValue;
	}

	public String getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(String reviewDate) {
		this.reviewDate = reviewDate;
	}
		
}
