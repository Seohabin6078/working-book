package com.example.workingbook.word.controller;

import com.example.workingbook.word.dto.WordDto;
import com.example.workingbook.word.entity.Word;
import com.example.workingbook.word.mapper.WordMapper;
import com.example.workingbook.word.service.WordService;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.snippet.Attributes.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WordController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class WordControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private WordService wordService;

    @MockBean
    private WordMapper mapper;

    @Test
    void 컨트롤러_단어_생성_테스트() throws Exception {
        //given
        Long wordBookId = 1L;

        WordDto.Post post = WordDto.Post.builder()
                .name("단어")
                .content("설명입니다.")
                .build();

        String json = gson.toJson(post);

        WordDto.Response response = WordDto.Response.builder()
                .wordId(1L)
                .name("단어")
                .content("설명입니다.")
                .wordBookId(wordBookId)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();

        given(mapper.wordPostDtoToWord(Mockito.any(WordDto.Post.class), Mockito.anyLong())).willReturn(Word.builder().build());
        given(wordService.createWord(Mockito.any(Word.class))).willReturn(Word.builder().build());
        given(mapper.wordToWordResponseDto(Mockito.any(Word.class))).willReturn(response);

        //when
        ResultActions actions = mockMvc.perform(
                post("/wordbooks/{wordbook-id}/words", wordBookId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        );

        //then
        actions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(post.getName()))
                .andExpect(jsonPath("$.content").value(post.getContent()))
                .andDo(document("post-word",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("wordbook-id").description("단어장 식별자")
                        ),
                        requestFields(
                                List.of(
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("단어 이름").attributes(new Attribute("constraints", "공백일 수 없습니다.")),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("단어 내용").attributes(new Attribute("constraints", "공백일 수 없습니다."))
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("wordId").type(JsonFieldType.NUMBER).description("단어 식별자"),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("단어 이름"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("단어 내용"),
                                        fieldWithPath("wordBookId").type(JsonFieldType.NUMBER).description("단어장 식별자"),
                                        fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성일자"),
                                        fieldWithPath("modifiedAt").type(JsonFieldType.STRING).description("수정일자")
                                )
                        )
                ));
    }

    @Test
    void 컨트롤러_단어_수정_테스트() throws Exception {
        //given
        Long wordBookId = 1L;
        Long wordId = 1L;

        WordDto.Patch patch = WordDto.Patch.builder()
                .name("단어")
                .content("설명입니다.")
                .build();

        String json = gson.toJson(patch);

        WordDto.Response response = WordDto.Response.builder()
                .wordId(wordId)
                .name("단어")
                .content("설명입니다.")
                .wordBookId(wordBookId)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();

        given(mapper.wordPatchDtoToWord(Mockito.any(WordDto.Patch.class))).willReturn(Word.builder().build());
        given(wordService.updateWord(Mockito.any(Word.class))).willReturn(Word.builder().build());
        given(mapper.wordToWordResponseDto(Mockito.any(Word.class))).willReturn(response);

        //when
        ResultActions actions = mockMvc.perform(
                patch("/wordbooks/{wordbook-id}/words/{word-id}", wordBookId, wordId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(patch.getName()))
                .andExpect(jsonPath("$.content").value(patch.getContent()))
                .andDo(document("patch-word",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("wordbook-id").description("단어장 식별자"),
                                parameterWithName("word-id").description("단어 식별자")
                        ),
                        requestFields(
                                List.of(
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("단어 이름").optional().attributes(new Attribute("constraints", "공백일 수 없습니다.")),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("단어 내용").optional().attributes(new Attribute("constraints", "공백일 수 없습니다."))
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("wordId").type(JsonFieldType.NUMBER).description("단어 식별자"),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("단어 이름"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("단어 내용"),
                                        fieldWithPath("wordBookId").type(JsonFieldType.NUMBER).description("단어장 식별자"),
                                        fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성일자"),
                                        fieldWithPath("modifiedAt").type(JsonFieldType.STRING).description("수정일자")
                                )
                        )
                ));
    }

    @Test
    void 컨트롤러_단어_조회_테스트() throws Exception {
        //given
        Long wordBookId = 1L;

        WordDto.Response response1 = WordDto.Response.builder()
                .wordId(1L)
                .name("단어1")
                .content("설명입니다.")
                .wordBookId(wordBookId)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();

        WordDto.Response response2 = WordDto.Response.builder()
                .wordId(2L)
                .name("단어2")
                .content("설명입니다.")
                .wordBookId(wordBookId)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();

        WordDto.Response response3 = WordDto.Response.builder()
                .wordId(3L)
                .name("단어3")
                .content("설명입니다.")
                .wordBookId(wordBookId)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();

        List<WordDto.Response> responses = new ArrayList<>();
        responses.add(response1);
        responses.add(response2);
        responses.add(response3);

        given(wordService.findWords(Mockito.anyLong())).willReturn(new ArrayList<>());
        given(mapper.wordsToWordResponseDtos(Mockito.anyList())).willReturn(responses);

        //when
        ResultActions actions = mockMvc.perform(
                get("/wordbooks/{wordbook-id}/words", wordBookId)
                        .accept(MediaType.APPLICATION_JSON)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3))) // 최상위 json이 list이고 크기가 3임을 검증하는 부분
                .andDo(document("get-words",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("wordbook-id").description("단어장 식별자")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("[].wordId").type(JsonFieldType.NUMBER).description("단어 식별자"),
                                        fieldWithPath("[].name").type(JsonFieldType.STRING).description("단어 이름"),
                                        fieldWithPath("[].content").type(JsonFieldType.STRING).description("단어 내용"),
                                        fieldWithPath("[].wordBookId").type(JsonFieldType.NUMBER).description("단어장 식별자"),
                                        fieldWithPath("[].createdAt").type(JsonFieldType.STRING).description("생성일자"),
                                        fieldWithPath("[].modifiedAt").type(JsonFieldType.STRING).description("수정일자")
                                )
                        )
                ));

    }

    @Test
    void 컨트롤러_단어_한건_조회_테스트() throws Exception {
        //given
        Long wordBookId = 1L;
        Long wordId = 1L;

        WordDto.Response response = WordDto.Response.builder()
                .wordId(wordId)
                .name("단어")
                .content("설명입니다.")
                .wordBookId(wordBookId)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();

        given(wordService.findWord(Mockito.anyLong())).willReturn(Word.builder().build());
        given(mapper.wordToWordResponseDto(Mockito.any(Word.class))).willReturn(response);

        //when
        ResultActions actions = mockMvc.perform(
                get("/wordbooks/{wordbook-id}/words/{word-id}", wordBookId, wordId)
                        .accept(MediaType.APPLICATION_JSON)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(response.getName()))
                .andExpect(jsonPath("$.content").value(response.getContent()))
                .andDo(document("get-word",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("wordbook-id").description("단어장 식별자"),
                                parameterWithName("word-id").description("단어 식별자")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("wordId").type(JsonFieldType.NUMBER).description("단어 식별자"),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("단어 이름"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("단어 내용"),
                                        fieldWithPath("wordBookId").type(JsonFieldType.NUMBER).description("단어장 식별자"),
                                        fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성일자"),
                                        fieldWithPath("modifiedAt").type(JsonFieldType.STRING).description("수정일자")
                                )
                        )
                ));
    }

    @Test
    void 컨트롤러_단어_삭제_테스트() throws Exception {
        //given
        Long wordBookId = 1L;
        Long wordId = 1L;

        doNothing().when(wordService).deleteWord(Mockito.anyLong());

        //when
        ResultActions actions = mockMvc.perform(
                delete("/wordbooks/{wordbook-id}/words/{word-id}", wordBookId, wordId)
        );

        //then
        actions
                .andExpect(status().isNoContent())
                .andDo(document("delete-word",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("wordbook-id").description("단어장 식별자"),
                                parameterWithName("word-id").description("단어 식별자")
                        )
                ));
    }

    @Test
    void 컨트롤러_단어_모두_삭제_테스트() throws Exception {
        //given
        Long wordBookId = 1L;

        doNothing().when(wordService).deleteWords(Mockito.anyLong());

        //when
        ResultActions actions = mockMvc.perform(
                delete("/wordbooks/{wordbook-id}/words", wordBookId)
        );

        //then
        actions
                .andExpect(status().isNoContent())
                .andDo(document("delete-words",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("wordbook-id").description("단어장 식별자")
                        )
                ));
    }
}
