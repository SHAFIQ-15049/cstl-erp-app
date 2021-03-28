package software.cstl.service.reports;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Service
public class ThymeleafReportService {

    private final SpringTemplateEngine templateEngine;

    public ThymeleafReportService(SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String parseThymeleafTemplate(Context context, String template){
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        return templateEngine.process(template, context);
    }
}
