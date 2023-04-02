package org.example.doclet;

import java.util.List;

import jdk.javadoc.doclet.Doclet.Option;

public class GenericOption implements Option{

    private String name;
    private String description;
    private String value;

    public GenericOption(String name, String description, String defaultValue) {
        this.name = name;
        this.description = description;
        this.value = defaultValue;
    }

    @Override
    public int getArgumentCount() {
        return 1;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Kind getKind() {
        return Kind.STANDARD;
    }

    @Override
    public List<String> getNames() {
        return List.of(this.name);
    }

    @Override
    public String getParameters() {
        return "";
    }

    @Override
    public boolean process(String optionName, List<String> values) {
        if (values.size() > 1) {
            this.value = values.get(0);
            return true;
        }
        else {
            return false;
        }
    }
    
    public String getValue() {
        return this.value;
    }
}
