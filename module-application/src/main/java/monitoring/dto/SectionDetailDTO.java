package monitoring.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import monitoring.domain.Section;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SectionDetailDTO {
    private String downStationName;
    private String upStationName;
    private String lineName;

    public SectionDetailDTO(String downStationName, String upStationName, String lineName) {
        this.downStationName = downStationName;
        this.upStationName = upStationName;
        this.lineName = lineName;
    }

    public static SectionDetailDTO from(Section section){
        return new SectionDetailDTO(section.getDownStation().getName(), section.getUpStation().getName(), section.getLine().getName());
    }
}
