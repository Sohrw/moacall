package deliverySystem.moacall.controller;


import deliverySystem.moacall.domain.Address;
import deliverySystem.moacall.domain.Member;
import deliverySystem.moacall.domain.MemberStatus;
import deliverySystem.moacall.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("form", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping(value = "/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "members/createMemberForm";
        }
        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode(), form.getDetailAddress());
        Member member = new Member();
        member.setName(form.getName());
        member.setPassword(form.getPassword());
        member.setMemberStatus(MemberStatus.valueOf(form.getMemberStatus()));
        member.setUserId(form.getUserId());
        member.setFoodAddress(address);


        memberService.join(member);
        return "redirect:/";
    }
}
