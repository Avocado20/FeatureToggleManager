package domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
public class FeatureToggle implements Serializable {

    private String key;

    private Boolean isEnabled = false;
    private String description;

    public FeatureToggle parent;

    public Set<FeatureToggle> subToggles = new HashSet<>();

}