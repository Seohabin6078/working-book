package com.example.workingbook.device.entity;

import com.example.workingbook.audit.Auditable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Device extends Auditable {
    @Id
    private String deviceId;
}
