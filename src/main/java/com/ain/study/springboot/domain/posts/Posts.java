package com.ain.study.springboot.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Posts {

    /*
    Posts 클래스는 실제 DB의 테이블과 매칭될 클래스로, 보통 Entity 클래스라고도 한다.

    - @Entity: 테이블과 링크될 클래스임을 나타냄.
            기본값으로 클래스의 카멜케이스 이름을 언더스코어 네이밍으로 테이블 이름을 매칭한다. (ex. SalesManager.java > sales_manager table
    - @Id : 해당 테이블의 PK 필드
    - @GeneratedValue : PK의 생성 규칙 (스프링 부트 2.0 에서는 GenerationType.IDENTITY 옵션을 추가해야 auto_increment 가 된다
    - @Column : 테이블의 칼럼을 나타내며 굳이 선언하지 않더라도 해당 클래스의 필드는 모두 칼럼이 된다.
            기본값 외에 추가로 변경이 필요한 옵션이 있으면 사용한다.
            문자열의 경우 VARCHAR(255)가 기본값인데 사이즈를 늘리고 싶거나, 타입을 TEXT로 변경하고싶거나 등의 경우에 사용한다.

    (tip) 웬만하면 Entity의 PK는 Long타입의 Auto_increment 추천!
    (tip) Entity 클래스에서는 절대 Setter 메소드를 만들지 않는다!
            > 해당 클래스의 인스턴스 값들이 언제 어디서 변해야 하는지 코드상으로 명확하게 구분할수 없기 때문에

    - @NoArgsConstructor : 기본 생성자 자동 추가
    - @Builder : 해당 클래스의 빌더 패텅 클래스를 생성. 생성자 상단에 선언시 생성자에 포함된 빌드만 빌더에 포함한다.
            빌더를 사용하여 어느 필드에 어떤 값을 채워야 할지 명확하게 인지할 수 있다.
    (tip) Setter 없이 값을 채워 DB에 사용하는 법?
            > 생성자를 통해 최종값을 채운 후 DB에 삽입한다. 값 변경이 필요한 경우 해당 이벤트에 맞는 public 메소드를 호출하여 변경함!
            > 여기서는 채워야 할 필드가 무엇인지 명확히 지정할 수 없는 생성자 대신 빌더 클래스를 사용한 것.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
