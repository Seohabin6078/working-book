package com.example.workingbook.device.controller;

import com.example.workingbook.device.dto.DeviceDto;
import com.example.workingbook.device.entity.Device;
import com.example.workingbook.device.repository.DeviceRepository;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.UUID;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(DeviceController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class DeviceControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private DeviceRepository deviceRepository;

    @Test
    void 컨트롤러_디바이스_생성_테스트() throws Exception {
        //given
        DeviceDto.Post post = DeviceDto.Post.builder()
                .deviceId(UUID.randomUUID().toString())
                .build();

        String json = gson.toJson(post);

        given(deviceRepository.save(Mockito.any(Device.class))).willReturn(Device.builder().build());

        //when
        ResultActions actions = mockMvc.perform(
                post("/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        );

        //then
        actions
                .andExpect(status().isCreated())
                .andDo(document("post-device",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("deviceId").type(JsonFieldType.STRING).description("디바이스 식별자").attributes(new Attributes.Attribute("constraints", "공백일 수 없습니다."))
                        )
                ));
    }
}
