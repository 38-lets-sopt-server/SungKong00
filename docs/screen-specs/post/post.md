#  게시판 게시글 기능 명세서

## 1. 개요
에브리타임 클론 실습의 화면 설계서를 재정리 한다.

게시글 작성, 수정, 삭제, 조회 기능을 구현한다.
게시글과 관련된 공감, 댓글, 스크랩 등 관련 기능도 정의한다.
투표는 추후 확장을 위해 id 자리만 확보한다.

## 2. 기능
게시판 목록, 게시글 상세, 게시글 작성을 정의한다.

### 2.1 게시글 작성
- 작성지 익명 / 닉네임 표기 여부 선택 
- 제목 및 내용 입력 
- 게시글 유형 질문으로 선택 
- 사진 추가
- 투표 추가

### 2.2 게시글 목록 조회
- 제목
- 작성자
- 작성일
- 내용
- 댓글 수
- 투표

### 2.3 게시글 상세 조회
- 제목
- 작성자
- 작성일
- 내용
- 댓글 수
- 투표
- 댓글

### 2.4 게시글 수정 / 삭제
- 제목 및 내용 수정
- 사진 추가/삭제
- 투표 수정
- 작성지 익명/닉네임 표기 여부 변경
- 게시글 유형 변경(질문/일반)
- 게시글 삭제
- 게시글 삭제 시 댓글, 공감, 스크랩 등 관련 데이터도 함께 삭제

## 3. 관련 기능
- 공감: 게시글에 공감 기능 추가, 공감 수 표시
- 댓글: 게시글에 댓글 기능 추가, 댓글 수 표시
- 스크랩: 게시글에 스크랩 기능 추가, 스크랩 수 표시
- 투표: 게시글에 투표 기능 추가, 투표 결과 표시 (추후 확장 예정)
- 알림: 게시글 작성, 수정, 삭제 시 관련 사용자에게 알림 기능 추가, 알림 받기 켠 사용자에게 알림 발송
- 신고: 게시글에 신고 기능 추가, 신고 수 표시, 신고 처리 로직 구현

## 4. 유저 시나리오
### 시나리오 1: 게시판 탐색 -> 게시글 확인
1. 사용자가 게시판 선택
2. 게시글 목록에서 제목, 작성자, 작성일, 내용 일부, 사진 썸네일, 댓글 수, 투표 진행 여부 확인
3. 게시글 상세 화면으로 이동하여 본문과 사진, 댓글, 투표 확인

### 시나리오 2: 게시글 작성
1. 게시글 작성 화면으로 이동
2. 제목, 내용 입력
3. 작성지 익명 / 닉네임 표기 여부 선택
4. 게시글 유형 질문으로 선택
5. 사진 추가
6. 투표 추가
7. 게시글 작성 완료 후 게시글 상세 화면으로 이동하여 작성한 내용 확인

### 시나리오 3: 댓글 / 대댓글 작성 및 기타 기능 사용
1. 게시글 상세 페이지에서 본문 확인
2. 공감, 댓글 작성, 스크랩, 투표, 투표 결과 확인, 신고 기능 사용

## 5. 도메인 모델

### 5.1 게시글 (Post)
| 필드명          | 타입            | 설명                                              |
|--------------|---------------|-------------------------------------------------|
| id           | Long          | 게시글 고유 ID                                       |
| title        | String        | 게시글 제목                                          |
| content      | String        | 게시글 내용                                          |
| authorId     | Long          | 게시글 작성자 ID                                      |
| createdAt    | LocalDateTime | 게시글 작성 시간                                       |
| updatedAt    | LocalDateTime | 게시글 수정 시간                                       |
| isAnonymous  | Boolean       | 작성지 익명 여부                                       |
| postType     | PostType      | 게시글 유형 (QUESTION / GENERAL / 추후 확장 가능)          |
| images       | `List<image>` | 게시글에 첨부된 사진 목록                                  |
| voteId       | Long          | 게시글에 연결된 투표 ID (추후 확장)                          |
| likeCount    | Integer       | 게시글 공감 수                                        |
| commentCount | Integer       | 게시글 댓글 수                                        |
| scrapCount   | Integer       | 게시글 스크랩 수                                       |
| reportCount  | Integer       | 게시글 신고 수                                        |
| status       | PostStatus    | 게시글 상태 (PUBLISHED, DELETED / BANNED / 추후 확장 가능) |

### 5.2 게시글 유형 (PostType)
| 값        | 설명        |
|----------|-----------|
| QUESTION | 질문 게시글 유형 |
| GENERAL  | 일반 게시글 유형 |

### 5.3 게시글 상태 (PostStatus)
| 값         | 설명                 |
|-----------|--------------------|
| PUBLISHED | 게시글이 게시된 상태        |
| DELETED   | 게시글이 삭제된 상태        |
| BANNED    | 게시글이 신고로 인해 차단된 상태 |

### 5.4 이미지 (Image)
| 필드명    | 타입     | 설명                 |
|--------|--------|--------------------|
| id     | Long   | 이미지 고유 ID          |
| url    | String | 이미지 URL            |
| postId | Long   | 해당 이미지가 첨부된 게시글 ID |

### 5.5 투표 (Vote)
| 필드명    | 타입   | 설명                |
|--------|------|-------------------|
| id     | Long | 투표 고유 ID          |
| postId | Long | 해당 투표가 연결된 게시글 ID |

투표 관련 필드는 추후 확장을 위해 id 자리만 확보한다.

### 5.6 공감 (Like)
| 필드명    | 타입   | 설명                |
|--------|------|-------------------|
| id     | Long | 공감 고유 ID          |
| postId | Long | 해당 공감이 연결된 게시글 ID |
| userId | Long | 공감을 누른 사용자 ID     |

### 5.7 댓글 (Comment)
| 필드명             | 타입            | 설명                              |
|-----------------|---------------|---------------------------------|
| id              | Long          | 댓글 고유 ID                        |
| postId          | Long          | 해당 댓글이 연결된 게시글 ID               |
| userId          | Long          | 댓글 작성자 ID                       |
| isAnonymous     | Boolean       | 댓글 작성지 익명 여부                    |
| content         | String        | 댓글 내용                           |
| createdAt       | LocalDateTime | 댓글 작성 시간                        |
| updatedAt       | LocalDateTime | 댓글 수정 시간                        |
| parentCommentId | Long          | 대댓글인 경우 부모 댓글 ID (null이면 일반 댓글) |

###  5.8 스크랩 (Scrap)
| 필드명    | 타입   | 설명                |
|--------|------|-------------------|
| id     | Long | 스크랩 고유 ID          |
| postId | Long | 해당 스크랩이 연결된 게시글 ID |
| userId | Long | 스크랩한 사용자 ID       |
| createdAt | LocalDateTime | 스크랩 생성 시간        |

### 5.9 익명 번호
| 필드명       | 타입            | 설명                   |
|-----------|---------------|----------------------|
| id        | Long          | 익명 번호 고유 ID          |
| number    | Integer       | 익명 번호 (1~9999)       |
| postId    | Long          | 해당 익명 번호가 연결된 게시글 ID |
| userId    | Long          | 익명 번호가 연결된 사용자 ID    |
| createdAt | LocalDateTime | 익명 번호 생성 시간          |

## 6. API 명세
❗️아직 API까지 작성 할 필요는 없었는데 생각 못하고 작성해버림. 추후 Auth를 추가할 때 API 명세는 다시 작성할 예정.
### 6.1 게시글 작성

- **URL**: `/api/posts`
- **Method**: POST
- **Request Body**:
```json
{
  "title": "게시글 제목",
  "content": "게시글 내용",
  "isAnonymous": true,
  "postType": "GENERAL",
  "images": ["image1_url", "image2_url"],
   "vote": "vote_id" 
}
```
**Response**:
- **Status Code**: 201 Created
- **Body**:
```json
{
  "id": 1,
  "title": "게시글 제목",
  "content": "게시글 내용",
  "authorId": 123,
  "createdAt": "2026-06-01T12:00:00",
  "updatedAt": "2026-06-01T12:00:00",
  "isAnonymous": true,
  "postType": "GENERAL",
  "images": ["image1_url", "image2_url"],
  "voteId": "vote_id",
  "likeCount": 0,
  "commentCount": 0,
  "scrapCount": 0,
  "reportCount": 0,
  "status": "PUBLISHED"
}
```
### 6.2 게시글 목록 조회

#### 요구사항
- 일반 사용자는 PUBLISHED 상태의 게시글만 조회할 수 있다.
- 관리자는 PUBLISHED, DELETED, BANNED 상태의 게시글을 모두 조회할 수 있다.

- **URL**: `/api/posts`
- **Method**: GET
**Response**:
- **Status Code**: 200 OK
- **Body**:
```json
[
  {
    "id": 1,
    "title": "게시글 제목",
    "content": "게시글 내용",
    "authorId": 123,
    "createdAt": "2026-06-01T12:00:00",
    "updatedAt": "2026-06-01T12:00:00",
    "isAnonymous": true,
    "postType": "GENERAL",
    "images": ["image1_url", "image2_url"],
    "voteId": "vote_id",
    "likeCount": 10,
    "commentCount": 5,
    "scrapCount": 3,
    "reportCount": 1,
    "status": "PUBLISHED"
  },
  {
    "id": 2,
    "title": "게시글 제목2",
    "content": "게시글 내용2",
    "authorId": 124,
    "createdAt": "2026-06-02T12:00:00",
    "updatedAt": "2026-06-02T12:00:00",
    "isAnonymous": false,
    "postType": "QUESTION",
    "images": ["image3_url"],
    "voteId": "vote_id2",
    "likeCount": 20,
    "commentCount": 10,
    "scrapCount": 5,
    "reportCount": 0,
    "status": "PUBLISHED"
  }
]
```
### 6.3 게시글 상세 조회

#### 요구사항
- 일반 사용자는 PUBLISHED 상태의 게시글만 조회할 수 있다.
- 관리자는 PUBLISHED, DELETED, BANNED 상태의 게시글을 모두 조회할 수 있다.

- **URL**: `/api/posts/{postId}`
- **Method**: GET
 **Response**:
- **Status Code**: 200 OK
- **Body**:
```json
{
  "id": 1,
  "title": "게시글 제목",
  "content": "게시글 내용",
  "authorId": 123,
  "createdAt": "2026-06-01T12:00:00",
  "updatedAt": "2026-06-01T12:00:00",
  "isAnonymous": true,
  "postType": "GENERAL",
  "images": ["image1_url", "image2_url"],
  "voteId": "vote_id",
  "likeCount": 10,
  "commentCount": 5,
  "scrapCount": 3,
  "reportCount": 1,
  "status": "PUBLISHED",
  "comments": [
    {
      "id": 1,
      "postId": 1,
      "isAnonymous": true,
      "authorDisplayName": "익명 3",
      "content": "댓글 내용",
      "createdAt": "2026-06-01T13:00:00",
      "updatedAt": "2026-06-01T13:00:00",
      "parentCommentId": null
    },
    {
      "id": 2,
      "postId": 1,
      "isAnonymous": false,
      "authorDisplayName": "닉네임",
      "content": "대댓글 내용",
      "createdAt": "2026-06-01T14:00:00",
      "updatedAt": "2026-06-01T14:00:00",
      "parentCommentId": 1
    }
  ]
}
```

### 6.4 게시글 수정

#### 요구사항
- 작성자 본인만 게시글을 수정할 수 있다.

- **URL**: `/api/posts/{postId}`
- **Method**: PUT
- **Request Body**:
```json
{
  "title": "수정된 게시글 제목",
  "content": "수정된 게시글 내용",
  "isAnonymous": false,
  "postType": "QUESTION",
  "images": ["new_image_url"],
  "vote": "new_vote_id"
}
```
 **Response**:
- **Status Code**: 200 OK
- **Body**:
```json
{
  "id": 1,
  "title": "수정된 게시글 제목",
  "content": "수정된 게시글 내용",
  "authorId": 123,
  "createdAt": "2026-06-01T12:00:00",
  "updatedAt": "2026-06-02T12:00:00",
  "isAnonymous": false,
  "postType": "QUESTION",
  "images": ["new_image_url"],
  "voteId": "new_vote_id",
  "likeCount": 10,
  "commentCount": 5,
  "scrapCount": 3,
  "reportCount": 1,
  "status": "PUBLISHED"
}
```
### 6.5 게시글 삭제

#### 요구사항
- 작성자 본인만 게시글을 삭제할 수 있다.
- 게시글 삭제 시 댓글, 공감, 스크랩 등 관련 데이터도 함께 삭제된다.

- **URL**: `/api/posts/{postId}`
- **Method**: DELETE
- **Request Body**: 없음

**Response**:
- **Status Code**: 204 No Content
- **Body**: 없음

### 6.6 댓글 작성
#### 요구사항
- 게시글 상세 페이지에서 댓글 작성이 가능하다.
- 대댓글이 존재할 수 있다.
- 익명 선택시 익명 번호가 부여된다.

- **URL**: `/api/posts/{postId}/comments`
- **Method**: POST
- **Request Body**:
```json
{
  "content": "댓글 내용",
  "isAnonymous": true,
  "parentCommentId": null
}
```
**Response**:
- **Status Code**: 201 Created
- **Body**:
```json
{
  "id": 1,
  "postId": 1,
  "isAnonymous": true,
  "authorDisplayName": "익명 3",
  "content": "댓글 내용",
  "createdAt": "2026-06-01T13:00:00",
  "updatedAt": "2026-06-01T13:00:00",
  "parentCommentId": null
}
```

### 6.7 댓글 삭제
#### 요구사항
- 댓글 작성자 본인만 댓글을 삭제할 수 있다.

- **URL**: `/api/posts/{postId}/comments/{commentId}`
- **Method**: DELETE
- **Request Body**: 없음
**Response**:
- **Status Code**: 204 No Content
- **Body**: 없음

### 6.8 공감
#### 요구사항
- 게시글 상세 페이지에서 공감이 가능하다.
- 공감은 한 게시글당 한 번만 가능하다.

- **URL**: `/api/posts/{postId}/likes`
- **Method**: POST
- **Request Body**: 없음
**Response**:
- **Status Code**: 201 Created
- **Body**:
```json
{
  "id": 1,
  "postId": 1,
  "userId": 125,
  "createdAt": "2026-06-01T13:00:00"
}
```

### 6.9 공감 취소
#### 요구사항
- 게시글 상세 페이지에서 공감 취소가 가능하다.
- 본인이 누른 공감만 취소할 수 있다.

- **URL**: `/api/posts/{postId}/likes`
- **Method**: DELETE
- **Request Body**: 없음
**Response**:
- **Status Code**: 204 No Content
- **Body**: 없음

### 6.10 스크랩
#### 요구사항
- 게시글 상세 페이지에서 스크랩이 가능하다.
- 스크랩은 한 게시글당 한 번만 가능하다.

- **URL**: `/api/posts/{postId}/scraps`
- **Method**: POST
- **Request Body**: 없음
**Response**:
- **Status Code**: 201 Created
- **Body**:
```json
{
  "id": 1, 
    "postId": 1,
    "userId": 125,
    "createdAt": "2026-06-01T13:00:00"
}
```

### 6.11 스크랩 취소
#### 요구사항
- 게시글 상세 페이지에서 스크랩 취소가 가능하다.
- 본인이 스크랩한 항목만 취소할 수 있다.

- **URL**: `/api/posts/{postId}/scraps`
- **Method**: DELETE
- **Request Body**: 없음
**Response**:
- **Status Code**: 204 No Content
- **Body**: 없음


# 7. 고민 한 내용
## 7.1  설계 문서 실수
- 아직 Json으로 응답할 것이 아니기 때문에 명세서를 이렇게까지 작성 할 필요 없었음
- Auth가 없어서 익명 처리시 유저 id를 어떻게 처리할지 감을 못 잡았음
- Json에 유저 id가 들어가면 안되는데..? 라고 생각하다가 Auth 없이 설계하려다가 발생한 문젱미을 늦게 인지