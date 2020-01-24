package org.pathref.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class SimpleObjectWithProperties {

    private String valueToSearchBy;
    private String valueToReturn;
}
