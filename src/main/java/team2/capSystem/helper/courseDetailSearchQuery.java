package team2.capSystem.helper;


import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class courseDetailSearchQuery {
    private String keyword;
    private String startDate;
    private String endDate;

    
    public LocalDate getLocalStartDate(){
        return LocalDate.parse(this.startDate);
    }

    public LocalDate getLocalEndDate(){
        return LocalDate.parse(this.endDate);
    }

    public boolean keywordNullOrEmpty(){
        if (this.keyword == null || this.keyword == ""){
            return true;
        }

        return false;
    }
    public boolean startNullOrEmpty(){
        if (this.startDate == null || this.startDate == ""){
            return true;
        }

        return false;
    }
    public boolean endNullOrEmpty(){
        if (this.endDate == null || this.endDate == ""){
            return true;
        }

        return false;
    }

}
