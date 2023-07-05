import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {

    public static final BlockingQueue<String> countA = new ArrayBlockingQueue<>(100);
    public static final BlockingQueue<String> countB = new ArrayBlockingQueue<>(100);
    public static final BlockingQueue<String> countC = new ArrayBlockingQueue<>(100);

    public static void main(String[] args) {
        new Thread(() -> {
            for (int i = 0; i < 10_000; i++) {
                String text = generateText("abc", 100_000);
                try {
                    countA.put(text);
                    countB.put(text);
                    countC.put(text);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        }).start();

        new Thread(() -> {
            int count = 0;
            int numberLine = 0;

            char charA = 'a';

            for (int i = 0; i < 10_000; i++) {
                int countInLine = 0;
                String text;
                try {
                    text = countA.take();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                for (int k = 0; k < text.length(); k++) {
                    if (text.charAt(k) == charA) {
                        countInLine++;
                    }
                }
                if (countInLine > count) {
                    count = countInLine;
                    numberLine = i;
                }
            }
            System.out.format("Строка %d, количество A - %d\n", numberLine, count);
        }).start();

        new Thread(() -> {
            int count = 0;
            int numberLine = 0;

            char charA = 'b';

            for (int i = 0; i < 10_000; i++) {
                int countInLine = 0;
                String text;
                try {
                    text = countB.take();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                for (int k = 0; k < text.length(); k++) {
                    if (text.charAt(k) == charA) {
                        countInLine++;
                    }
                }
                if (countInLine > count) {
                    count = countInLine;
                    numberLine = i;
                }
            }
            System.out.format("Строка %d, количество B - %d\n", numberLine, count);
        }).start();

        new Thread(() -> {
            int count = 0;
            int numberLine = 0;

            char charA = 'c';

            for (int i = 0; i < 10_000; i++) {
                int countInLine = 0;
                String text;
                try {
                    text = countC.take();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                for (int k = 0; k < text.length(); k++) {
                    if (text.charAt(k) == charA) {
                        countInLine++;
                    }
                }
                if (countInLine > count) {
                    count = countInLine;
                    numberLine = i;
                }
            }
            System.out.format("Строка %d, количество C - %d\n", numberLine, count);
        }).start();
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}