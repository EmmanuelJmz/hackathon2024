package com.example.hackathon2024.service.event;

import com.example.hackathon2024.model.event.Event;
import com.example.hackathon2024.model.event.EventRepository;
import com.example.hackathon2024.model.user.User;
import com.example.hackathon2024.model.user.UserRepository;
import com.example.hackathon2024.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventParticipationService {

    private final EventRepository eventRepository;

    private final UserRepository userRepository;


    public ApiResponse<Event> participateInEvent(String eventId, String userId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow();

        User user = userRepository.findById(userId)
                .orElseThrow();

        assert event.getParticipants() != null;
        if (event.getParticipants().contains(user)) {
            return new ApiResponse<>(null, true, HttpStatus.BAD_REQUEST, "El usuario ya está participando en el evento");
        }

        event.getParticipants().add(user);
        Event savedEvent = eventRepository.save(event);

        return new ApiResponse<>(savedEvent, false, HttpStatus.OK, "Participación exitosa en el evento");
    }
}

