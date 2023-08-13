package CompWeb.Homepage.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


//로그인 페이지
@Controller
@RequestMapping("/member")
public class MemberController {

    @GetMapping("/login")
    public String login(@RequestParam(value="error",required = false)String error,
                        @RequestParam(value = "exception",required = false)String exception,
                        Model model){

        //로그인 실패시 로그인 창 아래에 오류 메시지 표출을 위한 model
        model.addAttribute("error",error);
        model.addAttribute("exception",exception);
        return "Member/login.html";
    }


}
