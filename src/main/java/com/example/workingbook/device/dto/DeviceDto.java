package com.example.workingbook.device.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

public class DeviceDto {
    @Jacksonized // 필드가 1개인 dto에서는 꼭 사용해야함.
    @Builder
    @Getter
    public static class Post {
        @NotBlank
        private String deviceId;
    }
}
