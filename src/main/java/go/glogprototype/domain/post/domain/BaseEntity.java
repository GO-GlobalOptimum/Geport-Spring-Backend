package go.glogprototype.domain.post.domain;


import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import java.time.LocalDateTime;

@MappedSuperclass // 이 어노테이션은 이 클래스에 있는 속성을 다른 엔티티에서도 쓰고싶을때 붙이는 어노테이션이다
                  // 이 클래스는 엔티티가 아니므로 테이블이 생성되지 않는다 부모 클래스를 상속받는 자식클래스의 매핑정보만 제공한다.
                  // 추상 클래스로 만들어서 사용하는것을 권장한다(직접 생성할 일이 없음)
                  // @Entity 클래스는 엔티티나 @MappedSuperclass 로 지정한 클래스만 상속가능"
public abstract class BaseEntity {


    private String createdBy;
    private LocalDateTime createdDate;

    private String lastModifiedBy;
    private LocalDateTime lastModifiedDate;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
