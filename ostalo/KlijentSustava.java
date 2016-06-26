
import java.io.*;
import java.net.*;

/**
 * Korisnik
 *
 * @author Alen Benkovic
 */
public class KlijentSustava {

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.err.println("Unesite naredbu!");
            System.exit(1);
        }

        StringBuilder naredba = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            naredba.append(args[i]);
            naredba.append(" ");
        }
        

        System.out.println("Saljem naredbu: " + naredba);
        Socket server = null;
        InputStream is = null;
        OutputStream os = null;
        String command = naredba.toString();
        StringBuilder odgovor;
        int znak;
        try {
            server = new Socket("localhost", 8000);
            is = server.getInputStream();
            os = server.getOutputStream();
            os.write(command.getBytes());
            os.flush();
            server.shutdownOutput();

            odgovor = new StringBuilder();
            while ((znak = is.read()) != -1) {
                odgovor.append((char) znak);
            }
            System.out.println(odgovor);

        } catch (IOException e) {
            System.err.println("Greska. " + e.getMessage());
            System.exit(1);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (os != null) {
                    os.close();
                }
                if (server != null) {
                    server.close();
                }
            } catch (IOException ex) {
                System.err.println("Greska. " + ex.getMessage());
            }
        }

    }
}
