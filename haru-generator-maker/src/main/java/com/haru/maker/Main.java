package com.haru.maker;


import com.haru.maker.generator.MainGenerator;
import freemarker.template.TemplateException;

import java.io.IOException;

public class Main {

public static void main(String[] args) throws TemplateException, IOException, InterruptedException {
    MainGenerator mainGenerator = new MainGenerator();
    mainGenerator.doGenerate();
}

}