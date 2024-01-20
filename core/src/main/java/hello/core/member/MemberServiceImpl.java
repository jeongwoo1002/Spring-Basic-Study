package hello.core.member;

import hello.core.member.discount.FixDiscountPolicy;
import hello.core.order.Order;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService{

  private final MemberRepository memberRepository;

  @Autowired //ac.getBean(MemberRepository.class)
  public MemberServiceImpl(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  public OrderService orderService() {
    return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
  }

  @Override
  public void join(Member member) {
    memberRepository.save(member);

  }

  @Override
  public Member findMember(Long memberId) {
    return memberRepository.findById(memberId);
  }

  //테스트 용도
  public MemberRepository getMemberRepository() {
    return memberRepository;
  }

}
