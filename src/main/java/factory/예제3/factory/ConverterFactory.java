package factory.예제3.factory;

import factory.예제3.DocumentConverter;

public abstract class ConverterFactory {
    public final byte[] convertDocument(byte[] input) {
        DocumentConverter converter = createConverter();
        String outputFormat = converter.getOutputFormat();
        System.out.println(outputFormat);
        return converter.convert(input);
    }

    protected abstract DocumentConverter createConverter();
}
