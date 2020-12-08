package software.cstl.service.extended;

import io.github.jhipster.security.RandomUtil;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.cstl.domain.PersonalInfo;
import software.cstl.repository.PersonalInfoRepository;
import software.cstl.service.PersonalInfoService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
@Transactional
public class PersonalInfoExtService extends PersonalInfoService {

    @Value("${cstl.file-path}")
    String filePath;

    public PersonalInfoExtService(PersonalInfoRepository personalInfoRepository) {
        super(personalInfoRepository);
    }

    public PersonalInfo save(PersonalInfo personalInfo) {
        try{
            storeFiles(personalInfo);
        }catch (IOException e){
            e.printStackTrace();
        }
        return super.save(personalInfo);
    }

    private void storeFiles(PersonalInfo personalInfo) throws IOException {
     byte[] emptyFile = new byte[0];
     if(personalInfo.getPhoto().length>0){
        if(personalInfo.getPhotoId()!=null){
            String path = filePath+ personalInfo.getPhotoId();
            Path filePath = Paths.get(path);
            Files.write(filePath, personalInfo.getPhoto());
        }else{
            personalInfo.setPhotoId(RandomUtil.generateRandomAlphanumericString());
            String path = filePath+ personalInfo.getPhotoId();
            Path filePath = Paths.get(path);
            Files.write(filePath, personalInfo.getPhoto());
        }
     }

     if(personalInfo.getNationalIdAttachment()!=null){
         if(personalInfo.getNationalIdAttachmentId()!=null){
             String path = filePath+ personalInfo.getNationalIdAttachmentId()+personalInfo.getNationalIdAttachmentContentType();
             Path filePath = Paths.get(path);
             Files.write(filePath, personalInfo.getNationalIdAttachment());
             // personalInfo.setNationalIdAttachment(byte[]);
         }else{
             personalInfo.setNationalIdAttachmentId(RandomUtil.generateRandomAlphanumericString());
             String path = filePath+ personalInfo.getNationalIdAttachmentId()+personalInfo.getNationalIdAttachmentContentType();
             Path filePath = Paths.get(path);
             Files.write(filePath, personalInfo.getNationalIdAttachment());
         }
     }

        if(personalInfo.getBirthRegistrationAttachment()!=null){
            if(personalInfo.getBirthRegistrationAttachmentId()!=null){
                String path = filePath+ personalInfo.getBirthRegistrationAttachmentId()+personalInfo.getBirthRegistrationAttachmentContentType();
                Path filePath = Paths.get(path);
                Files.write(filePath, personalInfo.getBirthRegistrationAttachment());
            }else{
                personalInfo.setBirthRegistrationAttachmentId(RandomUtil.generateRandomAlphanumericString());
                String path = filePath+ personalInfo.getBirthRegistrationAttachmentId()+personalInfo.getBirthRegistrationAttachmentContentType();
                Path filePath = Paths.get(path);
                Files.write(filePath, personalInfo.getBirthRegistrationAttachment());
            }
        }

        personalInfo.setPhoto(emptyFile);
        personalInfo.setNationalIdAttachment(emptyFile);
        personalInfo.setBirthRegistrationAttachment(emptyFile);
    }

    @Override
    public Optional<PersonalInfo> findOne(Long id) {
        Optional<PersonalInfo> optionalPersonalInfo =  super.findOne(id);
        try{
            PersonalInfo personalInfo = optionalPersonalInfo.get();
            attachFiles(personalInfo);
            optionalPersonalInfo = Optional.of(personalInfo);
        }catch (IOException e){
            e.printStackTrace();
        }
        return optionalPersonalInfo;
    }

    @Override
    public Page<PersonalInfo> findAll(Pageable pageable) {
        Page<PersonalInfo> personalInfoPage =  super.findAll(pageable);
        personalInfoPage.get().forEach(p->{
            try{
                attachFiles(p);
            }catch (IOException e){
                e.printStackTrace();
            }
        });
        return personalInfoPage;
    }

    private void attachFiles(PersonalInfo personalInfo) throws IOException {
        if(personalInfo.getPhotoId()!=null){
            File photo = new File(filePath+personalInfo.getPhotoId());
            personalInfo.setPhoto(Files.readAllBytes(photo.toPath()));
        }
        if(personalInfo.getNationalIdAttachmentId()!=null){
            File natioalIdAttachment = new File(filePath+personalInfo.getNationalIdAttachmentId()+personalInfo.getNationalIdAttachmentContentType());
            personalInfo.setNationalIdAttachment(Files.readAllBytes(natioalIdAttachment.toPath()));
        }

        if(personalInfo.getBirthRegistrationAttachmentId()!=null){
            File birthRegistrationAttachment = new File(filePath+personalInfo.getBirthRegistrationAttachmentId()+personalInfo.getBirthRegistrationAttachmentContentType());
            personalInfo.setBirthRegistrationAttachment(Files.readAllBytes(birthRegistrationAttachment.toPath()));
        }
    }
}
