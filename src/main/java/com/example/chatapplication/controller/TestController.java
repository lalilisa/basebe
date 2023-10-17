package com.example.chatapplication.controller;


import com.example.chatapplication.common.Utils;
import com.example.chatapplication.domain.User;
import com.example.chatapplication.dto.request.Notice;
import com.example.chatapplication.service.write.FireBaseNotifiCommandService;
import com.example.chatapplication.socket.module.ChatModule;
import com.google.zxing.WriterException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@RestController
@RequestMapping("api")
@CrossOrigin
@RequiredArgsConstructor
public class TestController {

    private final FireBaseNotifiCommandService fireBaseNotifiCommandService;
    @Autowired
    private ChatModule chatModule;
    @GetMapping(value = "get-qr",produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<?> test() throws WriterException, IOException {


        return ResponseEntity.ok(Utils.genQrCode("TRIMAIAAMAAMAMASdassdasdasdas"));
    }

    @GetMapping("test")
    public ResponseEntity<?> testSocket() {

        return ResponseEntity.ok("trimai");
    }

    @PostMapping("notifi")
    public ResponseEntity<?> testNOti(@RequestBody Notice notice){
        asyncNotifi(notice);
        return ResponseEntity.ok("TRIMAIs");
    }
    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private void asyncNotifi(Notice notice){
        CompletableFuture.runAsync(() -> {
            try {
                // Wait for 3 seconds
                Thread.sleep(3000);
                fireBaseNotifiCommandService.sendNotification(notice);

            } catch (InterruptedException e) {
                //TODO
            }
        }, executorService);
    }
}
