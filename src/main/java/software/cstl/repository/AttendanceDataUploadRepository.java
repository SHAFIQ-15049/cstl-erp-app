package software.cstl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import software.cstl.domain.AttendanceDataUpload;

/**
 * Spring Data  repository for the AttendanceDataUpload entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttendanceDataUploadRepository extends JpaRepository<AttendanceDataUpload, Long> {
}
