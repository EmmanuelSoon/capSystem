package team2.capSystem.helper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class batchCreator {
    private String startDate;
    private String endDate;
    private int size;
}
