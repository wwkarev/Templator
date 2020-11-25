StringTemplator is engine for building string object from strings or files.
StringTemplate supports:
* parameterizable templates
* import template from another template (including parts of templates)

##### parameterizable templates
Parameter is possible to define **`%param_name%`** statement in template

Example:
```
String templateString = "Hello %param%"
Templator templator = StringTemplator.getInstance(templateString)
templator.setParam("param", "World")
assert templator.render() === "Hello World"
```

##### Template import
FileTemplator supports import of other templates. Imports is defined by **%{relative_file.name}** statement.

Example:
```
Templator templator = FileTemplator.getInstance(new File("template.html"))
templator.setParam("first_word", "Hello")
templator.setParam("second_word", "World")
File destFile = new File("output.html")
destFile.write(templator.render())
```
template.html
```
<div>
    %first_word%
    %{sub_template.html}
</div>
```
sub_template.html
```
<b>%second_word%</b>
```
output.html
```
<div>
    Hello
    <b>World</b>
</div>
```

FileTemplator supports import part of template. It's usefull when you want to wrap some other template.
Templates splitting into parts by **%|** statement and available by **%{relative_file.name}[statement_index]**.

Example:
template.html
```
%{box.html}[0]
    %first_word%
    <b>%second_word%</b>
%{box.html}[1]
```
box.html
```
<div style="border: 1px solid #000000; display: inline-block">%|</div>
```
output.html
```
<div style="border: 1px solid #000000; display: inline-block">
    Hello
    <b>World</b>
</div>
```

Notice %{file.name} equals to %{file.name}[0]  
