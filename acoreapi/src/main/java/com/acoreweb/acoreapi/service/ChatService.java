package com.acoreweb.acoreapi.service;

import com.acoreweb.acoreapi.model.ChatQuery;
import com.acoreweb.acoreapi.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    // ================= COMPANY INFO =================
    private static final String COMPANY_NAME = "Acore IT HUB Pvt. Ltd.";
    private static final String HR_EMAIL = "hrofficial@acoreithub.com";
    private static final String HR_PHONE = "+91-9039589348";
    private static final String INFO_EMAIL = "info@acoreithub.com";
    private static final String SALES_EMAIL = "sales@acoreithub.com";
    private static final String PHONE = "+91 88718-55460";

    private static final String ADDRESS =
            "Acore IT Hub Pvt. Ltd., Behind Apollo Premiere, Vijay Nagar, Indore, Madhya Pradesh 452010";

    private static final String MAP_URL =
            "https://www.google.com/maps/search/?api=1&query=Acore+IT+HUB+PVT+LTD+Indore";

    private final Random random = new Random();

    // ================= INTENTS =================
    private enum Intent {
        GREETING,
        JOB,
        INTERNSHIP,
        JOB_INTERNSHIP,
        HR_CONTACT,
        SERVICES,
        LOCATION,
        COMPANY_INFO,
        SALARY,
        WORK_MODE,
        ELIGIBILITY,
        THANKS,
        UNKNOWN
    }

    // ================= STOP WORDS =================
    private static final String[] STOP_WORDS = {
            "i","am","is","are","was","were","looking","for","any","please",
            "can","you","me","about","want","need","tell","give","show","do"
    };

    // ================= MAIN METHOD =================
    public String getBotResponse(String userQuery) {

        if (userQuery == null || userQuery.trim().isEmpty()) {
            return fallback();
        }

        // 🔒 LIMIT = 500 CHARS
        if (userQuery.length() > 500) {
            return "Please keep your query under 500 characters so I can assist you better.";
        }

        String query = userQuery
                .trim()
                .toLowerCase()
                .replaceAll("[^a-z0-9\\s]", ""); // punctuation safe

        Intent intent = detectIntent(query);
        String response = generateResponse(intent);

        // footer only where useful
        if (intent == Intent.HR_CONTACT
                || intent == Intent.LOCATION
                || intent == Intent.COMPANY_INFO) {
            response += footer();
        }

        return response.trim();
    }

    // ================= INTENT DETECTION =================
    private Intent detectIntent(String q) {

        boolean job = hasIntent(q,
                "job","jobs","opening","openings","vacancy","career","hiring");

        boolean intern = hasIntent(q,
                "intern","internship","training","trainee");

        // 🔥 priority: job / internship
        if (job && intern) return Intent.JOB_INTERNSHIP;
        if (job) return Intent.JOB;
        if (intern) return Intent.INTERNSHIP;

        if (isPureGreeting(q)) return Intent.GREETING;

        if (hasIntent(q,"hr","contact","email","phone"))
            return Intent.HR_CONTACT;

        if (hasIntent(q,
                "service","services","website","web","app","mobile",
                "software","seo","smm","ui","ux","cloud","devops","ecommerce"))
            return Intent.SERVICES;

        if (hasIntent(q,"address","location","office","map","direction"))
            return Intent.LOCATION;

        if (hasIntent(q,"about","company","acore","profile"))
            return Intent.COMPANY_INFO;

        if (hasIntent(q,"salary","package","ctc","stipend"))
            return Intent.SALARY;

        if (hasIntent(q,"remote","onsite","work","timing"))
            return Intent.WORK_MODE;

        if (hasIntent(q,"eligibility","skill","qualification","experience"))
            return Intent.ELIGIBILITY;

        if (hasIntent(q,"thanks","thank","bye"))
            return Intent.THANKS;

        return Intent.UNKNOWN;
    }

    // ================= SMART KEYWORD MATCH =================
    private boolean hasIntent(String text, String... keywords) {

        String[] tokens = text.split("\\s+");

        for (String token : tokens) {

            if (token.length() <= 2 || isStopWord(token)) continue;

            for (String key : keywords) {
                if (token.startsWith(key) || key.startsWith(token)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isStopWord(String word) {
        for (String sw : STOP_WORDS) {
            if (sw.equals(word)) return true;
        }
        return false;
    }

    private boolean isPureGreeting(String q) {
        return q.matches("^(hi|hello|hey|hii|heii|good morning|good evening)$");
    }

    // ================= RESPONSE GENERATOR =================
    private String generateResponse(Intent intent) {

        return switch (intent) {

            case GREETING -> pick(
                    "Hello! Welcome to " + COMPANY_NAME + ". How may I assist you today?",
                    "Hi there 👋 Welcome to " + COMPANY_NAME + ". How can I help?",
                    "Welcome! Please let me know how I can assist you."
            );

            case JOB -> pick(
                    "We do have job opportunities. Please share your resume at " + HR_EMAIL + ".",
                    "You may apply for current openings by emailing your CV to " + HR_EMAIL + ".",
                    "We are currently accepting job applications. Kindly send your resume."
            );

            case INTERNSHIP -> pick(
                    "Internship opportunities are available. Please email your resume to " + HR_EMAIL + ".",
                    "We do offer internships. Kindly share your CV with our HR team.",
                    "You can apply for an internship by sending your resume to " + HR_EMAIL + "."
            );

            case JOB_INTERNSHIP ->
                    "We offer both job and internship opportunities. Please send your resume to " + HR_EMAIL + ".";

            case HR_CONTACT ->
                    "You can contact our HR team at:\nEmail: " + HR_EMAIL + "\nPhone: " + HR_PHONE;

            case SERVICES ->
                    """
                    We offer the following services:
                    • Custom Web & Web App Development
                    • Mobile App Development
                    • UI/UX Design
                    • Cloud & DevOps
                    • Digital Marketing (SEO & SMM)
                    • E-Commerce Development
                    • IT Consulting

                    Please let me know which service you want to know more about.
                    """;

            case LOCATION ->
                    """
                    Our office address:
                    %s

                    Get directions:
                    %s
                    """.formatted(ADDRESS, MAP_URL);

            case COMPANY_INFO ->
                    COMPANY_NAME + " is an IT services company providing development, consulting, and digital solutions.";

            case SALARY ->
                    "Salary or stipend details depend on the role and interview process. Please contact HR for details.";

            case WORK_MODE ->
                    "Work mode and office timings depend on the role. Kindly discuss this with HR.";

            case ELIGIBILITY ->
                    "Eligibility and skill requirements vary by role. Please share your resume for review.";

            case THANKS ->
                    "You’re welcome 😊 Feel free to reach out if you need any assistance.";

            default -> fallback();
        };
    }

    private String pick(String... options) {
        return options[random.nextInt(options.length)];
    }

    // ================= FALLBACK =================
    private String fallback() {
        return "I may not have complete information on that. Please contact us at " + INFO_EMAIL + ".";
    }

    // ================= FOOTER =================
    private String footer() {
        return """

                Phone: %s
                Email: %s
                Sales: %s
                """.formatted(PHONE, INFO_EMAIL, SALES_EMAIL);
    }

    // ================= SAVE CHAT =================
    public ChatQuery saveQuery(String query) {

        String response = getBotResponse(query);

        ChatQuery chat = new ChatQuery();
        chat.setUserQuery(query);
        chat.setBotResponse(response);

        return chatRepository.save(chat);
    }
}
