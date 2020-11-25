package com.github.wwkarev

/**
 * Templator is engine for building string object from templates.
 * @author Vitalii Karev
 */
interface Templator {
    /**
     * Setting params to template
     * @param param
     * @param value
     * @return this Templator object
     */
    abstract Templator setParam(String param, String value)

    /**
     * Rendering result String
     * @return rendered result String
     */
    String render()
}
