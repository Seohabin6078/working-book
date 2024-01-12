package com.example.workingbook.device.service;

import com.example.workingbook.device.dto.DeviceDto;
import com.example.workingbook.device.entity.Device;
import com.example.workingbook.device.repository.DeviceRepository;
import com.example.workingbook.exception.BusinessLogicException;
import com.example.workingbook.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DeviceService {
    private final DeviceRepository deviceRepository;

    public void createDevice(DeviceDto.Post post) {
        Device device = Device.builder()
                .deviceId(post.getDeviceId())
                .build();
        deviceRepository.save(device);
    }

    public Device findDevice(String deviceId) {
        return deviceRepository.findById(deviceId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.DEVICE_NOT_FOUND));
    }
}
