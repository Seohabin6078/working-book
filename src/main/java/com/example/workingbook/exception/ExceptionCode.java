package com.example.workingbook.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {
//    MEMBER_NOT_FOUND(404, "Member is not found"),
//    MEMBER_STATUS_SLEEP(403, "MemberStatus is sleep"),
//    MEMBER_STATUS_QUIT(404, "MemberStatus is quit"),
//    MEMBER_EXISTS(409, "Member already exists"),
//    REFRESH_TOKEN_INVALID(401, "Refresh token is invalid"),
//    ACCESS_TOKEN_INVALID(401, "Access token is invalid"), // Authorization 헤더에 refresh 토큰을 넣은 경우
//    AUTHENTICATION_FAILED(401, "Authentication failed, try login again"),
//    POST_NOT_FOUND(404, "Post is not found"),
//    ADMIN_ONLY(403, "This is only for admin"),
//    NOT_LOGIN(401, "Not login"),
//    NO_AUTH_FOR_POST(403, "No authority for this post"),
//    NO_AUTH_FOR_MEMBER(403, "No authority for this member"),
//    NO_AUTH_FOR_COMMENT(403, "No authority for this comment"),
//    COMMENT_NOT_FOUND(404, "Comment is not found");

    WORD_BOOK_NOT_FOUND(404, "WordBook is not found"),
    WORD_NOT_FOUND(404, "Word is not found");

    private final int status;
    private final String message;

    ExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
