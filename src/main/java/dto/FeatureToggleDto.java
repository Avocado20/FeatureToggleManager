package dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class FeatureToggleDto {

    private String key;
    private String parentKey;

    private Boolean isEnabled;
    private String description;

}
