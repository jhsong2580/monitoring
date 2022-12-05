package monitoring.dto;

import lombok.Data;

@Data
public class StationDetailDTO {
    private String name;

    public StationDetailDTO(String name) {
        this.name = name;
    }
}
