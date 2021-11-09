package gg.jte.demo;

import gg.jte.CodeResolver;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.output.Utf8ByteOutput;
import gg.jte.resolve.DirectoryCodeResolver;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class TemplateRenderer {

    private final TemplateEngine templateEngine;

    public TemplateRenderer() {
        String profile = System.getenv("SPRING_ENV");
        if ("prod".equals(profile)) {
            // Templates will be compiled by the maven build task
            templateEngine = TemplateEngine.createPrecompiled(ContentType.Html);
        } else {
            // Here, a JTE file watcher will recompile the JTE templates upon file save (the web browser will auto-refresh)
            // If using IntelliJ, use Ctrl-F9 to trigger an auto-refresh when editing non-JTE files.
            CodeResolver codeResolver = new DirectoryCodeResolver(Path.of("src", "main", "jte"));
            templateEngine = TemplateEngine.create(codeResolver, Paths.get("jte-classes"), ContentType.Html, getClass().getClassLoader());
            templateEngine.setBinaryStaticContent(true);
        }
    }

    public void render(String name, Object model, HttpServletResponse response) {
        Utf8ByteOutput output = new Utf8ByteOutput();
        templateEngine.render(name, model, output);

        response.setContentType("text/html");
        response.setContentLength(output.getContentLength());

        try {
            output.writeTo(response.getOutputStream());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
