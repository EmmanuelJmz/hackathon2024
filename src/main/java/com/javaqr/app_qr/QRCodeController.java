package com.javaqr.app_qr;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Base64;
import java.util.Optional;

@RestController
@RequestMapping("/api")

public class QRCodeController {
    @Autowired
    private UserRepository repository;
    @Autowired
    private QRCodeService qrCodeService;
    private String encryptText(String plainText) {
        byte[] encodedBytes = Base64.getEncoder().encode(plainText.getBytes());
        return new String(encodedBytes);
    }
    private String decryptText(String encryptedText) {
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
        return new String(decodedBytes);
    }


    @GetMapping("/v1/qrcode")
    public void generateQRCode(HttpServletResponse response,
                               @RequestParam String text,
                               @RequestParam(defaultValue = "350") int width,
                               @RequestParam(defaultValue = "350") int height) throws Exception {
        BufferedImage image = qrCodeService.generateQRCode(text, width, height);
        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(image, "png", outputStream);
        outputStream.flush();
        outputStream.close();
    }

    @PutMapping("/person/{id}")
    public User updatePerson(@PathVariable int id ,@RequestBody User user, @RequestParam String status) {
        Optional<User> existingUserOpt = repository.findById(id);
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
            existingUser.setStatus(status);
            return repository.save(existingUser);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    }

}