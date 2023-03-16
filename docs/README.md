# CI/CD 구성
- Github webhook을 이용해서 main 브랜치 merge 되면 자동으로 배포

## Jenkins
- url : http://146.56.38.5:8080/
    - id : guest
    - pw : guest123

# nGrinder를 이용한 성능 테스트

## 주문 api
![nGrinder 성능 측정](https://user-images.githubusercontent.com/76800974/225563839-651cbcad-05ec-4198-b4cc-68b3ee2e47b9.png)

# scouter
![스크린샷 2023-03-16 오후 11 14 20](https://user-images.githubusercontent.com/76800974/225645089-95770f1d-e249-4046-8150-6cd226886df2.png)

- m1 mac에서 client 오류가 발생해 scouter paper를 client로 이용하였다.

# ERD
![erd](https://user-images.githubusercontent.com/76800974/225649818-29890c13-bcbf-4f91-9098-31adff99666b.png)

- time table을 따로 구성해 이력 관리를 할 수 있도록 구상하였다.

# 와이어 프레임
- 구현 실패

    
    
# 성능측정 및 개선 내용

처음에는 RDB를 이용한 주문 로직을 구상하였는데 성능 향상을 위하여 Redis를 도입하였다.
여기서부터 문제가 발생하였는데, Redis에서 NoSuchException이 발생하는 것이였다.
여기저기 검색해본 결과 Repository방식 spring data redis는 transaction을 지원하지 않고, lock을 걸어서 원자성을 보존해야 했다.
그래서 lock을 걸어서 원자성을 보존하는 방식으로 개발을 하던 중, 지인 개발자 분께서
"cache는 cache일 뿐이다. 굳이 lock을 걸어서 사용할 바에는 redis에 데이터가 없다면 RDB에서 가져오고, 가져온 값을 redis와 동기화를 하는 작업이 필요할 것 같다."
라고 말씀해 주셨다. 참고로 Cap theorem도 한번 찾아보라고 말씀해 주셨다...

```java
Optional<ProductWrapper> wrapper = productRedisRepository.findById(request.getProductId().toString());

Product product = new Product();

if(wrapper.isEmpty() || wrapper.get().getProduct() == null) {
    product = productRepository.findById(request.getProductId()).orElseThrow(() -> new NoSuchElementException());
    product.order(request.getOrderCount());
    asyncService.cache(member, product);
} else {
    product = wrapper.get().getProduct();
    product.order(request.getOrderCount());
    asyncService.persist(member, wrapper.get(), request);
}
```

주문 로직을 RDB로만 의존했을 때 TPS가 2000정도 나왔는데,
Redis를 도입하고 3700까지 향상되었다.

# 기술적으로 고민한(고생한) 부분들

Local과 Product에서 Rdb와 Redis를 분리하고 싶어서, H2 임베디드 DB와 임베디드 Redis를 처음 사용해 보았다.
스프링에서 제공해주는 임베디드 DB는 database properties를 따로 지정하지 않으면 자동으로 빈을 생성하고 실행해 주기 때문에, 큰 무리가 없었는데
이 임베디드 redis를 사용하면서 머리가 많이 아팠다. 처음 써보는 것도 있지만, Bean등록을 환경마다 다르게 해줘야 하는데 구현을 제대로 못한거 같아 아쉽다.
그리고 테스트 코드도 짜고 싶었는데, 챌린지 중간에 참여해서 1주일여 정도에 시간동안 기능 구현과 Redis 공부로 인해 테스트 코드를 구현해 보지 못한거 같다.
# Api 목록

- 회원
    - 회원가입 
        - url : /member/join 
        - method : post
        request
        ```json
        {
            "email" : "drow724@naver.com",
            "password" : "abcd1234"
        }
        ```
        response
        ```json
        {
            "id": 2,
            "email": "drow724@naver.com",
            "role": "USER"
        }
        ```
    - 로그인
        - url : /member/login 
        - method : post
        request
        ```json
        {
            "email" : "drow724@naver.com",
            "password" : "abcd1234"
        }
        ```
        response
        ```json
        {
            "id": 2,
            "email": "drow724@naver.com",
            "role": "USER"
        }
        ```
- 상품
    - 등록 
        - url : /product 
        - method : post
        request
        ```json
        {
            "name" : "상품1",
            "stock" : 1000000000
        }
        ```
        response
        ```json
        {
            "id": 1,
            "name": "상품1",
            "delYn": false,
            "stock": 1000000000
        }
        ```
    - 수정
        - url : /product 
        - method : put
        request
        ```json
        {
            "name" : "수정 상품",
            "stock" : 100000000
        }
        ```
        response
        ```json
        {
            "id": 1,
            "name": "수정 상품",
            "delYn": false,
            "stock": 100000000
        }
        ```
    - 삭제 (논리 삭제)
        - url : /product/{productId}
        - method : delete
        response
        ```json
        {
            "id": 1,
            "name": "수정 상품",
            "delYn": false,
            "stock": 100000000
        }
        ```
    - 조회
        - url : /product?
        - method : get
        request
            paging query
        response
        ```json
        {
            "content": [
                {
                    "id": 2,
                    "name": "상품1",
                    "delYn": false,
                    "stock": 1000000000
                }
            ],
            "pageable": {
                "sort": {
                    "sorted": true,
                    "unsorted": false,
                    "empty": false
                },
                "pageNumber": 0,
                "pageSize": 10,
                "offset": 0,
                "paged": true,
                "unpaged": false
            },
            "totalPages": 1,
            "totalElements": 1,
            "last": true,
            "numberOfElements": 1,
            "first": true,
            "number": 0,
            "sort": {
                "sorted": true,
                "unsorted": false,
                "empty": false
            },
            "size": 10,
            "empty": false
        }
        ```
- 타임딜
    - 등록
        - url : /time 
        - method : post
        request
        ```json
        {
            "productId" : 1,
            "date" : "2022-03-16",
            "time" : "23:22:14"
        }
        ```
        response
        ```json
        {
            "productId" : 1,
            "date" : "2022-03-16",
            "time" : "23:22:14"
        }
        ```
    - 수정
        - url : /time
        - method : put
        request
        ```json
        {
            "productId" : "1",
            "date" : "2023-03-13",
            "time" : "10:05:00"
        }
        ```
        response
        ```json
        {
            "productId" : "1",
            "date" : "2023-03-13",
            "time" : "10:05:00"
        }
        ```
- 주문
    - 주문하기
        - url : /order
        - method : post
        request
        ```json
        {
            "productId" : "2",
            "orderCount" : 1,
            "address" : {
                "city" : "seoul"
            }
        }
        ```
        response
        ```json
        {
            "id": 1,
            "address": {
                "city": "seoul"
            },
            "orderCount": 1,
            "deliveryYn": false
        }
        ```
    - 상품별 구매한 유저 리스트
        - url : /order/product/{productId}
        - method : get
        response
        ```json
        {
            "content": [
                {
                    "id": 1,
                    "email": "admin",
                    "role": "ADMIN"
                }
            ],
            "pageable": {
                "sort": {
                    "sorted": true,
                    "unsorted": false,
                    "empty": false
                },
                "pageNumber": 0,
                "pageSize": 10,
                "offset": 0,
                "paged": true,
                "unpaged": false
            },
            "totalPages": 1,
            "totalElements": 1,
            "last": true,
            "numberOfElements": 1,
            "first": true,
            "number": 0,
            "sort": {
                "sorted": true,
                "unsorted": false,
                "empty": false
            },
            "size": 10,
            "empty": false
        }
        ```
    - 유저의 구매한 상품리스트
        - url : /order/member/{memberId}
        - method : get
        response
        ```json
        {
            "content": [
                {
                    "id": 2,
                    "name": "상품1",
                    "orderCount": 1
                }
            ],
            "pageable": {
                "sort": {
                    "sorted": true,
                    "unsorted": false,
                    "empty": false
                },
                "pageNumber": 0,
                "pageSize": 10,
                "offset": 0,
                "paged": true,
                "unpaged": false
            },
            "totalPages": 1,
            "totalElements": 1,
            "last": true,
            "numberOfElements": 1,
            "first": true,
            "number": 0,
            "sort": {
                "sorted": true,
                "unsorted": false,
                "empty": false
            },
            "size": 10,
            "empty": false
        }
        ```
