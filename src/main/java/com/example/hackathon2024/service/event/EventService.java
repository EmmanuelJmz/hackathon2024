package com.example.hackathon2024.service.event;

import com.example.hackathon2024.model.event.Event;
import com.example.hackathon2024.model.event.EventDto;
import com.example.hackathon2024.model.event.EventRepository;
import com.example.hackathon2024.model.eventImages.EventImages;
import com.example.hackathon2024.model.eventImages.EventImagesRepository;
import com.example.hackathon2024.model.user.User;
import com.example.hackathon2024.model.user.UserRepository;
import com.example.hackathon2024.service.images.ImagesService;
import com.example.hackathon2024.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final ImagesService imagesService;
    private final EventImagesRepository eventImagesRepository;


    @Transactional(rollbackFor = {SecurityException.class})
    public ApiResponse<Event> createEvent(EventDto event, List<MultipartFile> files){
        User user = userRepository.findById(event.getUserId()).orElse(null);
        if (user == null) {
            return new ApiResponse<>(
                    null, true, HttpStatus.NOT_FOUND, "El usuario especificado no existe"
            );
        }

        Event event1 = new Event();
        String id = UUID.randomUUID().toString();
        event1.setId(id);
        event1.setTitle(event.getTitle());
        event1.setDescription(event.getDescription());
        event1.setDate(event.getDate());
        event1.setUser(user);
        event1.setAddress(event.getAddress());
        event1.setMedal(event.getMedal());

        Event saveEvent = eventRepository.save(event1);

        int maxImages = Math.min(files.size(), 5);
        for (int i = 0; i < maxImages; i++){
            MultipartFile imageFile = files.get(i);
            String imageUrl = imagesService.upload(imageFile);
            if (imageUrl != null){
                EventImages eventImages = new EventImages();
                eventImages.setEvent(saveEvent);
                eventImages.setImageKey("img" + (i + 1));
                eventImages.setImageUrl(imageUrl);
                eventImagesRepository.save(eventImages);
            }
        }
        return new ApiResponse<>(
          saveEvent, false, HttpStatus.OK, "evento creado"
        );
    }

    @Transactional(readOnly = true)
    public ApiResponse<List<Event>> getAll(){
        List<Event> events = eventRepository.findAll();
        if (events.isEmpty()){
            return new ApiResponse<>(
                    null, true, HttpStatus.NOT_FOUND, "No hay eventos disponibles"
            );
        }else {
            return new ApiResponse<>(
                    events, false,HttpStatus.OK, "eventos"
            );
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ApiResponse<Event> delete(String id){
        Optional<Event> event = eventRepository.findById(id);
        if (event.isPresent()) {
            eventRepository.deleteById(id);
            return new ApiResponse<>(
                    null, false, HttpStatus.OK, "eliminado correctamente"
            );
        }else {
            return new ApiResponse<>(
                    null, true, HttpStatus.NOT_FOUND, "evento no encontrado"
            );
        }
    }

    @Transactional(readOnly = true)
    public ApiResponse<List<Event>> eventsCreatedBy(String userId) {
        List<Event> events = eventRepository.findByUserId(userId);

        if (events.isEmpty()) {
            return new ApiResponse<>(null, true, HttpStatus.NOT_FOUND, "No se encontraron eventos creados por este usuario");
        } else {
            return new ApiResponse<>(events, false, HttpStatus.OK, "Eventos creados por el usuario");
        }
    }

    @Transactional(readOnly = true)
    public ApiResponse<List<Event>> eventsUserParticiped(String userId) {
        List<Event> events = eventRepository.findAll();

        List<Event> participatingEvents = events.stream()
                .filter(event -> event.getParticipants() != null && event.getParticipants().stream()
                        .anyMatch(user -> user.getId().equals(userId)))
                .collect(Collectors.toList());

        if (participatingEvents.isEmpty()) {
            return new ApiResponse<>(null, true, HttpStatus.NOT_FOUND, "El usuario no participa en ningún evento");
        } else {
            return new ApiResponse<>(participatingEvents, false, HttpStatus.OK, "Eventos en los que participa el usuario");
        }
    }


}
