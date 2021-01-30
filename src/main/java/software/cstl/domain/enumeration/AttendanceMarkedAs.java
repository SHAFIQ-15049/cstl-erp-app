package software.cstl.domain.enumeration;

/**
 * The AttendanceMarkedAs enumeration.
 */
public enum AttendanceMarkedAs {
    R, WR, HR, WO, HO;

    public static AttendanceMarkedAs lookup(String label){
        for(AttendanceMarkedAs attendanceMarkedAs : values()){
            if( attendanceMarkedAs.toString().equals(label)){
                return attendanceMarkedAs;
            }
        }
        return null;
    }
}
