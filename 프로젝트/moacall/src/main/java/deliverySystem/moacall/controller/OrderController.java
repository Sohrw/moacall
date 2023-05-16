package deliverySystem.moacall.controller;


import deliverySystem.moacall.domain.Address;
import deliverySystem.moacall.domain.Member;
import deliverySystem.moacall.service.MemberService;
import deliverySystem.moacall.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final MemberService memberService;

    @GetMapping("/order/new")
    public String createForm(Model model) {
        List<Member> members = memberService.findMembers();

        model.addAttribute("members", members);
        model.addAttribute("form", new OrderForm());

        return "order/createOrderForm";
    }

    @PostMapping("/order")
    public String order(@Valid OrderForm form, @RequestParam("memberId") Long memberId, BindingResult result) {
        if (result.hasErrors()) {
            return "order/createOrderForm";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        orderService.order(memberId, form.getClientCity(), form.getClientStreet(), form.getClientZipcode(), form.getClientDetailAddress(), form.getMemo(), form.getClientPrice(), form.getDeliveryPrice(), form.getPaymentType() );

        return "redirect:/";
    }


}
