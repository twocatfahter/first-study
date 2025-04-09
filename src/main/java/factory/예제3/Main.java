package factory.예제3;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        DocumentService documentService = new DocumentService();
        byte[] test = {1, 2, 3, 4};
        byte[] bytes = documentService.convertDocument(test, DocumentType.WORD, DocumentType.PDF);

        System.out.println("bytes: "  + Arrays.toString(bytes));
    }
}
