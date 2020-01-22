package org.pathref

import org.pathref.model.SimpleNestedObject
import org.pathref.model.SimpleObject
import spock.lang.Specification

class PropertySetterSpec extends Specification {
    void setup() {
    }

    def "given a key to set and a class to instantiate, when we call PropertySetter.setProperty, then the Object is instantiated and the property is set"() {
        given:
        def key = "stringProperty"
        def clazz = SimpleObject.class
        def value = "BANANAS"

        when:
        SimpleObject instantiatedClass = PropertySetter.setProperty(key, value, clazz)

        then:
        instantiatedClass.getStringProperty() == "BANANAS"
    }

    def "given a key to set and a class to instantiate, when we call PropertySetter.setProperty with a nested object structure, then the Object is instantiated and the nested property is set"() {
        given:
        def key = "simpleObject.stringProperty"
        def clazz = SimpleNestedObject.class
        def value = "BANANAS"

        when:
        SimpleNestedObject instantiatedClass = PropertySetter.setProperty(key, value, clazz)

        then:
        instantiatedClass.getSimpleObject().getStringProperty() == "BANANAS"
    }
}
