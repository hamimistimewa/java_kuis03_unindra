import org.sqlite.*;
import java.sql.*;
import java.util.Scanner;

public class UASSore2 {
    public static void main(String args[]) {
        Scanner scan = new Scanner(System.in);
        connectDB();
        System.out.println("\t\tPENCARIAN DATA PEGAWAI");
        System.out.println("================================================================");
        System.out.print("\tPilih kategori pencarian >> [id/nama/gol] : ");
        String pilih = scan.nextLine();
        System.out.println("----------------------------------------------------------------");
        System.out.print("\tMasukan kata kunci pencarian : ");
        String Stat = scan.nextLine();
        System.out.println("================================================================");
        selectingDB(Stat, pilih);
    }

    public static void connectDB() {
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:UAS2021.db");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("");
        System.out.println("Koneksi Database sukses");
        System.out.println("-----------------------");
    }

    public static void selectingDB(String stat, String pilih) {
        if (pilih.equals("id")) {
            pilih = "ID";
        } else if (pilih.equals("nama")) {
            pilih = "Nama";
        } else if (pilih.equals("gol")) {
            pilih = "Golongan";
        }
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:UAS2021.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM pegawai where " + pilih + " like '%" + stat + "%';");
            System.out.println("Hasil Pencarian : \n\n");
            System.out.println("----------------------------------------------------------------");
            System.out.printf("%s %-20s %-10s %-15s %-10s %-10s%n", "ID", "Nama Pegawai", "Golongan", "Gaji Pokok",
                    "Tunjangan", "Total Gaji");
            System.out.println("----------------------------------------------------------------");
            while (rs.next()) {
                String id = rs.getString("ID");
                String nama = rs.getString("Nama");
                int gol = rs.getInt("Golongan");
                int gaji = rs.getInt("Gaji_Pokok");
                int gaji_total = rs.getInt("Total_Gaji");
                int tunjangan = rs.getInt("Tunjangan");
                String format = "%s %-20s %-10s %-15s %-10s %-10s%n";
                System.out.printf(format, id, nama, gol, gaji, tunjangan, gaji_total);
            }
            System.out.println("----------------------------------------------------------------");
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            // TODO: handle exception
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Done");
    }
}