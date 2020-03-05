package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
// 트랜잭션을 붙여 놓으면 기본적으로 메서드 안에 다 먹힘. (read가 많으니 read에 최적화된 것을 기본으로 설정)
@Transactional(readOnly = true) // 데이터 변경은 기본적은 Transaction 안에 있어야 함.
@RequiredArgsConstructor // 롬복 사용. 참고) @AllArgsConstructor -> 생성자를 알아서 만들어줌.
public class MemberService {

    private final MemberRepository memberRepository;

    // 요새 많이 쓰는 생성자 Injection. 최근 스프링은 Autowired 어노테이션 안붙여도 생성자가 하나면 알아서 주입해줌.
//    @Autowired
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    /*
    @Autowired // Setter Injection. 장점 : test 시 주입이 편함. 단점 : runtime 시 누가 넣을 수 있음.
    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    */


    /*
        회원 가입
        * */
    @Transactional // 따로 설정한 것은 우선권을 가져 먹힘.
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);

        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers =
                memberRepository.findByName(member.getName());

        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findOne(id);
        member.setName(name);

    }
}
