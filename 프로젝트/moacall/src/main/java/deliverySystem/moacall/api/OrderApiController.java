package deliverySystem.moacall.api;


import deliverySystem.moacall.domain.Address;
import deliverySystem.moacall.domain.Order;
import deliverySystem.moacall.domain.OrderStatus;
import deliverySystem.moacall.domain.PaymentType;
import deliverySystem.moacall.repository.OrderRepository;
import deliverySystem.moacall.repository.OrderSearch;
import deliverySystem.moacall.service.OrderService;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.http.GET;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final OrderService orderService;

    @GetMapping("/api/orders")
    public List<OrderDto> orderDtoList() {
        List<Order> orders = orderRepository.findAll(new OrderSearch());

        return orders.stream()
                .map(o -> new OrderDto(o))
                .collect(Collectors.toList());


    }

    @GetMapping("/api/orders/{memberId}")
    public List<OrderDto> orderDtoListForMember(@PathVariable("memberId") Long memberId)
    {
        List<Order> allWithMember = orderRepository.findAllWithMember();
        return allWithMember.stream()
                .map(o-> new OrderDto(o))
                .collect(Collectors.toList());
    }
    @PostMapping("api/orders/{orderId}")
    public void orderDtoListForDispatch(@PathVariable("orderId") Long orderId) {
        Order order = orderRepository.findOne(orderId);
        order.setOrderStatus(OrderStatus.AFTER_ACCEPT);
        if (order != null) {
            orderRepository.save(order);
        }


    }
    @PostMapping("api/orderpick/{orderId}")
    public void orderDtoListForPickUp(@PathVariable("orderId") Long orderId) {
        Order order = orderRepository.findOne(orderId);
        order.setOrderStatus(OrderStatus.DELIVERY);
        if (order != null) {
            orderRepository.save(order);
        }


    }

    @PostMapping("api/ordercomplete/{orderId}")
    public void orderDtoListForComplete(@PathVariable("orderId") Long orderId) {
        Order order = orderRepository.findOne(orderId);
        order.setOrderStatus(OrderStatus.COMPLITE);
        if (order != null) {
            orderRepository.save(order);
        }


    }


    @GetMapping("/api/orders/accept")
    public List<OrderDto> orderDtoListForAccept() {
        List<Order> allWithAccept = orderRepository.findAllWithAccept();
        return allWithAccept.stream()
                .map(o -> new OrderDto(o))
                .collect(Collectors.toList());

    }

    @GetMapping("/api/orders/dispatch")
    public List<OrderDto> orderDtoListForDispatch() {
        List<Order> allWithAccept = orderRepository.findAllWithDispatch();
        return allWithAccept.stream()
                .map(o -> new OrderDto(o))
                .collect(Collectors.toList());

    }

    @GetMapping("/api/orders/delivery")
    public List<OrderDto> orderDtoListForDelivery() {
        List<Order> allWithAccept = orderRepository.findAllWithDelivery();
        return allWithAccept.stream()
                .map(o -> new OrderDto(o))
                .collect(Collectors.toList());

    }
    @GetMapping("/api/orders/complete")
    public List<OrderDto> orderDtoListForComplete() {
        List<Order> allWithAccept = orderRepository.findAllWithComplete();
        return allWithAccept.stream()
                .map(o -> new OrderDto(o))
                .collect(Collectors.toList());

    }
    @Getter @Data
    static class OrderDto {
        private Long orderId;
        private Long memberId;
        private String foodName;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address clientAddress;
        private Address foodAddress;
        private int totalPrice;
        private int deliveryPrice;
        private String memo;
        private double distance;
        private PaymentType paymentType;
        private String latitude;
        private String longitude;

        public OrderDto(Order order) {
            orderId = order.getId();
            memberId = order.getMember().getId();
            foodName = order.getMember().getName();
            orderDate = order.getOrderDateTime();
            orderStatus = order.getOrderStatus();
            clientAddress = order.getClientAddress();
            totalPrice = order.getTotalPrice();
            deliveryPrice = order.getDeliveryPrice();
            memo = order.getMemo();
            distance = order.getDistance();
            foodAddress = order.getMember().getFoodAddress();
            paymentType = order.getPaymentType();
            latitude = order.getLatitude();
            longitude = order.getLongitude();

        }

    }
}
