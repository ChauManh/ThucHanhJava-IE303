import java.util.Random;

public class Bt2 {
    public static void main(String[] args) {
        int tongSoDiem = 1_000_000;
        int soDiemTrongHinhTron = 0;
        Random random = new Random();

        for (int i = 0; i < tongSoDiem; i++) {
            double x = -1 + 2 * random.nextDouble();
            double y = -1 + 2 * random.nextDouble();

            if (x * x + y * y <= 1) {
                soDiemTrongHinhTron++;
            }
        }

        double pi = 4.0 * soDiemTrongHinhTron / tongSoDiem;
        System.out.println("Giá trị pi xấp xỉ là: " + pi);
    }
}
