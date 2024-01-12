package com.example.workingbook.device.controller;

import com.example.workingbook.device.dto.DeviceDto;
import com.example.workingbook.device.entity.Device;
import com.example.workingbook.device.service.DeviceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequestMapping("/devices")
@RequiredArgsConstructor
@RestController
public class DeviceController {
    private final DeviceService deviceService;

    @PostMapping
    public ResponseEntity<Void> postDevice(@RequestBody @Valid DeviceDto.Post requestBody) {
        deviceService.createDevice(requestBody);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
