package recap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//1 - Parse souboru
//students.csv
//Jméno, předmět, skóre (double), čas psaní v minutách (celé číslo)
//
//Map/Stream úkoly
//1) Vypište do konzole všechny studenty, kteří biologii psali pod 50 minut a získali skóre alespoň 80
//2) Vypište číslo, kolik % studentů získalo nesplnil zisk ani 30 bodů z chemie.
//3) Vypište, kolik jmen studentů se opakovalo.
//4) Vypište do konzole, jaká je průměrná doba psaní testu z každého předmětu - namapujte.
//5) Namapujte počet všech testů vůči všem předmětům.

class Student {
    private String name;
    private String subject;
    private Double score;
    private int time;

    public Student(String name, String subject, Double score, int time) {
        this.name = name;
        this.subject = subject;
        this.score = score;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public String getSubject() {
        return subject;
    }

    public Double getScore() {
        return score;
    }

    public int getTime() {
        return time;
    }
}

class mapping {
    public static void main(String[] args) throws IOException {
        List<Student> students = Files.lines(Paths.get("data/students.csv"))
                .skip(1)
                .map(line -> line.split(","))
                .map(split -> new Student(
                        split[0],
                        split[1],
                        Double.parseDouble(split[2]),
                        Integer.parseInt(split[3])
                ))
                .toList();

//        students.forEach(p -> System.out.println(p.getName() + " " + p.getSubject()));
        //1) Vypište do konzole všechny studenty, kteří biologii psali pod 50 minut a získali skóre alespoň 80
        students.stream()
                .filter(s -> s.getSubject().equals("Biology") && s.getTime() < 50 && s.getScore() >= 80)
                .forEach(s -> System.out.println(s.getName() + " subject: " + s.getSubject() + ", Time: " + s.getTime() + ", score: " + s.getScore()));

//        2) Vypište číslo, kolik % studentů nesplnilo zisk ani 30 bodů z chemie.
        long countChemistry = students.stream()
                .filter(s -> s.getSubject().equals("Chemistry") && s.getScore() < 30)
                .count();
        double percentage =((double) countChemistry / students.size()) * 100;
        System.out.println("Percentage: " + percentage);

        //3) Vypište, kolik jmen studentů se opakovalo.
        long countNonDuplicates = students.stream()
                .map(Student::getName)
                .distinct()
                .count();
//        System.out.println(countNonDuplicates);
        System.out.println("Duplicates: " + (students.size() - countNonDuplicates));

        //4) Vypište do konzole, jaká je průměrná doba psaní testu z každého předmětu - namapujte.
        Map<String, Double> avgTime = students.stream()
                .collect(Collectors.groupingBy(Student::getSubject, Collectors.averagingDouble(Student::getTime)));
        System.out.println("Average time: " + avgTime);

        //5) Namapujte počet všech testů vůči všem předmětům.
        Map<String, Long> countTests = students.stream()
                .collect(Collectors.groupingBy(Student::getSubject, Collectors.counting()));
        System.out.println("Count: " + countTests);


        //průměrné skóre z každého předmětu
        Map<String, Double> avgScore = students.stream()
                .collect(Collectors.groupingBy(Student::getSubject, Collectors.averagingDouble(Student::getScore)));
        System.out.println("prumer" + avgScore);

        //průměrné sckóre každého studenta (podle jména)
        Map <String, Double> avgScoreByName = students.stream()
                .collect(Collectors.groupingBy(Student::getName,Collectors.averagingDouble(Student::getScore)));
        System.out.println("Průměrné skóre studenta je " + avgScoreByName);

        //spočítat, kolik testů psal každý student podle jména
        Map<String, Long> counting = students.stream()
                .collect(Collectors.groupingBy(Student::getName,Collectors.counting()));
        System.out.println("Počet testů na studenta" + counting);

        //maximalni score tzz kazdeho predmetu
        Map<String, Double> maxc = students.stream()
                .collect(Collectors.groupingBy(Student::getSubject,Collectors.collectingAndThen(
                        Collectors.maxBy((s1,s2) -> s1.getScore().compareTo(s2.getScore())),
                        max -> max.map(Student::getScore).orElse(0.0)
                )));

        System.out.println("maximalni skóre z každého předmětu " + maxc);


    }
}
