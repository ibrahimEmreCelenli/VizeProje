import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

//Dosya işlemleri için kullanılan bir class
class Dosyalar {

    //Bu metot hangi satırda Genel Üyeler yazısının olduğunu bulup bize bi önceki satır değerini gönderiyor.
    public int satirBulucu() throws IOException {

        //dosya okumak için gerekli nesneler oluşturuluyor
        FileReader dosyaOkuyucu=new FileReader("src//Kullanıcılar.txt");
        BufferedReader oku=new BufferedReader(dosyaOkuyucu);

        //Gerekli değişkenler
        String a;
        int i=0;
        int satir=0;

        //Okunan belgenin sonuna kadar GENEL ÜYELER yazan yer aranıyor ve kaçıncı satırda olduğu bulunuyor
        while((a=oku.readLine())!=null){
            if(a.contains("GENEL ÜYELER")){
                satir=i;
                break;
            }
            i++;
        }

        //Belge kapatılıyor
        oku.close();

        //Satır bilgisi dönülüyor
        return satir;
    }

    //Bu metot elit kullanıcı eklememizi sağlıyor
    public void elitYazici(int satir,String bilgi) throws IOException {

        //İki adet dosya oluşturuluyor (eski dosya halihazırda var olduğu için sadece açıyor)
        File eski=new File("src//Kullanıcılar.txt");
        File gecici= new File("gecici.txt");

        //Dosyaya yazma işlemleri için gerekli nesneler oluşturuluyor
        FileWriter fw= new FileWriter(gecici,true);
        BufferedWriter bw= new BufferedWriter(fw);
        PrintWriter yaz = new PrintWriter(bw);

        //Dosyayı okuma işlemleri için gerekli nesneler oluşturuluyor
        FileReader fr=new FileReader("src//Kullanıcılar.txt");
        BufferedReader oku= new BufferedReader(fr);

        //Gerekli değişkenler
        String a;
        int sayac=0;

        //Eski dosya, bilgileri girilen elit kullanıcı dahil edilerek yeni dosyaya aynen yazılıyor
        while((a=oku.readLine())!=null){
            sayac++;
            if(satir!=sayac){
                yaz.println(a);
            }
            else{
                yaz.println(bilgi+"\n");
            }
        }

        //Dosyalar kapatılıyor
        yaz.flush();
        yaz.close();
        fr.close();
        oku.close();
        bw.close();
        fw.close();

        //Eski dosya siliniyor
        eski.delete();

        //Gecici dosya eski dosyanın yerine geçiyor
        File gecici2= new File("src//Kullanıcılar.txt");
        gecici.renameTo(gecici2);
    }

    //Bu metot genel kullanıcı eklememizi sağlıyor
    public void genelYazici(String bilgi) throws IOException {

        //dosya yazma işlemleri için gerekli nesneler oluşturuluyor
        FileWriter fw= new FileWriter("src//Kullanıcılar.txt",true);
        BufferedWriter yaz= new BufferedWriter(fw);

        //Dosyanın en sonuna genel kullanıcı bilgisi ekleniyor
        yaz.write(bilgi+"\n");

        //Dosya kapatılıyor
        yaz.close();
    }

    //Bu metot dosyaya herhangi bir şey yadırmamızı sağlıyor
    public void yazici(String a) throws IOException {

        //Dosya yazma işlemleri için gerekli değişkenler
        FileWriter fw= new FileWriter("src//Kullanıcılar.txt");
        BufferedWriter yaz=new BufferedWriter(fw);

        //Dosyaya istenilen şey yazılıyor
        yaz.write(a);

        //Dosya kapatılıyor
        yaz.close();
    }

    //Bu metot dosyamızdaki bütün mailleri bulmaya yarıyor
    public ArrayList<String> tumMailBul() throws IOException {

        //Dosya okumak için gerekli nesneler oluşturuluyor
        FileReader fw=new FileReader("src//Kullanıcılar.txt");
        BufferedReader oku=new BufferedReader(fw);

        //E-maillerdeki belli patternlere göre e-mailler saptanıyor ve ArrayList'imize kaydediliyor
        Pattern kalip = Pattern.compile("[a-zA-Z0-9]" + "[a-zA-Z0-9_.]" + "*@[a-zA-Z0-9]" + "+([.][a-zA-Z]+)+");
        ArrayList<String> a=new ArrayList<>();
        int i=0;
        String satir = oku.readLine();
        while (satir != null) {
            Matcher m = kalip.matcher(satir);
            while (m.find()) {
                a.add(m.group());
            }
            satir = oku.readLine();
        }

        //Maillerin kayıtlı olduğu ArrayList döndürülüyor
        return a;
    }

    //Bu metot dosyamızdaki elit üyelerin maillerini bulmaya yarıyor
    public ArrayList<String> elitMailBul() throws IOException {

            //Dosya okumak için gerekli nesneler oluşturuluyor
            FileReader fw=new FileReader("src//Kullanıcılar.txt");
            BufferedReader oku=new BufferedReader(fw);

            //Gerekli değişkenler
            int i=0,s=0;
            ArrayList<String> a=new ArrayList<>();

            //E-maillerdeki belli patternlere göre e-mailler saptanıyor ve ArrayList'imize kaydediliyor
            Pattern kalip = Pattern.compile("[a-zA-Z0-9]" + "[a-zA-Z0-9_.]" + "*@[a-zA-Z0-9]" + "+([.][a-zA-Z]+)+");
            String satir = oku.readLine();

            /*Bu döngü "GENEL ÜYELER" satırına gelene kadar yani bir diğer değiş ile
            sadece elit üyeler alınacak şekilde maillerin kaydedilmesini sağlıyor*/
            while (i<=satirBulucu()) {
                Matcher m = kalip.matcher(satir);
                while (m.find()) {
                    a.add(m.group());
                }
                satir = oku.readLine();
                i++;
            }

            //Maillerin kayıtlı olduğu ArrayList döndürülüyor
            return a;
        }

    //Bu metot dosyamızdaki genel üyelerin maillerini bulmaya yarıyor
    public ArrayList<String> genelMailBul() throws IOException{

        //Dosya okumak için gerekli nesneler oluşturuluyor
        FileReader fw=new FileReader("src//Kullanıcılar.txt");
        BufferedReader oku=new BufferedReader(fw);


        //Gerekli değişkenler
        ArrayList<String> a=new ArrayList<>();
        int i=0;
        String cop;

        //E-maillerdeki belli patternlere göre e-mailler saptanıyor ve ArrayList'imize kaydediliyor
        Pattern kalip = Pattern.compile("[a-zA-Z0-9]" + "[a-zA-Z0-9_.]" + "*@[a-zA-Z0-9]" + "+([.][a-zA-Z]+)+");

        /*Bu döngü "GENEL ÜYELER" satırından sonra gelen üyelerin
        mailleri alınacak şekilde maillerin kaydedilmesini sağlıyor*/
        while (i<=satirBulucu()) {

            //Bellekteki gereksiz "\n" karakterini temizlemek için çöp isimli bir değişkene atıyorum
            cop = oku.readLine();
            i++;
        }
        String satir = oku.readLine();
        while (satir != null) {
            Matcher m = kalip.matcher(satir);
            while (m.find()) {
                a.add(m.group());
            }
            satir = oku.readLine();
        }

        //Maillerin kayıtlı olduğu ArrayList döndürülüyor
        return a;
    }
    public Dosyalar() throws FileNotFoundException {
    }
}

//Bu class bizim posta ile ilgili işlemlerimizi hazırlıyor
class Posta{
    void posta(ArrayList<String> liste, String mesaj){

        //Gonderen e-posta adresi ve sifresi buraya yazılmalıdır
        String gonderici = "";
        String sifre="";

        //Posta göndermek için server bilgisi gibi gerekli özellikler ayarlanıyor
        Properties ozellik = System.getProperties();
        ozellik.put("mail.smtp.host", "smtp.gmail.com");
        ozellik.put("mail.smtp.port", "587");
        ozellik.put("mail.smtp.starttls.enable", "true");
        ozellik.put("mail.smtp.auth", "true");
        ozellik.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        System.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");

        //Mail doğrulamak için yeni oturum açılıyor ve onaylama işlemleri yapılıyor
        Session oturum = Session.getInstance(ozellik, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(gonderici, sifre);
            }
        });

        try {

            //Mail işlemleri için gerekli nesne oluşturuluyor
            MimeMessage msg = new MimeMessage(oturum);

            //Mail'i gonderen belirtiliyor
            msg.setFrom(new InternetAddress(gonderici));

            //Mail alıcıları ekleniyor
            for(int i=0;i<liste.size();i++){
                msg.addRecipient(Message.RecipientType.TO, new InternetAddress(liste.get(i)));
            }

            //Mesaj içeriği ayarlanıyor
            msg.setText(mesaj);

            //Gonderiliyor mesajı ile kullanıcı bilgilendiriliyor
            System.out.println("Gönderiliyor...");

            //Mesaj gönderiliyor
            Transport.send(msg);

            //Mesajın basşarılı bir şekilde gönderildiği belirtiliyor
            System.out.println("Mesaj başarılı bir şekilde gönderildi....");

            //Hata ile karşılaşılması halinde ekrana hata yazılır
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}

//Bu class Posta class'ının bir alt classıdır ve posta metodunu kalıtım ile almıştır
class PostaGonder extends Posta{

    //Bu metod çağrıldığında posta gönderiliyor
    void postaGonderici(ArrayList<String> liste, String mesaj){

        //Burada kalıtım ile aldığı posta metodunu kullanıyor
        super.posta(liste,mesaj);
    }
}

//Main class
public class Main {
    public static void main(String[] args) throws IOException {

        //Gerekli nesneler
        Scanner scan = new Scanner(System.in);
        Dosyalar dosya = new Dosyalar();
        PostaGonder p=new PostaGonder();

        //Burada eğer src klasöründe kullanıcılar.txt dosyası yoksa bu dosya oluşturuluyor
        File dosyaOlustur = new File("src//Kullanıcılar.txt");
        if (dosyaOlustur.createNewFile()) {
            dosya.yazici("#ELİT ÜYELER# \n\n#GENEL ÜYELER# ");
        }
        //Gerekli bir değişken
        boolean karar = false;

        //Bu do-while döngüsü ile kullanıcı yeni bir işlem yapmak istemeyene kadar başa dönüyoruz
        do {

            //Menü ekranı ve basılan tuşun algılanması
            System.out.println("#Üye ekleme ekranına hoşgeldiniz#\n1- Elit Üye Ekleme\n2- Genel Üye Ekleme\n3- Mail Gönderme\n");
            int basilanTus = scan.nextInt();

            //Basılan tuşa göre çalışan bir switch-case yapısı ile işlemler yapılıyor
            switch (basilanTus) {

                //Eğer kullanıcı '1' tuşuna basarsa bu case çalışacak
                case 1:

                    //Bellekteki gereksiz "\n" karakterini temizlemek için çöp isimli bir değişkene atıyorum
                    String cop = scan.nextLine();

                    //Kişinin bilgileri alınıyor
                    System.out.print("Kişinin ismi: ");
                    String isim = scan.nextLine();
                    System.out.print("Kişinin soyismi: ");
                    String soyisim = scan.nextLine();
                    System.out.print("Kişinin e-mail adresi: ");
                    String email = scan.nextLine();
                    String bilgi = isim + "\t" + soyisim + "\t" + email;

                    //Bilgiler Kullanıcılar.txt dosyasına kaydediliyor
                    dosya.elitYazici(dosya.satirBulucu(), bilgi);
                    break;

                //Eğer kullanıcı '2' tuşuna basarsa bu case çalışacak
                case 2:

                    //Bellekteki gereksiz "\n" karakterini temizlemek için çöp isimli bir değişkene atıyorum
                    cop = scan.nextLine();

                    //Kişinin bilgilerini alıyor
                    System.out.print("Kişinin ismi: ");
                    isim = scan.nextLine();
                    System.out.print("Kişinin soyismi: ");
                    soyisim = scan.nextLine();
                    System.out.print("Kişinin e-mail adresi: ");
                    email = scan.nextLine();
                    bilgi = isim + "\t" + soyisim + "\t" + email;

                    //Bilgileri Kullanıcılar.txt dosyasına kaydediliyor
                    dosya.genelYazici(bilgi);
                    break;

                //Eğer kullanıcı '3' tuşuna basarsa bu case çalışacak
                case 3:

                    //Kullanıcıya kimlere mail atmak istediği soruluyor
                    System.out.println("1- Elit Üyelere Mail\n2- Genel Üyelere Mail\n3- Tüm Üyelere Mail");
                    basilanTus=scan.nextInt();

                    //Basılan tuşa göre çalışan bir switch-case yapısı ile işlemler yapılıyor
                    switch (basilanTus){

                        //Eğer kullanıcı '1' tuşuna basarsa bu case çalışacak
                        case 1:

                            //Bellekteki gereksiz "\n" karakterini temizlemek için çöp isimli bir değişkene atıyorum
                            cop=scan.nextLine();

                            //Mesaj içeriği alınıyor
                            System.out.print("Mesaj içeriği:  ");
                            String mesaj=scan.nextLine();

                            //elitMailBul metodu ile elit üyelerin mailleri liste isimli ArrayList'e kaydediliyor
                            ArrayList<String> liste=dosya.elitMailBul();

                            //postaGonderici metodu ile mail gönderiliyor
                            p.postaGonderici(liste,mesaj);
                            break;

                        //Eğer kullanıcı '2' tuşuna basarsa bu case çalışacak
                        case 2:

                            //Bellekteki gereksiz "\n" karakterini temizlemek için çöp isimli bir değişkene atıyorum
                            cop=scan.nextLine();

                            //Mesaj içeriği alınıyor
                            System.out.print("Mesaj içeriği: ");
                            mesaj=scan.nextLine();

                            //genelMailBul metodu ile genel üyelerin mailleri liste isimli ArrayList'e kaydediliyor
                            liste=dosya.genelMailBul();

                            //postaGonderici metodu ile mail gönderiliyor
                            p.postaGonderici(liste,mesaj);
                            break;

                        //Eğer kullanıcı '3' tuşuna basarsa bu case çalışacak
                        case 3:

                            //Bellekteki gereksiz "\n" karakterini temizlemek için çöp isimli bir değişkene atıyorum
                            cop=scan.nextLine();

                            //Mesaj içeriği alınıyor
                            System.out.print("Mesaj içeriği: ");
                            mesaj=scan.nextLine();

                            //tumMailBul metodu ile tüm üyelerin mailleri liste isimli ArrayList'e kaydediliyor
                            liste=dosya.tumMailBul();

                            //postaGonderici metodu ile mail gönderiliyor
                            p.postaGonderici(liste,mesaj);
                            break;

                        //Eğer kullanıcı yanlış tuşlama yaparsa default case çalışıyor ve bir uyarı veriyor
                        default:
                            System.out.println("Hatalı tuşlama yaptınız! Lütfen tekrar deneyin.");
                    }

                    break;

                //Eğer kullanıcı yanlış tuşlama yaparsa default case çalışıyor ve bir uyarı veriyor
                default:
                    System.out.println("Hatalı tuşlama yaptınız! Lütfen tekrar deneyin.");

            }

            //Kullanıcıdan başka bir işlem yapmak isteyip istemediği soruluyor
            System.out.println("Başka bir işlem yapmak ister misiniz? (Evet/Hayır)");
            do {

                //Kullanıcının kararı alınıyor
                String kararTus = scan.nextLine();

                //eğer kullanıcı evet derse karar değeri true olarak değiştiriliyor
                if (kararTus.equals("Evet") || kararTus.equals("EVET") || kararTus.equals("evet") || kararTus.equals("E") || kararTus.equals("e")) {
                    karar = true;
                    break;

                //eğer kullanıcı hayır derse karar değeri false olarak değiştiriliyor
                } else if (kararTus.equals("Hayır") || kararTus.equals("hayır") || kararTus.equals("hayir") || kararTus.equals("HAYIR") || kararTus.equals("Hayir") || kararTus.equals("HAYİR") || kararTus.equals("h") || kararTus.equals("H")) {
                    karar = false;
                    break;

                //eğer kullanıcı yanlış tuşlama yaptıysa uyarı veriliyor
                } else {
                    System.out.println("Hatalı tuşlama yaptınız. LÜtfen Evet ya da Hayır yazınız.");
                }
            } while (true);
        } while (karar);
    }
}