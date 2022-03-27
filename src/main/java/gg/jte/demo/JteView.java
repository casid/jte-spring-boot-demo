package gg.jte.demo;

import gg.jte.TemplateEngine;
import gg.jte.output.Utf8ByteOutput;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.view.AbstractTemplateView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class JteView extends AbstractTemplateView {

    private final TemplateEngine templateEngine;

    public JteView(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    protected void renderMergedTemplateModel(Map<String, Object> model,
                                             HttpServletRequest request,
                                             HttpServletResponse response) throws Exception {

        String url = this.getUrl();

        Utf8ByteOutput output = new Utf8ByteOutput();
        templateEngine.render(url, model, output);

        response.setContentType(MediaType.TEXT_HTML_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentLength(output.getContentLength());

        output.writeTo(response.getOutputStream());
    }
}
