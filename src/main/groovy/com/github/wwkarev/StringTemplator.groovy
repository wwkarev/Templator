package com.github.wwkarev

/**
 * StringTemplator is engine for building string object from strings.
 * @author Vitalii Karev
 */
class StringTemplator implements Templator {
    protected Map<String, String> params = [:]
    protected String tempalateString

    protected StringTemplator(String tempalateString) {
        this.tempalateString = tempalateString
    }

    static StringTemplator getInstance(String templateString) {
        return new StringTemplator(templateString)
    }

    StringTemplator setParam(String param, String value) {
        this.params[param] = value
        return this
    }

    String render() {
        return this.insertParams(tempalateString)
    }

    protected String insertParams(String templateString) {
        params.each{param, value ->
            templateString = templateString.replaceAll("%$param%", value)
        }
        return templateString
    }


}
