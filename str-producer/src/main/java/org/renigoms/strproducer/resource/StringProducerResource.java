package org.renigoms.strproducer.resource;

import lombok.RequiredArgsConstructor;
import org.renigoms.strproducer.service.StringProducerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/producer")
public class StringProducerResource {

    private final StringProducerService producerService;

    @PostMapping
    public ResponseEntity<?> sendMessage(@RequestBody String message){
        producerService.sendMessage(message);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
