package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private String address;

    @OneToMany(mappedBy = "member") // order table 에 있는 member field 에 매핑이 된 것.
    private List<Order> orders = new ArrayList<>();
}
