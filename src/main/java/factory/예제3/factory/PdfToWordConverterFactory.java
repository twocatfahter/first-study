package factory.예제3.factory;

import factory.예제3.DocumentConverter;
import factory.예제3.PdfToWordConverter;

public class PdfToWordConverterFactory extends ConverterFactory{

    @Override
    protected DocumentConverter createConverter() {
        System.out.println("pdf 에서 word로 바꾸는 컨버터 생성");
        return new PdfToWordConverter();
    }
}
