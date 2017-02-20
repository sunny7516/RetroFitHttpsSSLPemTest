package com.example.tacademy.retrofithttpssslpemtest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 회원관련 모든 API 구현
 1. 카카오톡 로그인 및 최초 회원등록_
 2. 로컬 회원 최초 가입_
 3. 로컬 회원 로그인_
 4. 로컬 회원 정보 변경_
 5. 회원 탈퇴하기_
 6. 회원 로그아웃_
 7. 회원 상세 정보 보기_
 8. 회원 본인 모든 기록 보기_
 9. 회원 본인 점수 등록_
 10. 회원 본인 점수 수정_
 11. 회원 본인 점수 삭제_

 */

public interface MemberImpFactory {
    // 2. 로컬 회원 최초 가입_
    @POST("users")
    Call<ResJoin> join(@Body ReqJoin reqJoin);
}
