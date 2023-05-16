package deliverySystem.moacall.service;


import deliverySystem.moacall.domain.Address;
import deliverySystem.moacall.domain.Member;
import deliverySystem.moacall.domain.Order;
import deliverySystem.moacall.domain.PaymentType;
import deliverySystem.moacall.repository.MemberRepository;
import deliverySystem.moacall.repository.OrderRepository;
import deliverySystem.moacall.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long order(Long memberId, String clientCity, String clientStreet, String clientZipcode, String clientDetailAddress, String memo, int clientPrice, int deliveryPrice, PaymentType paymentType) {
        Member member = memberRepository.findOne(memberId);

        Order order = Order.createOrder(member, memo, clientCity, clientStreet, clientZipcode, clientDetailAddress, clientPrice, deliveryPrice, paymentType);

        orderRepository.save(order);
        return order.getId();
    }

    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAll(orderSearch);
    }


}
