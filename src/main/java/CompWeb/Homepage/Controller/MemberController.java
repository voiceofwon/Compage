package CompWeb.Homepage.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/member")
public class MemberController {

    @GetMapping("/login")
    public String login(@RequestParam(value="error",required = false)String error,
                        @RequestParam(value = "exception",required = false)String exception,
                        Model model){
        model.addAttribute("error",error);
        model.addAttribute("exception",exception);
        return "Member/login.html";
    }


}
