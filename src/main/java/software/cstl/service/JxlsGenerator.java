package software.cstl.service;

import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Service
public class JxlsGenerator {



    private void processTemplate(Context context, InputStream pTemplateLocation, OutputStream pOutputStream) throws IOException {
        JxlsHelper jxlsHelper = JxlsHelper.getInstance();
        /*Transformer transformer = jxlsHelper.createTransformer(pTemplateLocation, pOutputStream);
        setSilent(transformer);
        jxlsHelper.processTemplate(context, transformer);*/
        jxlsHelper.processTemplate(pTemplateLocation, pOutputStream, context);
    }
}


