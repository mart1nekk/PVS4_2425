package recap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MapTest {
    static class student{
        String name;
        private String subject;
       private double score;
       private int Timespent;

        public student(String name, String subject, double score, int timespent) {
            this.name = name;
            this.subject = subject;
            this.score = score;
            Timespent = timespent;
        }

        public String getName() {
            return name;
        }

        public String getSubject() {
            return subject;
        }

        public double isScore() {
            return score;
        }

        public int getTimespent() {
            return Timespent;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public void setTimespent(int timespent) {
            Timespent = timespent;
        }

        @Override
        public String toString() {
            return "student{" +
                    "name='" + name + '\'' +
                    ", subject='" + subject + '\'' +
                    ", score=" + score +
                    ", Timespent=" + Timespent +
                    '}';
        }

    }

    public static void main(String[] args) throws IOException {
        List<student> idk = Files.lines(Paths.get("students.csv"))
                .skip(1)
                .map(line -> line.split(";"))
                .map(split ->new student(
                        split[0],
                        split[1],
                        Double.parseDouble(split[2]),
                        Integer.parseInt(split[3])
                )).toList();

        //prvni
        idk.stream()
                .filter(s -> s.getSubject().equals("Biology") && s.getTimespent() <50 && s.isScore() >80)
                .forEach(System.out::println);

        //druhy
        Long pocetPredmetu = idk.stream()
                        .filter(s -> s.getSubject().equals("Chemistry"))
                        .count();
        System.out.println(pocetPredmetu);

       Long sdkgh = idk.stream()
               .filter(s -> s.getSubject().equals("Chemtisty") && s.isScore() <30)
               .count();
       double avg = (pocetPredmetu/sdkgh) * 100.0;
        System.out.println(avg);


        //treti
        List<student> unique = idk.stream()
                .distinct()
                .collect(Collectors.toList());
        System.out.println(idk.size());
        System.out.println(unique.size());

        //ctvrty Vypište do konzole, jaká je průměrná doba psaní testu z každého předmětu - namapujte.


        //5) Namapujte počet všech testů vůči všem předmětům.
        Map<String, Long> cnt = idk.stream()
                .collect(Collectors.groupingBy(student::getSubject,Collectors.counting()));
        System.out.println(cnt);

        HashMap<String, ArrayList<student>> contiMap = new HashMap<>();
        for (student st: idk ){
            if (contiMap.containsKey(st.getSubject())){
                contiMap.get(st.getSubject()).add(st);
            }else {
                ArrayList<student> toadd = new ArrayList<>();
                toadd.add(st);
                contiMap.put(st.getSubject(),toadd);
            }
        }
        for (String x : contiMap.keySet()){
            System.out.println(x);
            for (student c : contiMap.get(x)){
                System.out.println(c.getSubject());
            }
        }
    }
}
