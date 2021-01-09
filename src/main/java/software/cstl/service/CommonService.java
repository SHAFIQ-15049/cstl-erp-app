package software.cstl.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CommonService {

    private final Logger log = LoggerFactory.getLogger(CommonService.class);

    public CommonService() {}

    public String[] getStringArrayBySeparatingStringContentUsingSeparator(String content, String separator) {
        log.debug("Request to separate a string content {} using given separator {}", content, separator);
        return content.split(separator);
    }

    public String getByteArrayToString(byte[] bytes) {
        log.debug("Request to convert byte array to string {} ", bytes);
        return new String(bytes);
    }
}
