package dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookedDto {
    private String email;
    private String startDate;
    private String endDate;

}

