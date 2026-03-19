import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Bt3 {
    static class Diem implements Comparable<Diem> {
        int hoanhDo;
        int tungDo;

        Diem(int hoanhDo, int tungDo) {
            this.hoanhDo = hoanhDo;
            this.tungDo = tungDo;
        }

        @Override
        public int compareTo(Diem diemKhac) {
            if (this.hoanhDo != diemKhac.hoanhDo) {
                return this.hoanhDo - diemKhac.hoanhDo;
            }
            return this.tungDo - diemKhac.tungDo;
        }

        @Override
        public String toString() {
            return hoanhDo + " " + tungDo;
        }
    }

    static long tichCheo(Diem goc, Diem a, Diem b) {
        return 1L * (a.hoanhDo - goc.hoanhDo) * (b.tungDo - goc.tungDo)
                - 1L * (a.tungDo - goc.tungDo) * (b.hoanhDo - goc.hoanhDo);
    }

    static List<Diem> timBaoLoi(List<Diem> listDiem) {
        Collections.sort(listDiem);
        int soLuongDiem = listDiem.size();

        if (soLuongDiem <= 1) {
            return listDiem;
        }

        List<Diem> nuaDuoi = new ArrayList<>();
        for (Diem diem : listDiem) {
            while (nuaDuoi.size() >= 2 &&
                    tichCheo(nuaDuoi.get(nuaDuoi.size() - 2),
                            nuaDuoi.get(nuaDuoi.size() - 1),
                            diem) <= 0) {
                nuaDuoi.remove(nuaDuoi.size() - 1);
            }
            nuaDuoi.add(diem);
        }

        List<Diem> nuaTren = new ArrayList<>();
        for (int i = soLuongDiem - 1; i >= 0; i--) {
            Diem diem = listDiem.get(i);
            while (nuaTren.size() >= 2 &&
                    tichCheo(nuaTren.get(nuaTren.size() - 2),
                            nuaTren.get(nuaTren.size() - 1),
                            diem) <= 0) {
                nuaTren.remove(nuaTren.size() - 1);
            }
            nuaTren.add(diem);
        }

        nuaDuoi.remove(nuaDuoi.size() - 1);
        nuaTren.remove(nuaTren.size() - 1);
        nuaDuoi.addAll(nuaTren);

        return nuaDuoi;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int soTram = scanner.nextInt();
        List<Diem> danhSachTram = new ArrayList<>();

        for (int i = 0; i < soTram; i++) {
            int hoanhDo = scanner.nextInt();
            int tungDo = scanner.nextInt();
            danhSachTram.add(new Diem(hoanhDo, tungDo));
        }

        List<Diem> cacTramNgoaiCung = timBaoLoi(danhSachTram);

        for (Diem diem : cacTramNgoaiCung) {
            System.out.println(diem);
        }
        scanner.close();
    }
}