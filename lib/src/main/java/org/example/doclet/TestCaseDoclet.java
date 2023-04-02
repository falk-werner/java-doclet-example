package org.example.doclet;
 
import java.io.File;
import java.util.Locale;
import java.util.Set;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.QualifiedNameable;
import javax.tools.Diagnostic.Kind;

import com.sun.source.doctree.DocCommentTree;
import com.sun.source.util.DocTrees;

import jdk.javadoc.doclet.Doclet;
import jdk.javadoc.doclet.DocletEnvironment;
import jdk.javadoc.doclet.Reporter;
 
public class TestCaseDoclet implements Doclet {

    private Flag noTimestamp; 
    private GenericOption destinationDir;
    private Reporter reporter;

    @Override
    public void init(Locale locale, Reporter reporter) {
        this.noTimestamp = new Flag("-notimestamp", "Do not include hidden time stamp");
        this.destinationDir = new GenericOption("-d", "Destination directory for output files", "/tmp");
        this.reporter = reporter;
    }
 
    @Override
    public String getName() {
        return getClass().getSimpleName();
    }
 
    @Override
    public Set<? extends Option> getSupportedOptions() {
        return Set.of(noTimestamp, destinationDir);
    }
 
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }
  
    @Override
    public boolean run(DocletEnvironment environment) {
        boolean result = true;
        DocTrees commentProvider = environment.getDocTrees();

        try  {
            TestCaseWriter writer = new TestCaseWriter();

            for (Element element: environment.getSpecifiedElements()) {                
                if (element.getKind() == ElementKind.CLASS) {
                    documentClass(writer, commentProvider, element);
                }
            }

            writer.save(new File(destinationDir.getValue(), "testcases.xml"));
        }
        catch (Exception ex) {
            this.reporter.print(Kind.ERROR, ex.toString());
            result = false;
        }

        return result;
    }

    private void documentClass(TestCaseWriter writer, DocTrees commentProvider, Element classElement) {
        String className = ((QualifiedNameable) classElement).getQualifiedName().toString();

        for (Element method: classElement.getEnclosedElements()) {
            boolean isTest = null != method.getAnnotation(org.junit.Test.class);

            if (isTest) {
                DocCommentTree comments = commentProvider.getDocCommentTree(method);
                String description = (comments != null) ? comments.toString() : "";

                writer.add(method.getSimpleName().toString(), className, description);    
            }
        }
    }

}
