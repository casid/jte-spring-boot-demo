package gg.jte.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
public class DemoController {

    @Autowired
    TemplateRenderer templateRenderer;
    @Autowired
    VisitsRepository visitsRepository;

    @GetMapping("/")
    public void view(HttpServletResponse response) {
        visitsRepository.add();

        DemoModel model = new DemoModel();
        model.name = "unknown visitor";
        model.visits = visitsRepository.get();

        templateRenderer.render("demo.jte", model, response);
    }
}
