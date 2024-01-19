package hello.core.order;

import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.discount.DiscountPolicy;

public class OrderServiceImpl implements OrderService {

  private final MemberRepository memberRepository;
  private final DiscountPolicy discountPolicy;  //인터페이스에만 의존하도록 코드 변경
  //private final DiscountPolicy discountPolicy = new FixDiscountPolicy(); 인터페이스뿐만 아니라 구체 클래스도 함께의존하고있음 DIP위반
  //private final DiscountPolicy discountPolicy = new RateDiscountPolicy(); //OCP위반

  public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
    this.memberRepository = memberRepository;
    this.discountPolicy = discountPolicy;
  }

  @Override
  public Order createOrder(Long memberId, String itemName, int itemPrice) {
    Member member = memberRepository.findById(memberId);
    int discountPrice = discountPolicy.discount(member, itemPrice);

    return new Order(memberId, itemName, itemPrice, discountPrice);
  }

  //테스트 용도
  public MemberRepository getMemberRepository() {
    return memberRepository;
  }
}
