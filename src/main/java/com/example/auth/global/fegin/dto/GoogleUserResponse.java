package com.example.auth.global.fegin.dto;

public record GoogleUserResponse (
  String sub,
  String name,
  String email
) {

}

//{ 구글이 제공해ㅔ주는 응답데이터 예시
//        "sub": "1234567890", -> Google 계정의 고유 ID
//        "name": "방세준", -> 이름
//        "given_name": "세준", -> 이름
//        "family_name": "방", -> 성
//        "profile": "https://exampleaskmdalsfkjnao",-> 프로필사진
//        "picture": "https://lh3.googleusercontent.com/a-/AOh14GhLz9EHpJKNl3TkX2TlbQlx1Fsiwz82gC-_Kw4=s96-c", -> 프로필사진 URL
//        "email": "john.doe@example.com", -> 사용자의 email
//        "email_verified": true, -> 이메일이 검증되었는지 여부
//        "locale": "en" -> 사용자의 언어/지역 코
//        }
