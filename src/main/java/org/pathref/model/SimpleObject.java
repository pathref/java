package org.pathref.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Setter
@Getter
public class SimpleObject {
    private int intProperty;
    private String stringProperty;
    private List<String> stringList;
    private List<SimpleObjectWithProperties> objectWithPropertiesList;
}
