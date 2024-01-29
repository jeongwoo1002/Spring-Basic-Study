package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.discount.DiscountPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
//@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {


  private final MemberRepository memberRepository;
  private final DiscountPolicy discountPolicy;  //인터페이스에만 의존하도록 코드 변경
  //private final DiscountPolicy discountPolicy = new FixDiscountPolicy(); 인터페이스뿐만 아니라 구체 클래스도 함께의존하고있음 DIP위반
  //private final DiscountPolicy discountPolicy = new RateDiscountPolicy(); //OCP위반
//  @RequiredArgsConstructor 이걸 사용하면 아래 코드가 들어가 있어서 쓸 필요 없음
  @Autowired
  public OrderServiceImpl(@Qualifier("memoryMemberRepository") MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
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
