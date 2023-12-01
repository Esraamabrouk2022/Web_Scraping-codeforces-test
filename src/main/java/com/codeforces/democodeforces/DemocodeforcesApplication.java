package com.codeforces.democodeforces;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

import java.util.Scanner;


@SpringBootApplication
public class DemocodeforcesApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DemocodeforcesApplication.class, args);
    }

    @Override
    public void run(String... args) {
        // Get problem ID from the user
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Codeforces Problem ID: ");
        String problemId = scanner.nextLine();

        // Specify Codeforces URL
        String codeforcesUrl = "https://codeforces.com/contest/" + problemId + "/problem/A";
        // https://codeforces.com/contest/1903/problem/A
       // https://codeforces.com/contest/1903/problem/A

        // Fetch HTML content using Spring RestTemplate
        RestTemplate restTemplate = new RestTemplate();
        String htmlContent = restTemplate.getForObject(codeforcesUrl, String.class);

        // Parse HTML using Jsoup
        Document document = Jsoup.parse(htmlContent);

        // Extract relevant information
        Element titleElement = document.selectFirst("div.title");
        String problemTitle = titleElement.text();

        Elements paragraphs = document.select("div.ttypography > div.problem-statement > div > p");
        StringBuilder problemText = new StringBuilder();
        for (Element paragraph : paragraphs) {
            problemText.append(paragraph.text()).append("\n");
        }

        Elements inputElements = document.select("div.input-specification > p");
        String inputSpecification = inputElements.text();

        Elements outputElements = document.select("div.output-specification > p");
        String outputSpecification = outputElements.text();

        // Print the extracted information
        System.out.println("\nProblem Title: " + problemTitle);
        System.out.println("\nProblem Text:\n" + problemText.toString());
        System.out.println("\nInput Specification: " + inputSpecification);
        System.out.println("\nOutput Specification: " + outputSpecification);
    }
}
