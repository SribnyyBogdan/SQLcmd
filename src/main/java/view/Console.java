package view;


import java.util.Scanner;

public class Console implements InputOutput {
    @Override
    public void write(String message) {
        System.out.println(message);

    }

    @Override
    public String read() {
        String result = "";
        Scanner scanner = new Scanner(System.in);
        result = scanner.nextLine();
        return result;
    }
}
