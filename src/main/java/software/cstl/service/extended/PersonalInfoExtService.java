package software.cstl.service.extended;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import software.cstl.domain.PersonalInfo;
import software.cstl.repository.PersonalInfoRepository;
import software.cstl.service.PersonalInfoService;

@Log4j
public class PersonalInfoExtService extends PersonalInfoService {
    @Value("${application.file-path}")
    private String filePath;

    public PersonalInfoExtService(PersonalInfoRepository personalInfoRepository) {
        super(personalInfoRepository);
    }

    @Override
    public PersonalInfo save(PersonalInfo personalInfo) {
        storeFiles(personalInfo);
        return super.save(personalInfo);
    }

    private void storeFiles(PersonalInfo personalInfo){
     if(personalInfo.getPhoto().length>0){

     }
    }
}
