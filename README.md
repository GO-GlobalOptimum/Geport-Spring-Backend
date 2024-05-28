[spring]
  2024/05/27
  진행 상황
    게시글 작성 기능 postman에서 test하기(성공)

____________________________________________________________________________________________________


**Spring 진행상황 (2024-05-27)**
- **Post시 태그 같이 저장하는 기능:** 성공 :흰색_확인_표시:
- **조회수별 리스트 가져오기:** 성공 :흰색_확인_표시:
- **최근 조회한 리스트 불러오기:** 진행중 → cookie :쿠키:
- **Post 1개에 대한 정보 불러오기:** 성공 :흰색_확인_표시:
- **카테고리별로 리스트 가져오기:** 성공 :흰색_확인_표시:
  - **유의 사항 (카테고리별로 리스트 가져오기 관련):**
    - **Write시 Body 예시:**
      ```json
      {
        "memberId": 1,
        "userName": "testUser",
        "title": "Test Post",
        "postContent": "This is the content of the test post.",
        "thumbnailImage": "http://example.com/thumbnail.jpg",
        "createdDate": "2024-05-27T22:41:28",
        "isPublic": true,
        "categoryIds": [1, 2, 3] // 과학, 수학, 역사 카테고리 ID
      }
      ```
    - **검색시 카테고리 관련된 번호 사용 예시:**
      ```
      http://<your-server-address>/spring/posts/list/by-category?categoryId=1
      ``` (편집됨)

____________________________________________________________________________________________________


[spring]
2024/05/28 15:38
진행 상황  
    지금까지 개발한 모든 기능 merge (성공)

____________________________________________________________________________________________________


Spring 
진행상황 (2024-05-28 ~ 17:00)
  게시글 조회 기능 추가
  특정 사용자의 모든 게시글 조회 (성공)
  게시글 ID로 특정 게시글 조회 (성공)
  팔로우/팔로워 기능 추가 (성공 )
  팔로우 및 팔로워 수 증가 (성공)
  팔로우 테이블에서 팔로워 및 팔로잉 관계 확인 가능 (성공)

  
____________________________________________________________________________________________________

Spring 
진행상황 (2024-05-28 17:00 ~ )
  내 정보 불러오기 및 수정하기 ( 성공 ) 
