package com.example.hms.Controllers;

import com.example.hms.Entities.Feedback;
import com.example.hms.Entities.FeedbackDTO;
import com.example.hms.Services.FeedbackService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "https://book-ease-hms-frontend.vercel.app")
@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/giveFeedback/{guestID}")
    public ResponseEntity<?> giveFeedback(@PathVariable Long guestID, @RequestBody FeedbackDTO feedbackDTO) {
        try {
            Feedback createdFeedback = feedbackService.giveFeedback(guestID, feedbackDTO.getFeedbackContent());
            return ResponseEntity.ok(createdFeedback);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error saving feedback: " + e.getMessage());
        }
    }

    @DeleteMapping("/deleteFeedback/{feedbackId}")
    public ResponseEntity<?> deleteFeedback(@PathVariable Long feedbackId) {
        try {
            feedbackService.deleteFeedback(feedbackId);
            return ResponseEntity.ok().body("Feedback Deleted Successfully.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error deleting feedback: " + e.getMessage());
        }
    }

    @GetMapping("/getAllFeedbacks")
    public ResponseEntity<?> getAllFeedback() {
        try {
            List<Feedback> feedbacks = feedbackService.getAllFeedback();
            return ResponseEntity.ok(feedbacks);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error retrieving all feedback: " + e.getMessage());
        }
    }

    @GetMapping("/getFeedbackOfGuest/{guestId}")
    public ResponseEntity<?> getFeedbackOfGuest(@PathVariable Long guestId) {
        try {
            List<Feedback> feedbacks = feedbackService.getFeedbackOfGuest(guestId);
            return ResponseEntity.ok(feedbacks);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error retrieving feedback for guest: " + e.getMessage());
        }
    }
}
