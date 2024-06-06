package com.example.hms.Services;

import com.example.hms.Entities.Feedback;
import com.example.hms.Entities.Guest;
import com.example.hms.Repositories.FeedbackRepository;
import com.example.hms.Repositories.GuestRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    private GuestRepository guestRepository;

    public Feedback giveFeedback(Long guestID, String feedbackContent) throws Exception {
        Guest guest = guestRepository.getGuestByGuestID(guestID);

        if(guest == null) {
            throw new Exception("No Guest was found with ID: " + guestID);
        }

        if(feedbackContent.length() == 0 || feedbackContent.isEmpty()){
            throw new Exception("Feedback content is empty");
        }

        Feedback feedback = new Feedback();
        feedback.setGuest(guest);
        feedback.setContent(feedbackContent);
        feedback.setSubmissionDate(LocalDate.now());
        return feedbackRepository.save(feedback);
    }

    public void deleteFeedback(Long feedbackId) {
        if (!feedbackRepository.existsById(feedbackId)) {
            throw new EntityNotFoundException("Feedback not found with id: " + feedbackId);
        }
        feedbackRepository.deleteById(feedbackId);
    }

    public List<Feedback> getAllFeedback() {
        List<Feedback> feedbacks = feedbackRepository.findAll();
        if (feedbacks.isEmpty()) {
            throw new EntityNotFoundException("No feedback found.");
        }
        return feedbacks;
    }

    public List<Feedback> getFeedbackOfGuest(Long guestId) {
        List<Feedback> feedbacks = feedbackRepository.findAllByGuestGuestID(guestId);
        if (feedbacks.isEmpty()) {
            throw new EntityNotFoundException("No feedback found for guest id: " + guestId);
        }
        return feedbacks;
    }
}
