package csnowstack.lib;

import com.google.auto.service.AutoService;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.JavaFileObject;

@AutoService(Processor.class)
public class StartActivityProcessor extends AbstractProcessor {
    private Types mTypeUtils;
    private Elements mElementUtils;
    private Filer mFiler;
    private Messager mMessager;

    @Override public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

        //初始化我们需要的基础工具
        mTypeUtils = processingEnv.getTypeUtils();
        mElementUtils = processingEnv.getElementUtils();
        mFiler = processingEnv.getFiler();
        mMessager = processingEnv.getMessager();
    }

    @Override public SourceVersion getSupportedSourceVersion() {
        //支持的java版本
        return SourceVersion.RELEASE_7;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        //支持的注解
        Set<String> annotations = new LinkedHashSet<>();
        annotations.add(StartActivityAnnotation.class.getCanonicalName());
        return annotations;
    }

    @Override public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {


        if (set == null || set.isEmpty()) {
            return true;
        }


        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(StartActivityAnnotation.class);

        if (elements == null || elements.isEmpty()) {
            return true;
        }

        // 遍历所有被注解了@Factory的元素
        for (Element annotatedElement : elements) {

            // 检查被注解为@Factory的元素是否是一个类
            if (annotatedElement.getKind() != ElementKind.CLASS) {
                return true; // 退出处理
            }

            analysisAnnotated(annotatedElement);
        }

        return true;
    }


    private void analysisAnnotated(Element classElement) {
        StartActivityAnnotation annotation = classElement.getAnnotation(StartActivityAnnotation.class);

        String[] keyValues = annotation.keys().equals("") ? null : annotation.keys().split(",");

        int flag = annotation.flag();
        // 包裹注解元素的元素, 也就是其父元素, 比如注解了成员变量或者成员函数, 其上层就是该类

        // 获取父元素的全类名, 用来生成包名
        String enclosingQualifiedName;
        if (classElement instanceof PackageElement) {
            enclosingQualifiedName = ((PackageElement) classElement).getQualifiedName().toString();
        } else {
            enclosingQualifiedName = ((TypeElement) classElement).getQualifiedName().toString();
        }


        // 生成的包名
        String generatePackageName = enclosingQualifiedName.substring(0, enclosingQualifiedName.lastIndexOf('.'));
        // 生成的类名
        String generateClassName = classElement.getSimpleName() + "_Start";

        StringBuilder stringBuilder = new StringBuilder();




        StringBuilder content = new StringBuilder();
        if (flag != 789123) {
            content.append("\n\tintent.setFlags(" + flag + ");");
        }

        if (keyValues != null && keyValues.length > 0) {
            for (int i = 0, n = keyValues.length; i < n; i += 2) {
                stringBuilder.append(keyValues[i] + " " + keyValues[i + 1] + ",");
                content.append("\n\tintent.putExtra(\"" + keyValues[i+1]+"\"," +keyValues[i+1]+ ");");
            }
        }


        try {
            // 创建Java文件
            JavaFileObject f = processingEnv.getFiler().createSourceFile(generateClassName);
            // 在控制台输出文件路径
            Writer w = f.openWriter();
            PrintWriter pw = new PrintWriter(w);
            pw.println("package " + generatePackageName + ";");
            pw.println("\nimport " + enclosingQualifiedName + ";");
            pw.println("\nimport android.content.Context;");
            pw.println("\nimport android.content.Intent;");
            pw.println("\npublic class " + generateClassName + " { ");
            //拼接层参数列表
            pw.println("    public static void start(Context context" +
                    (!stringBuilder.toString().equals("") ? (","+stringBuilder.substring(0, stringBuilder.length() - 1)) : "") + "){");
            pw.println("    Intent intent=new Intent(context," + classElement.getSimpleName() + ".class);");
            pw.println(content.toString());
            pw.println("    context.startActivity(intent);");
            pw.println("    }");
            pw.println("}");
            pw.flush();
            pw.close();

        } catch (Exception e) {
        }


    }
}
