package gg.jte.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
public class DemoController {

    @Autowired
    TemplateRenderer templateRenderer;
    @Autowired
    VisitsRepository visitsRepository;

    @Value("${spring.profiles.active:PROFILE_NOT_SET}")
    private String profile;

    @ResponseBody
    @GetMapping(value = "/profile")
    public String profile() {
        return profile;
    }

    @GetMapping("/")
    public void view(HttpServletResponse response) {
        visitsRepository.add();

        DemoModel model = new DemoModel();
        model.name = "unknown visitor";
        model.visits = visitsRepository.get();

        templateRenderer.render("demo.jte", model, response);
    }
}
