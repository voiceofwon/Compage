package CompWeb.Homepage.Controller;

import CompWeb.Homepage.Service.HistroyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final HistroyService histroyService;

    @GetMapping("/home")
    public String home(){
        return "test.html";
    }

    @GetMapping("/intro")
    public String intro(Model model){
        model.addAttribute("historyList",histroyService.getHistoryList());
        return "introduction.html";
    }

}
