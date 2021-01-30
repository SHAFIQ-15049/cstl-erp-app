package software.cstl.service.dto.salary;

import java.math.BigDecimal;

public class SalaryDetailsDto {
    String totalSalary;
    String mainSalary;
    String houseRent;

    public SalaryDetailsDto() {
    }

    public String getTotalSalary() {
        return totalSalary;
    }

    public void setTotalSalary(String totalSalary) {
        this.totalSalary = totalSalary;
    }

    public String getMainSalary() {
        return mainSalary;
    }

    public void setMainSalary(String mainSalary) {
        this.mainSalary = mainSalary;
    }

    public String getHouseRent() {
        return houseRent;
    }

    public void setHouseRent(String houseRent) {
        this.houseRent = houseRent;
    }
}
