import java.io.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


//  TODO:
//
//  - Make directories      *DONE*
//  - Listing               *DONE*
//  - Sorting               *DONE*
//  - Moving                *DONE*
//  - Add more file types   **


public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {



        //  Tervetulo viesti
        System.out.println("---------------------------------------------------------");
        System.out.println("                    Java File sorter                     ");
        System.out.println("---------------------------------------------------------");
        System.out.println("Hello and welcome to my sorter!");
        System.out.println("This programm runs only in current directory or in subdirectory.");
        System.out.println("It also works only for certain file types.");
        System.out.println("---------------------------------------------------------");

        //  Kertoo nykyisen hakemistosijainnin
        String path = System.getProperty("user.dir");
        System.out.println("Current directory: " + path);

        boolean runs = true;
        Scanner scanner = new Scanner(System.in);
        int vastaus = 0;

        while(runs){
            System.out.println("---------------------------------------------------------");
            System.out.println("What would you like to do?");
            System.out.println("(1)\tList files and folders");
            System.out.println("(2)\tCreate folders.");
            System.out.println("(3)\tMove all files to new folders.");
            System.out.println("(4)\tExit program.");
            System.out.println("---------------------------------------------------------");
            vastaus = scanner.nextInt();

            try {

                switch (vastaus){

                    case 1:
                        System.out.println("Listing your files.");
                        try{
                            //  Pistää tietdosto File tyyppiseen listaan
                            List<File> files = Files.list(Paths.get(path))
                                    .map(Path::toFile)
                                    .filter(File::isFile)
                                    .collect(Collectors.toList());
                            files.forEach(System.out::println);

                            //  Pistää kansiot Listaan
                            List<File> folders = Files.list(Paths.get(path))
                                    .map(Path::toFile)
                                    .filter(File::isDirectory)
                                    .collect(Collectors.toList());
                            folders.forEach(System.out::println);

                        } catch (IOException e){
                            e.printStackTrace();
                        }
                        break;

                    case 2:
                        String[] folders = new String[4];
                        folders[0] = "/images/";
                        folders[1] = "/videos/";
                        folders[2] = "/gifs/";
                        folders[3] = "/documents/";

                        for (String subdirectory : folders) {
                            File theDir = new File(path + subdirectory);
                            if (!theDir.exists()){
                                theDir.mkdirs();
                                System.out.println("Creating " + subdirectory + " folder.");
                            }
                            else{
                                System.out.println("Directories already exist!");
                            }
                        }



                        break;

                    case 3:
                        System.out.println("Organizing your files.");

                        //  Hakee tiedostot listaan
                        List<File> files = Files.list(Paths.get(path))
                                .map(Path::toFile)
                                .filter(File::isFile)
                                .collect(Collectors.toList());

                        //  Käy tiedostot läpi                  +
                        //  Tarkistaa tiedostopäätteen          +
                        //  Vie päätteen mukaiseen kansioon     +


                        for (File file: files) {

                            System.out.println(file);

                            String nimi = file.toString();
                            String extension = "";

                            int i = nimi.lastIndexOf('.');
                            int p = Math.max(nimi.lastIndexOf('/'), nimi.lastIndexOf('\\'));

                            if (i > p) {
                                extension = nimi.substring(i+1);
                                System.out.println(extension);

                                if (extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png") || extension.equals("webp")){
                                    String tiedoston_nimi = file.getName();
                                    String destonation = path + "\\images\\" + tiedoston_nimi;

                                    Files.move(Path.of(nimi), Path.of(destonation));
                                } else if (extension.equals("gif")) {
                                    String tiedoston_nimi = file.getName();
                                    String destination = path + "\\gifs\\" + tiedoston_nimi;

                                    Files.move(Path.of(nimi), Path.of(destination));
                                } else if (extension.equals("mp4") || extension.equals("mov") || extension.equals("mkv")) {
                                    String tiedoston_nimi = file.getName();
                                    String destination = path + "\\videos\\" + tiedoston_nimi;

                                    Files.move(Path.of(nimi), Path.of(destination));
                                }else if (extension.equals("docx") || extension.equals("txt") || extension.equals("rtf") || extension.equals("pdf") ||extension.equals("pptx") || extension.equals("xlsx") || extension.equals("xls") || extension.equals("csv")) {
                                    String tiedoston_nimi = file.getName();
                                    String destination = path + "\\documents\\" + tiedoston_nimi;

                                    Files.move(Path.of(nimi), Path.of(destination));
                                }
                            }
                        }

                        break;
                    case 4:
                        System.out.println("Stopping system.");
                        runs = false;
                        break;
                }


            }
            catch (Exception e){
                System.out.println("Something went wrong!");
            }



        }
        Thread.sleep(800);
        System.out.println("\nThank you and have a nice day!\n\n\n\n");
        Thread.sleep(1000);

    }
}