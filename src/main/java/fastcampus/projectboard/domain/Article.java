package fastcampus.projectboard.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString(callSuper = true)     // 상속 받은 필드도 ToString 적용 (생성일, 생성자 등)
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy"),
})
@Entity
public class Article extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter @ManyToOne(optional = false) private UserAccount userAccount;   // 유저 정보 (ID)

    @Setter
    @Column(nullable = false)
    private String title;                     // 제목, NotNull 필드로 지정
    @Setter
    @Column(nullable = false, length = 10000)
    private String content;   // 본문, NotNull, 10000자 제한
    @Setter
    private String hashtag;                                             // 해시태그, Null

    /**
     * 댓글은 게시글에 종속되어 있기 때문에 cascade옵션으로 게시글이 삭제될 때 댓글을 함께 지울 수 있다.
     * 하지만 실무에서는 이렇게 하지 않을 수도 있다. 글이 지워져도 댓글은 백업해야 할 수도 있다. 따라서 옵션이다.
     */
    @OrderBy("createdAt DESC")
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    @ToString.Exclude
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();

    /**
     * 하이버네이트 구현체의 경우 모든 엔티티는 기본 생성자를 가져야 한다.
     * 하지만 외부에는 노출하지 않을 거기 때문에 protected로 생성한다.
     * private은 불가하다.
     */
    protected Article() {
    }

    public Article(UserAccount userAccount, String title, String content, String hashtag) {
        this.userAccount = userAccount;
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    public static Article of(UserAccount userAccount, String title, String content, String hashtag) {
        return new Article(userAccount, title, content, hashtag);
    }

    /**
     * 현재 엔티티는 객체끼리 비교할 때 id만 비교하면 같은 객체인지 확인할 수 있다.
     * 따라서 equals와 hashCode를 재정의하면서 id 필드만 사용한다.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        /** id는 데이터베이스에 들어갈 때 생성된다.
         * 따라서 id가 없다는 것은 데이터베이스에서 관리되고 있지 않다는 것이다.
         * id != null은 아이디가 없다면 같은 객체라도 같은 객체로 보지 않겠다는 뜻이다. */
        return id != null && id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
