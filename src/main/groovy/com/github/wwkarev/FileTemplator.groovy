package com.github.wwkarev

import org.apache.commons.io.FilenameUtils

import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * StringTemplator is engine for building string object from files.
 * @author Vitalii Karev
 */
final class FileTemplator extends StringTemplator {
    private File templateFile
    private List<String> parts
    private final String SPLIT_SIGN = '%\\|'
    private final String FILE_IMPORT_REGEX = "%\\{(.*?)\\}\\[?(\\d+)?\\]?"

    private FileTemplator(File templateFile, String templateString) {
        super(templateString)
        this.templateFile = templateFile
    }

    static FileTemplator getInstance(File templateFile) {
        String templateString = templateFile.getText().trim()

        FileTemplator templator = new FileTemplator(templateFile, templateString)
        templator.init()
        return templator
    }

    static FileTemplator getInstance(String templateFileName) {
        return FileTemplator.getInstance(new File(templateFileName))
    }

    @Override
    String render() {
        return insertParams(joinParts())
    }

    private String joinParts() {
        return parts.join('')
    }

    private void init() {
        this.parts = this.createParts(tempalateString)
    }

    private createParts(String data) {
        return this.createRowParts(data).collect {String part ->
            return initPart(part)
        }
    }

    private List<String> createRowParts(String fullData) {
        return fullData.split(SPLIT_SIGN)
    }

    private String initPart(String part) {
        for(int i = 0; i < 100; i++) {
            try {
                part = this.insertSubPart(part)
            } catch (IndexOutOfBoundsException e) {
                break
            }
        }
        return part
    }

    private String insertSubPart(String part) {
        String pathName = templateFile.getParentFile().getAbsolutePath()
        Matcher matcher = part =~ FILE_IMPORT_REGEX
        matcher.find()
        String key = matcher[0][0]
        String subBaseName = matcher[0][1]
        Integer subPartIndex = Integer.valueOf(matcher[0][2] ?: 0)
        String subFileName = FilenameUtils.concat(pathName, subBaseName)

        String subData = FileTemplator.getInstance(new File(subFileName)).getPart(subPartIndex)

        return part.replaceFirst(Pattern.quote(key), subData)
    }

    private String getPart(Integer index) {
        return parts[index]
    }
}
