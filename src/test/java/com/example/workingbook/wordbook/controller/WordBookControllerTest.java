package com.example.workingbook.wordbook.controller;

import com.example.workingbook.wordbook.dto.WordBookDto;
import com.example.workingbook.wordbook.entity.WordBook;
import com.example.workingbook.wordbook.mapper.WordBookMapper;
import com.example.workingbook.wordbook.service.WordBookService;
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
import org.springframework.restdocs.headers.HeaderDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.snippet.Attributes.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WordBookController.class)
@MockBean(JpaMetamodelMappingContext.class) // @WebMvcTest에는 Jpa 생성과 관련된 기능이 전혀 없기 때문에 Application 클래스에 @EnableJpaAuditing 애너테이션을 사용하기 위해 필요하다.
@AutoConfigureRestDocs
public class WordBookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private WordBookService wordBookService;

    @MockBean
    private WordBookMapper mapper;

    @Test
    void 컨트롤러_단어장_생성_테스트() throws Exception {
        //given
        WordBookDto.Post post = WordBookDto.Post.builder()
                .title("단어장")
                .accessRange(0)
                .build();

        String json = gson.toJson(post);

        WordBookDto.Response response = WordBookDto.Response.builder()
                .wordBookId(1L)
                .title("단어장")
                .accessRange(0)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();

        given(mapper.wordBookPostDtoToWordBook(Mockito.any(WordBookDto.Post.class))).willReturn(WordBook.builder().build());
        given(wordBookService.createWordBook(Mockito.any(WordBook.class))).willReturn(WordBook.builder().build());
        given(mapper.wordBookToWordBookResponseDto(Mockito.any(WordBook.class))).willReturn(response);

        //when
        ResultActions actions = mockMvc.perform(
                post("/wordbooks")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        );

        //then
        actions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(post.getTitle()))
                .andExpect(jsonPath("$.accessRange").value(post.getAccessRange()))
                .andDo(document("post-wordbook",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                List.of(
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("제목").attributes(new Attribute("constraints", "공백일 수 없습니다.")),
                                        fieldWithPath("accessRange").type(JsonFieldType.NUMBER).description("공개범위").attributes(new Attribute("constraints", "0 이상 2 이하의 숫자여야 합니다."))
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("wordBookId").type(JsonFieldType.NUMBER).description("단어장 식별자"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                        fieldWithPath("accessRange").type(JsonFieldType.NUMBER).description("공개범위"),
                                        fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성일자"),
                                        fieldWithPath("modifiedAt").type(JsonFieldType.STRING).description("수정일자")
                                )
                        )
                ));
    }

    @Test
    void 컨트롤러_단어장_수정_테스트() throws Exception {
        //given
        long wordBookId = 1L;
        WordBookDto.Patch patch = WordBookDto.Patch.builder()
                .title("단어장")
                .accessRange(0)
                .build();

        String json = gson.toJson(patch);

        WordBookDto.Response response = WordBookDto.Response.builder()
                .wordBookId(wordBookId)
                .title("단어장")
                .accessRange(0)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();

        given(mapper.wordBookPatchDtoToWordBook(Mockito.any(WordBookDto.Patch.class))).willReturn(WordBook.builder().build());
        given(wordBookService.updateWordBook(Mockito.any(WordBook.class))).willReturn(WordBook.builder().build());
        given(mapper.wordBookToWordBookResponseDto(Mockito.any(WordBook.class))).willReturn(response);

        //when
        ResultActions actions = mockMvc.perform(
                patch("/wordbooks/{wordbook-id}", wordBookId)
                        .accept(MediaType.APPLICATION_JSON) // 응답 타입 설정
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(patch.getTitle()))
                .andExpect(jsonPath("$.accessRange").value(patch.getAccessRange()))
                .andDo(document("patch-wordbook",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("wordbook-id").description("단어장 식별자")
                        ),
                        requestFields(
                                List.of(
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("제목").optional().attributes(new Attribute("constraints", "공백일 수 없습니다.")),
                                        fieldWithPath("accessRange").type(JsonFieldType.NUMBER).description("공개범위").optional().attributes(new Attribute("constraints", "0 이상 2 이하의 숫자여야 합니다."))
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("wordBookId").type(JsonFieldType.NUMBER).description("단어장 식별자"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                        fieldWithPath("accessRange").type(JsonFieldType.NUMBER).description("공개범위"),
                                        fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성일자"),
                                        fieldWithPath("modifiedAt").type(JsonFieldType.STRING).description("수정일자")
                                )
                        )
                ));
    }

    @Test
    void 컨트롤러_단어장_조회_테스트() throws Exception {
        //given
        long wordBookId = 1L;
        WordBookDto.Response response = WordBookDto.Response.builder()
                .wordBookId(wordBookId)
                .title("단어장")
                .accessRange(0)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();

        given(wordBookService.findWordBook(Mockito.anyLong())).willReturn(WordBook.builder().build());
        given(mapper.wordBookToWordBookResponseDto(Mockito.any(WordBook.class))).willReturn(response);

        //when
        ResultActions actions = mockMvc.perform(
                get("/wordbooks/{wordbook-id}", wordBookId)
                        .accept(MediaType.APPLICATION_JSON)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(response.getTitle()))
                .andExpect(jsonPath("$.accessRange").value(response.getAccessRange()))
                .andDo(document("get-wordbook",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("wordbook-id").description("단어장 식별자")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("wordBookId").type(JsonFieldType.NUMBER).description("단어장 식별자"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                        fieldWithPath("accessRange").type(JsonFieldType.NUMBER).description("공개범위"),
                                        fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성일자"),
                                        fieldWithPath("modifiedAt").type(JsonFieldType.STRING).description("수정일자")
                                )
                        )
                ));
    }

    @Test
    void 컨트롤러_단어장_삭제_테스트() throws Exception {
        //given
        long wordBookId = 1L;
        doNothing().when(wordBookService).deleteWordBook(Mockito.anyLong());

        //when
        ResultActions actions = mockMvc.perform(
                delete("/wordbooks/{wordbook-id}", wordBookId)
        );

        //then
        actions
                .andExpect(status().isNoContent())
                .andDo(document("delete-wordbook",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("wordbook-id").description("단어장 식별자")
                        )
                ));
    }
}
