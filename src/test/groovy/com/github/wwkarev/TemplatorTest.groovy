package com.github.wwkarev

import org.apache.commons.io.FilenameUtils
import spock.lang.Specification

class TemplatorTest extends Specification {
    private final String TEMPLATE_PATH = 'src/test/groovy/com/github/wwkarev/templates/'

    def "test StringTemplator"() {
        when:
        String originalString = "<div>Hello<b>World</b></div>"

        String templateString = "<div>%first_word%<b>%second_word%</b></div>"
        String rendererString = StringTemplator.getInstance(templateString)
                .setParam("first_word", "Hello")
                .setParam("second_word", "World")
                .render()
        then:
        assert originalString == rendererString
    }

    def "test FileTemplator"() {
        when:
        String originalFileName = FilenameUtils.concat(TEMPLATE_PATH, 'filetemplator/original.html')
        String originalString = new File(originalFileName).getText().trim()

        String templateFileName = FilenameUtils.concat(TEMPLATE_PATH, 'filetemplator/template.html')
        String rendererString = FileTemplator.getInstance(new File(templateFileName))
                .setParam("first_word", "Hello")
                .setParam("second_word", "World")
                .render()
        then:
        assert originalString == rendererString
    }
}
