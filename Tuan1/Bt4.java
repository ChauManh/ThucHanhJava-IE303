import java.util.Scanner;

public class Bt4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nhập số phần tử của mảng: ");
        int soPhanTu = scanner.nextInt();

        System.out.print("Nhập tổng cần tìm: ");
        int tongCanTim = scanner.nextInt();

        int[] mangSo = new int[soPhanTu];
        System.out.println("Nhập các phần tử của mảng:");
        for (int i = 0; i < soPhanTu; i++) {
            System.out.print("Phần tử thứ " + (i + 1) + ": ");
            mangSo[i] = scanner.nextInt();
        }

        int trai = 0;
        int tongHienTai = 0;
        int viTriDauTotNhat = -1;
        int viTriCuoiTotNhat = -1;
        int doDaiLonNhat = 0;

        for (int phai = 0; phai < soPhanTu; phai++) {
            tongHienTai += mangSo[phai];

            while (tongHienTai > tongCanTim && trai <= phai) {
                tongHienTai -= mangSo[trai];
                trai++;
            }

            if (tongHienTai == tongCanTim) {
                int doDaiHienTai = phai - trai + 1;
                if (doDaiHienTai > doDaiLonNhat) {
                    doDaiLonNhat = doDaiHienTai;
                    viTriDauTotNhat = trai;
                    viTriCuoiTotNhat = phai;
                }
            }
        }

        if (viTriDauTotNhat == -1) {
            System.out.println("Không có dãy con nào có tổng bằng " + tongCanTim);
        } else {
            System.out.print("Dãy con dài nhất có tổng bằng " + tongCanTim + " là: ");
            for (int i = viTriDauTotNhat; i <= viTriCuoiTotNhat; i++) {
                System.out.print(mangSo[i]);
                if (i < viTriCuoiTotNhat) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }

        scanner.close();
    }
}