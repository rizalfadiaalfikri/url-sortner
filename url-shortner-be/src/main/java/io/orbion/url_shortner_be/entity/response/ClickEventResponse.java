package io.orbion.url_shortner_be.entity.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClickEventResponse {

    private LocalDate clickDate;

    private Long count;

}
