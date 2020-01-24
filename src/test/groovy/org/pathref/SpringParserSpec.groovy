package org.pathref

import org.pathref.model.SimpleNestedObject
import org.pathref.model.SimpleObject
import org.pathref.model.SimpleObjectWithProperties
import spock.lang.Specification

class SpringParserSpec extends Specification {

    void setup() {
    }

    def "given a key to set and a class to instantiate, when we call SpringParser.setProperty, then the Object is instantiated and the property is set"() {
        given:
        def key = "stringProperty"
        def clazz = SimpleObject.class
        def value = "BANANAS"

        when:
        SimpleObject instantiatedClass = SpringParser.setProperty(clazz, key, value)

        then:
        instantiatedClass.getStringProperty() == "BANANAS"
    }

    def "given a key to set and a class to instantiate, when we call SpringParser.setProperty with list item to set, then the Object is instantiated and the nested property is set"() {
        given:
        def key = "stringList[0]"
        def clazz = SimpleObject.class
        def value = "BANANAS"

        when:
        SimpleObject instantiatedClass = SpringParser.setProperty(clazz, key, value)

        then:
        instantiatedClass.getStringList().get(0) == "BANANAS"
    }

    def "given a key to set and a class to instantiate, when we call SpringParser.setProperty with a nested object structure, then the Object is instantiated and the nested property is set"() {
        given:
        def key = "simpleObject.stringProperty"
        def clazz = SimpleNestedObject.class
        def value = "BANANAS"

        when:
        SimpleNestedObject instantiatedClass = SpringParser.setProperty(clazz, key, value)

        then:
        instantiatedClass.getSimpleObject().getStringProperty() == "BANANAS"
    }

    def "given a key and an object with a type to read, when we call SpringParser.getProperty, then the property is returned"() {
        given:
        def key = "stringProperty"
        def object = SimpleObject.builder().stringProperty("BANANAS").build()

        when:
        String returnedType = SpringParser.getProperty(object, key, String.class)

        then:
        returnedType == "BANANAS"
    }

    def "given a key and an object with a primitive type to read, when we call SpringParser.getProperty, then the property is returned"() {
        given:
        def key = "intProperty"
        def object = SimpleObject.builder().intProperty(10).build()

        when:
        int returnedType = SpringParser.getProperty(object, key, Integer.class)

        then:
        returnedType == 10
    }

    def "given a key and a nested object with a list to read, when we call SpringParser.getProperty, then the property is returned"() {
        given:
        def key = "simpleObject.stringList[0]"
        def stringList = Collections.singletonList("BANANAS")
        def object = SimpleObject.builder().stringList(stringList).build()
        def simpleNestedObject = SimpleNestedObject.builder().simpleObject(object).build()

        when:
        String returnedType = SpringParser.getProperty(simpleNestedObject, key, String.class)

        then:
        returnedType == "BANANAS"
    }

    def "given a key and a nested object with a list to read, when we call SpringParser.getProperty providing conditional logic in the path property, then the property is returned"() {
        given:
        def key = "objectWithPropertiesList.?[valueToSearchBy == 'BANANAS'].get(0).valueToReturn"
        def objectWithPropertiesList = Collections.singletonList(SimpleObjectWithProperties.builder().valueToSearchBy("BANANAS").valueToReturn("RIPE BANANAS").build())
        def simpleObject = SimpleObject.builder().objectWithPropertiesList(objectWithPropertiesList).build()

        when:
        String returnedType = SpringParser.getProperty(simpleObject, key, String.class)

        then:
        returnedType == "RIPE BANANAS"
    }
}
