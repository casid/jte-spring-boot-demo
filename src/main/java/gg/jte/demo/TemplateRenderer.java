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

    // TODO determine if we run spring boot in development mode or not
    private final boolean devMode = false;

    private final TemplateEngine templateEngine;

    public TemplateRenderer() {
        if (devMode) {
            CodeResolver codeResolver = new DirectoryCodeResolver(Path.of("src", "main", "jte"));
            templateEngine = TemplateEngine.create(codeResolver, Paths.get("jte-classes"), ContentType.Html, getClass().getClassLoader());
            templateEngine.setBinaryStaticContent(true);
        } else {
            templateEngine = TemplateEngine.createPrecompiled(ContentType.Html);
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
