import java.util.Random;
import java.util.Scanner;

public class Bt1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập bán kính r: ");
        double r = scanner.nextDouble();

        int tongSoDiem = 1_000_000;
        int soDiemTrongHinhTron = 0;
        Random random = new Random();

        for (int i = 0; i < tongSoDiem; i++) {
            double x = -r + 2 * r * random.nextDouble();
            double y = -r + 2 * r * random.nextDouble();

            if (x * x + y * y <= r * r) {
                soDiemTrongHinhTron++;
            }
        }

        double S = (double) soDiemTrongHinhTron / tongSoDiem * 4 * r * r;
        System.out.println("Diện tích xấp xỉ là: " + S);
        scanner.close();
    }
}