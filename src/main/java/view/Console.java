package view;


import java.util.Scanner;

public class Console implements InputOutput {
    private Scanner scanner;
    public Console(){
        scanner = new Scanner(System.in);
    }
    @Override
    public void write(String message) {
        System.out.println(message);

    }

    @Override
    public String read() {
        String result = "";
        result = scanner.nextLine();
        return result;
    }
}
