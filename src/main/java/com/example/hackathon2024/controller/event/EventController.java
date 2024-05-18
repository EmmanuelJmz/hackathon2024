package com.example.hackathon2024.controller.event;

import com.example.hackathon2024.model.event.Event;
import com.example.hackathon2024.model.event.EventDto;
import com.example.hackathon2024.model.event.PayDto;
import com.example.hackathon2024.service.event.EventParticipationService;
import com.example.hackathon2024.service.event.EventService;
import com.example.hackathon2024.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api-greenpal/auth/event")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class EventController {
    private final EventService service;
    private final EventParticipationService participationService;

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Event>> create(@RequestPart EventDto event, @RequestParam("files") List<MultipartFile> files){
        try {
            ApiResponse<Event> response = service.createEvent(event, files);
            HttpStatus statusCode = response.isError() ? HttpStatus.BAD_REQUEST : HttpStatus.OK;
            return new ResponseEntity<>(
                    response,
                    statusCode
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(
                            null, true, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<Event>>> getAll () {
        try {
            ApiResponse<List<Event>> response = service.getAll();
            HttpStatus statusCode = response.isError() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
            return new ResponseEntity<>(response, statusCode);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(null, true, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Event>> deleteApplication (@PathVariable String id) {
        try {
            ApiResponse<Event> response = service.delete(id);
            HttpStatus statusCode = response.isError() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
            return new ResponseEntity<>(response, statusCode);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(null, true, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Event>> updateEvent (@PathVariable String id, @RequestBody EventDto event) {
        try {
            ApiResponse<Event> response = service.update(id, event);
            HttpStatus statusCode = response.isError() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
            return new ResponseEntity<>(response, statusCode);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(null, true, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @PostMapping("/events/{eventId}/participate")
    public ApiResponse<Event> participate(@PathVariable String eventId, @RequestParam String userId) {
        return participationService.participateInEvent(eventId, userId);
    }

    @GetMapping("/user/{userId}/created")
    public ResponseEntity<ApiResponse<List<Event>>> eventsCreatedBy(@PathVariable String userId) {
        try {
            ApiResponse<List<Event>> response = service.eventsCreatedBy(userId);
            HttpStatus statusCode = response.isError() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
            return new ResponseEntity<>(response, statusCode);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(null, true, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @GetMapping("/user/{userId}/participating")
    public ResponseEntity<ApiResponse<List<Event>>> eventsUserParticipated(@PathVariable String userId) {
        try {
            ApiResponse<List<Event>> response = service.eventsUserParticiped(userId);
            HttpStatus statusCode = response.isError() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
            return new ResponseEntity<>(response, statusCode);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(null, true, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @PostMapping("/{userId}/verify")
    public ResponseEntity<ApiResponse<String>> verifyEventCode(
            @PathVariable String userId,
            @RequestParam String code
    ) {
        try {
            ApiResponse<String> response = service.verifyEventCode(code, userId);
            HttpStatus statusCode = response.isError() ? response.getStatus() : HttpStatus.OK;
            return new ResponseEntity<>(response, statusCode);

        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(null, true, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<ApiResponse<String>> verifyEventCode(@RequestBody PayDto request) {
        try {
            ApiResponse<String> response = service.verifyEventCode(request.getCode(), request.getUserId());
            HttpStatus statusCode = response.isError() ? response.getStatus() : HttpStatus.OK;
            return new ResponseEntity<>(response, statusCode);

        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(null, true, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

}
